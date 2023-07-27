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
    public String getAllFishListForFishing(Model model,HttpSession session, RedirectAttributes redirectAttributes) {
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
 	 	System.out.println(userState);
 	 	if (userState.equals("로그인")) {
 	 		return "로그인";
 	 	} else {
 	 		session.invalidate();
 	 		return "로그아웃";
 	 	}
 	 }

    @PostMapping(value = "/selectCondition")
    public ResponseEntity<List<User>> getFishListByCondition(@RequestBody Map<String, Object> selectCondotionMap, Model model) {
        int loginUserId = 6;

        char loginUserSex = 'F';
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
        List<User> conditionUserList = userService.selectFishListByCondition(loginUserId, loginUserSex, locationList, bornYearList, hobbyList, personalityList);

        model.addAttribute("loginUserName", "뽑아라");
        model.addAttribute("userList", conditionUserList);
        System.out.println(conditionUserList);
        return new ResponseEntity<>(conditionUserList, HttpStatusCode.valueOf(202));
    }

    @GetMapping(value = "/userDetail")
    public String selectAUserInfo(@RequestParam("userId") int userId, Model model) {
        int loginUserId = 6;
        
        model.addAttribute("userState", "로그아웃");

        Map<String, Object> AUserInfo = userService.selectAUserInfo(userId);
        List<String> userPhotoName = photoService.selectUserPhoto(userId);
        model.addAttribute("userPhotoName", userPhotoName);

        System.out.println(AUserInfo);

        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        String formatedNow = formatter.format(now);
        User userInfo = (User) AUserInfo.get("userInfo");

        Integer userCalAge = (Integer.parseInt(formatedNow) - userInfo.getUserAge());
        userInfo.setUserAge(userCalAge);
        model.addAttribute("userInfo", userInfo);

        String[] userHobbies = (String[]) AUserInfo.get("userHobbies");
        System.out.println(userHobbies.length);
        model.addAttribute("userHobbies", userHobbies);

        String[] userIntroductions = (String[]) AUserInfo.get("userIntroductions");
        System.out.println("userIntroductions: " + userIntroductions);
        model.addAttribute("userIntroductions", userIntroductions);
        model.addAttribute("loginUserId", loginUserId);

        int isInAqua = aquaService.selectRowByUserIdTargetId(loginUserId, userId);
        model.addAttribute("isInAqua", isInAqua);
        System.out.println("isInAqua" + isInAqua);

        int isThrowBait = fishingService.seclectRowByUserIdTargetId(loginUserId, userId);
        model.addAttribute("isThrowBait", isThrowBait);

        Object fishingState = fishingService.seclectFishingStatus(loginUserId, userId);

        if (fishingState != null) {
            model.addAttribute("fishingState", fishingState);
        } else {
            model.addAttribute("fishingState", 'N');
        }

        return "ocean/userDetail";
    }

    @PostMapping("/freeFishInAqua")
    public String freeFish(@RequestParam int loginUserId, @RequestParam int targetUserId, Model model) {
        aquaService.deleteAqua(loginUserId, targetUserId);
        return selectAUserInfo(targetUserId, model);
    }

    //이거 service로 보내고 transactional 달아야하는데,,,
    @PostMapping("/minusBait")
    public String minusBait(@RequestParam int loginUserId, @RequestParam int targetUserId, Model model) {
        // 해당 유저 가용 미끼 있는지 확인


        if ((Integer) (oceanService.selectCountAllBait(loginUserId)) != null && oceanService.selectCountAllBait(loginUserId) > 0 && fishingService.seclectRowByUserIdTargetId(loginUserId, targetUserId) == 0) {
            // 가용 미끼가 있으면  무료 미끼 먼저 소진
            //무료 미끼 있냐?
            if (oceanService.selectCountFreeBait(loginUserId) > 0) {
                // 무료 미끼 있으면
                // 무료미끼 하나 삭제
                oceanService.minusFreeBait(loginUserId);
            } else {
                // 무료미끼 없으면 유료미끼 하나 삭제
                oceanService.minusNotFreeBait(loginUserId);
            }
            // 그 낚시 테이블에 정보 기록
            fishingService.insertFishingInfo(loginUserId, targetUserId);
            model.addAttribute("message", "미끼를 성공적으로 던졌습니다! \n 물고기의 반응을 기다려주세요!");
            model.addAttribute("searchUrl", "/ocean/userDetail?userId=" + targetUserId);
            return "ocean/message";
        } else if (oceanService.selectCountAllBait(loginUserId) <= 0) {
            // 가용 미끼 없으면?
            // 가용 미끼가 존재하지 않습니다!
            model.addAttribute("message", "가용 미끼가 존재하지 않습니다!");
            model.addAttribute("searchUrl", "/ocean/userDetail?userId=" + targetUserId);
            return "ocean/message";
        } else if (fishingService.seclectRowByUserIdTargetId(loginUserId, targetUserId) != 0) {
            //이미 그 물고기에게 미끼를 던졌으면?
            model.addAttribute("message", "해당 유저에게 미끼를 던진 과거 기록이 있습니다!");
            model.addAttribute("searchUrl", "/ocean/userDetail?userId=" + targetUserId);
            return "ocean/message";
        }
        return "/ocean/userDetail?userId=" + targetUserId;
    }
}
