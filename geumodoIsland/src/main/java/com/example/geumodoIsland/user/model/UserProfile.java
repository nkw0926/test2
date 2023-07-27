package com.example.geumodoIsland.user.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfile {
	  private String userEmail;
	  private String userName;
	  private int userAge;
	  private char userSex;
	  private String userAddress;
	  private int userHeight;
	  private String userBody;
	  private String userPhoneNumber;
	  private String userJob;
	  private String userHobbies;
	  private String userIntroductions;
	  private List<String> userPhotoFileNames;
}
