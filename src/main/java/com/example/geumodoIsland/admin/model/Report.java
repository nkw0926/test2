package com.example.geumodoIsland.admin.model;

import java.sql.Timestamp;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Report {
	private int reportId;  //신고 아이디
	private int reporterId;  //신고자 아이디
	private int targetId;  //신고받는자 아이디
	private String reportContent;  //신고 내용
	private String reportStatus;  //신고 상태
	private Timestamp createdAt;
}