package com.example.geumodoIsland.fishing.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.geumodoIsland.fishing.model.Baiting;
import com.example.geumodoIsland.fishing.service.IBaitingService;
import com.example.geumodoIsland.user.model.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class BaitingController {
	@Autowired
	IBaitingService baitingService;

	@GetMapping("/baiting") //낚시 리스트 불러오기
	public String getBaitingList(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
		String userIdInSession = String.valueOf(session.getAttribute("userId"));
		System.out.println(userIdInSession);
		
		if (userIdInSession == "null") {
    		return "redirect:/user/login";
    	} else {
    		int fishermenId = Integer.valueOf(userIdInSession);
    		//미끼 던진 내역 출력
    	    List<Baiting> baitingList = baitingService.showBaitingList(fishermenId);
    	    
    	    //미끼 받은 내역 출력
    	    List<Baiting> baitedList = baitingService.showBaitedList(fishermenId);
    	    
    		/*
    		 * baitedList.stream().forEach((b) -> { System.out.println(b.getBaitingId());
    		 * });
    		 */
    	    
    	    model.addAttribute("userState", "로그아웃");
    	    model.addAttribute("baitingList", baitingList);
    	    model.addAttribute("baitedList", baitedList);
    	    return "fishing/baitingList";
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

	@GetMapping("/getPhoneNumber/{fishId}")
	public ResponseEntity<String> getPhoneNumber(@PathVariable int fishId) {
		String phoneNumber = baitingService.getPhoneNumberWhenBaited(fishId);
		if (phoneNumber != null) {
			return ResponseEntity.ok(phoneNumber);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/baiting/accept/{baitingId}")
	public ResponseEntity<Void> acceptBaiting(@PathVariable int baitingId) {
	    baitingService.acceptBaiting(baitingId);
	    return ResponseEntity.ok().build();
	}

	@PostMapping("/baiting/reject/{baitingId}")
	public ResponseEntity<Void> rejectBaiting(@PathVariable int baitingId) {
	    baitingService.rejectBaiting(baitingId);
	    return ResponseEntity.ok().build();
	}
}
