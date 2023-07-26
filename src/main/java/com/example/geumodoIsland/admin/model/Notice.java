package com.example.geumodoIsland.admin.model;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Notice {
	private int noticeId;
	private int userId;
	private String noticeContent;
	private Timestamp createdAt;
	private Timestamp reportCreatedAt; // report 테이블의 created_at 값 저장용
}