package com.example.geumodoIsland.user.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLastStep {
	private List<MultipartFile> files = new ArrayList<MultipartFile>();
//	private String originalFileName; //원본 사진 이름
//	private String storedFileName; // 저장된 사진 이름
//	private int fileAttached; // 사진 첨부 여부(첨부 1, 미첨부 0)
}
