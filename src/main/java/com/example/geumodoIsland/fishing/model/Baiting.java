package com.example.geumodoIsland.fishing.model;

import lombok.Data;

@Data
public class Baiting {
	int baitingId;
	int fishermenId; //미끼 던진 사람
	int fishId; //미끼를 받을 물고기
	String baitingStatus; //미끼를 물었는지 뱉었는지 또는 간을 보는지 상태를 저장할 변수
	String fishName; //물고기 이름
	int fishAge; //물고기 나이
	String fishAddress;
	int fishHeight;
	String fishBody;

	String fishermenName; // 미끼 던진 사람 이름
	int fishermenAge; // 미끼 던진사람 나이


}
