package com.example.geumodoIsland.fishing.service;

import java.util.List;

import com.example.geumodoIsland.fishing.model.Baiting;

public interface IBaitingService {
	List<Baiting> showBaitingList(int fisherMenId); //미끼 던진 물고기 목록
	List<Baiting> showBaitedList(int fishId); // 내게로 온 미끼 목록
	String getPhoneNumberWhenBaited(int fishId); //미끼를 물었을 경우 전화번호 반환
	String getFishName(int fishId); //물고기 이름
	Integer getFishAge(int fishId); // 물고기 나이
	void acceptBaiting(int baitingId); // 미끼를 먹은 경우 처리 메서드
	void rejectBaiting(int baitingId); // 미끼를 먹은 경우 처리 메서드
}
