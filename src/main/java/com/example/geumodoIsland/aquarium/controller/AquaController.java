package com.example.geumodoIsland.aquarium.controller;

import com.example.geumodoIsland.aquarium.model.Aquarium;
import com.example.geumodoIsland.aquarium.service.IAquaService;

import jakarta.servlet.http.HttpSession;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/aquarium")
public class AquaController {
    @Autowired
    IAquaService aquaService;

    //내 아쿠아리움 물고기 출력
    @GetMapping("")
    public String getFishList(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        String userIdInSession = String.valueOf(session.getAttribute("userId"));

        if (userIdInSession == "null") {
            redirectAttributes.addFlashAttribute("userState", "로그인");
            return "redirect:/user/login";
        } else {
            int fishermenId = Integer.valueOf(userIdInSession);
//           String fishingStatus = String.valueOf(aquaService.selectRowByUserId(fishermenId).getFishingStatus());
//            if( fishingStatus.equals('W') || fishingStatus.equals('S')  ){
//// 상태가
//            }

            List<Aquarium> aquariumList = aquaService.showFishList(fishermenId);

            Date now = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
            String formatedNow = formatter.format(now);
            for (int i = 0; i < aquariumList.size(); i++) {
                int userBornYear = aquariumList.get(i).getFishAge();
                int userAgeThisYear = (Integer.parseInt(formatedNow) - userBornYear);
                aquariumList.get(i).setFishAge(userAgeThisYear);
            }

            model.addAttribute("aquariumList", aquariumList);
            model.addAttribute("userState", "로그아웃");
            return "aquarium/aquaList";
        }
    }

    @PostMapping("/addAquarium")
    public String addAquarium(int targetUserId, Model model, HttpSession session) {
        int loginUserId = Integer.valueOf(String.valueOf(session.getAttribute("userId")));

        if (aquaService.selectRowByUserIdTargetId(loginUserId, targetUserId) == 0) {
            // 삽입
            aquaService.insertAquarium(loginUserId, targetUserId);

            model.addAttribute("message", "물고기를 수족관에 담았습니다! ");
            model.addAttribute("searchUrl", "/ocean/userDetail?userId=" + targetUserId);
            return "ocean/message";
        } else {
            model.addAttribute("message", "이미 수족관에 있는 물고기입니다! ");
            model.addAttribute("searchUrl", "/ocean/userDetail?userId=" + targetUserId);
            return "ocean/message";
        }
    }


    @PostMapping("/delete/aquarium")
    public String deleteAquarium(int targetUserId,Model model,HttpSession session) {
        int loginUserId = Integer.valueOf(String.valueOf(session.getAttribute("userId")));
        System.out.println("targetUserId");
        System.out.println(targetUserId);
        aquaService.deleteAqua(loginUserId, targetUserId);
        model.addAttribute("message", "물고기를 방생했습니다! ");
        model.addAttribute("searchUrl", "/aquarium");
        return "ocean/message";
    }
    @PostMapping("/minusBait")
    public String minusBait(@RequestParam int targetUserId, Model model, HttpSession session) {
        // 해당 유저 가용 미끼 있는지 확인
        int userIdInSession = (int) session.getAttribute("userId");
        String throwBaitResult =aquaService.throwBait(userIdInSession, targetUserId);
        if( throwBaitResult.equals( throwBaitResult.equals("/aquarium"))){

            return "/aquarium";
        }else{
            model.addAttribute("message", throwBaitResult);
            model.addAttribute("searchUrl", "/aquarium");
            return "ocean/message";
        }
    }
}


