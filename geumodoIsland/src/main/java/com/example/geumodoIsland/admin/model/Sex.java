package com.example.geumodoIsland.admin.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Sex {
    private String sexCategory;
    private int sexCount;

    public Sex(String sexCategory, int sexCount) {
        this.sexCategory = sexCategory;
        this.sexCount = sexCount;
    }

}
