package com.example.geumodoIsland.fishing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.geumodoIsland.fishing.model.Baiting;
import com.example.geumodoIsland.fishing.service.IBaitingService;

@Controller
public class BaitingController {
	@Autowired
	IBaitingService baitingService;

	@GetMapping("/baiting") //낚시 리스트 불러오기
	public String getBaitingList(@RequestParam("fishermenId") int fishermenId, Model model) {

		//미끼 던진 내역 출력
	    List<Baiting> baitingList = baitingService.showBaitingList(fishermenId);

	    //미끼 받은 내역 출력
	    List<Baiting> baitedList = baitingService.showBaitedList(fishermenId);

		/*
		 * baitedList.stream().forEach((b) -> { System.out.println(b.getBaitingId());
		 * });
		 */

	    model.addAttribute("baitingList", baitingList);
	    model.addAttribute("baitedList", baitedList);
	    return "fishing/baitingList";

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