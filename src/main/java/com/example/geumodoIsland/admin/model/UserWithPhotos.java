package com.example.geumodoIsland.admin.model;

import java.util.List;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserWithPhotos {
	private int userId;
    private String userEmail;
    private String userName;
    private List<UserPhotos> photos;
}
