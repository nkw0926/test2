package com.example.geumodoIsland.ocean.controller;

import com.example.geumodoIsland.aquarium.service.IAquaService;
import com.example.geumodoIsland.fishing.service.IFishingService;
import com.example.geumodoIsland.ocean.service.IOceanService;
import com.example.geumodoIsland.photo.service.IPhotoService;
import com.example.geumodoIsland.user.model.User;
import com.example.geumodoIsland.user.service.IUserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/ocean")
public class OceanController {
    @Autowired
    IOceanService oceanService;
    @Autowired
    IUserService userService;
    @Autowired
    IPhotoService photoService;
    @Autowired
    IFishingService fishingService;
    @Autowired
    IAquaService aquaService;

    @GetMapping(value = "/all")
    public String getAllFishListForFishing(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        String userIdInSession = String.valueOf(session.getAttribute("userId"));

        if (userIdInSession == "null") {
            redirectAttributes.addFlashAttribute("userState", "로그인");
            return "redirect:/user/login";
        } else {
            int userId = Integer.valueOf(userIdInSession);
            Map<String, Object> loginUserInfo = userService.selectAUserInfo(userId);
            User userInfoMap = (User) loginUserInfo.get("userInfo");

            String loginUserName = userInfoMap.getUserName();
            String loginUserAddress = userInfoMap.getUserAddress();
            char loginUserSex = userInfoMap.getUserSex();

            List<User> userList = userService.selectFishListByAddress(loginUserAddress, loginUserSex, userId);
            model.addAttribute("loginUserName", loginUserName);

            Date now = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
            String formatedNow = formatter.format(now);
            for (int i = 0; i < userList.size(); i++) {
                int userBornYear = userList.get(i).getUserAge();
                int userAgeThisYear = (Integer.parseInt(formatedNow) - userBornYear);
                userList.get(i).setUserAge(userAgeThisYear);
            }
            model.addAttribute("userList", userList);
            model.addAttribute("userState", "로그아웃");

            oceanService.resetFreeBait();

            return "ocean/oceanMain";
        }
    }

    // 로그인, 로그아웃 처리
    @PostMapping("/getUserState")
    public @ResponseBody String getUserState(@RequestParam("userState") String userState, HttpSession session) {

        if (userState.equals("로그인")) {
            return "로그인";
        } else {
            session.invalidate();
            return "로그아웃";
        }
    }

    @PostMapping(value = "/selectCondition")
    public ResponseEntity<List<User>> getFishListByCondition(@RequestBody Map<String, Object> selectCondotionMap, Model model, HttpSession session) {

        String userIdInSession = String.valueOf(session.getAttribute("userId"));

        char loginUserSex = userService.selectUserProfileByUserId(Integer.parseInt(userIdInSession)).getUserSex();


        // JSON 데이터를 자바 Map으로
        List<String> locationList = (List<String>) selectCondotionMap.get("locationList");
        List<String> ageList = (List<String>) selectCondotionMap.get("ageList");
        List<String> list = new ArrayList<String>();
        List<Integer> bornYearList = new ArrayList<Integer>();

        for (int i = 0; i < ageList.size(); i++) {
            String[] arr = ageList.get(i).split(",");
            for (int j = 0; j < arr.length; j++) {
                list.add(arr[j]);
            }
        }

        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        String formatedNow = formatter.format(now);
        for (int i = 0; i < list.size(); i++) {
            bornYearList.add(Integer.parseInt(formatedNow) - Integer.parseInt(list.get(i)));
        }

        List<String> hobbyList = (List<String>) selectCondotionMap.get("hobbyList");
        List<String> personalityList = (List<String>) selectCondotionMap.get("personalityList");
        List<User> conditionUserList = userService.selectFishListByCondition(Integer.parseInt(userIdInSession), loginUserSex, locationList, bornYearList, hobbyList, personalityList);

        model.addAttribute("loginUserName", "뽑아라");
        model.addAttribute("userList", conditionUserList);

        return new ResponseEntity<>(conditionUserList, HttpStatusCode.valueOf(202));
    }

    @GetMapping(value = "/userDetail")
    public String selectAUserInfo(@RequestParam("userId") int targetUserId, Model model,HttpSession session) {
        int userIdInSession = (int) session.getAttribute("userId");
        model.addAttribute("userState", "로그아웃");

        Map<String, Object> AUserInfo = userService.selectAUserInfo(targetUserId);
        List<String> userPhotoName = photoService.selectUserPhoto(targetUserId);
        model.addAttribute("userPhotoName", userPhotoName);


        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        String formatedNow = formatter.format(now);
        User userInfo = (User) AUserInfo.get("userInfo");

        Integer userCalAge = (Integer.parseInt(formatedNow) - userInfo.getUserAge());
        userInfo.setUserAge(userCalAge);
        model.addAttribute("userInfo", userInfo);

        String[] userHobbies = (String[]) AUserInfo.get("userHobbies");

        model.addAttribute("userHobbies", userHobbies);

        String[] userIntroductions = (String[]) AUserInfo.get("userIntroductions");

        model.addAttribute("userIntroductions", userIntroductions);
        model.addAttribute("loginUserId", userIdInSession);

        int isInAqua = aquaService.selectRowByUserIdTargetId(userIdInSession, targetUserId);
        model.addAttribute("isInAqua", isInAqua);


        int isThrowBait = fishingService.seclectRowByUserIdTargetId(userIdInSession, targetUserId);
        model.addAttribute("isThrowBait", isThrowBait);

        Object fishingState = fishingService.seclectFishingStatus(userIdInSession, targetUserId);
        System.out.println(userIdInSession + "  " + targetUserId);
        System.out.println("fishingState");
        System.out.println(fishingState);
        if (fishingState != null) {
            model.addAttribute("fishingState", fishingState);
        } else {
            model.addAttribute("fishingState", 'N');
        }

        return "ocean/userDetail";
    }

    @PostMapping("/freeFishInAqua")
    public String freeFish( @RequestParam int targetUserId, Model model,HttpSession session) {
        int userIdInSession = (int) session.getAttribute("userId");
        aquaService.deleteAqua(userIdInSession, targetUserId);
        return selectAUserInfo(targetUserId, model, session);
    }

    //이거 service로 보내고 transactional 달아야하는데,,,
    @PostMapping("/minusBait")
    public String minusBait(@RequestParam int targetUserId, Model model, HttpSession session) {
        // 해당 유저 가용 미끼 있는지 확인
        int userIdInSession = (int) session.getAttribute("userId");
        String throwBaitResult = oceanService.throwBait(userIdInSession, targetUserId);
        if (throwBaitResult.equals("/ocean/userDetail?userId=" + throwBaitResult)) {

            return "/ocean/userDetail?userId=" + throwBaitResult;
        } else {
            model.addAttribute("message", throwBaitResult);
            model.addAttribute("searchUrl", "/ocean/userDetail?userId=" + targetUserId);
            return "ocean/message";
        }
    }
}
