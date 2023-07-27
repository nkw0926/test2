package com.example.geumodoIsland.aquarium.controller;

import com.example.geumodoIsland.aquarium.model.Aquarium;
import com.example.geumodoIsland.aquarium.service.IAquaService;
import com.example.geumodoIsland.ocean.service.IOceanService;

import jakarta.servlet.http.HttpSession;

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
    		   List<Aquarium> aquariumList = aquaService.showFishList(fishermenId);
    		   model.addAttribute("aquariumList", aquariumList);
    		   return "aquarium/aquaList";
    	}
	}
    
    @PostMapping("/addAquarium")
    public String addAquarium(@RequestParam int loginUserId, @RequestParam int targetUserId, Model model) {
        if(aquaService.selectRowByUserIdTargetId(loginUserId,targetUserId) ==0){
            // 삽입
            aquaService.insertAquarium(loginUserId,targetUserId);
            model.addAttribute("message", "물고기를 수족관에 담았습니다! ");
            model.addAttribute("searchUrl", "/ocean/userDetail?userId=" + targetUserId);
            return "ocean/message";
        }else{
            model.addAttribute("message", "이미 수족관에 있는 물고기입니다! ");
            model.addAttribute("searchUrl", "/ocean/userDetail?userId=" + targetUserId);
            return "ocean/message";
        }



    }
}
