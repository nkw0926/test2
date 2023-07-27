package com.example.geumodoIsland.fishing.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.geumodoIsland.fishing.model.Baiting;
import com.example.geumodoIsland.fishing.service.IBaitingService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BaitingController {
	@Autowired
	IBaitingService baitingService;
	
	//낚시 리스트 불러오기
	@GetMapping("/baiting") 
	public String getBaitingList( Model model, HttpSession session, RedirectAttributes redirectAttributes) {
		String userIdInSession = String.valueOf(session.getAttribute("userId"));

		if (userIdInSession == "null") {
			redirectAttributes.addFlashAttribute("userState", "로그인");
			return "redirect:/user/login";
		} else {
			int userId = Integer.valueOf(userIdInSession);

			//미끼 던진 내역 출력
			List<Baiting> baitingList = baitingService.showBaitingList(userId);

			//미끼 받은 내역 출력
			List<Baiting> baitedList = baitingService.showBaitedList(userId);

			/*
			 * baitedList.stream().forEach((b) -> { System.out.println(b.getBaitingId());
			 * });
			 */

			model.addAttribute("baitingList", baitingList);
			model.addAttribute("baitedList", baitedList);
			model.addAttribute("userState", "로그아웃");
			return "fishing/baitingList";
		}
	}
	
	//전화번호 반환
	@GetMapping("/getPhoneNumber/{fishId}")
	public ResponseEntity<String> getPhoneNumber(@PathVariable int fishId) {
		String phoneNumber = baitingService.getPhoneNumberWhenBaited(fishId);
		if (phoneNumber != null) {
			return ResponseEntity.ok(phoneNumber);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	//미끼 수락
	@PostMapping("/baiting/accept/{baitingId}")
	public ResponseEntity<Void> acceptBaiting(@PathVariable int baitingId) {
	    baitingService.acceptBaiting(baitingId);
	    return ResponseEntity.ok().build();
	}

	//미끼 거절
	@PostMapping("/baiting/reject/{baitingId}")
	public ResponseEntity<Void> rejectBaiting(@PathVariable int baitingId) {
	    baitingService.rejectBaiting(baitingId);
	    return ResponseEntity.ok().build();
	}
	
	 // 미끼 삭제
    @DeleteMapping("/baiting/delete/{baitingId}")
    public ResponseEntity<Void> deleteBaiting(@PathVariable int baitingId) {
        baitingService.deleteBaiting(baitingId);
        return ResponseEntity.ok().build();
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
}
