package com.example.geumodoIsland.fishing.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.geumodoIsland.fishing.model.Baiting;

@Repository
@Mapper
public interface IBaitingRepository {
	List<Baiting> showBaitingList(int fisherMenId); //미끼 던진 물고기 목록
	List<Baiting> showBaitedList(int fishId); // 내게로 온 미끼 목록
	String getPhoneNumberWhenBaited(int fishId); //미끼를 물었을 경우 전화번호 반환
	Baiting findById(int fishId); // 물고기 아이디 조회
	String getFishName(int fishId); //물고기 이름
	Integer getFishAge(int fishId); // 물고기 나이
	void updateBaitingStatusToS(int baitingId); // 미끼를 먹은 경우 처리 메서드
	void updateBaitingStatusToF(int baitingId); // 미끼를 먹은 경우 처리 메서드
}
