package com.example.geumodoIsland.user.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class UserBarChart {
    private int countFish;
    private char userSex;
    private String userAddress;
}
