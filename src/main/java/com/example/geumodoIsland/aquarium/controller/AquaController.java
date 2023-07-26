package com.example.geumodoIsland.aquarium.controller;

import com.example.geumodoIsland.aquarium.service.IAquaService;
import com.example.geumodoIsland.ocean.service.IOceanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
@RequestMapping("/aquarium")
public class AquaController {
    @Autowired
    IAquaService aquaService;
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
