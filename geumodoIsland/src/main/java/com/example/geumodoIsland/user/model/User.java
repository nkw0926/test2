package com.example.geumodoIsland.user.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Getter
@Setter
@ToString
@Builder
public class User {
    private int userId;
    private String userEmail;
    private String userName;
    private int userAge;
    private char userSex;
    private String userAddress;
    private int userHeight;
    private String userBody;
    private String userPhoneNumber;
    private String userPassword;
    private char userStatus;
    private String userJob;
    private String userHobbies;
    private String userIntroductions;
    private Date userUpdateAt;
}
