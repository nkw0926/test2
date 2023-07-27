package com.example.geumodoIsland.admin.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AgeRange {
    private String ageCategory;
    private int ageRange;

    public AgeRange(String ageCategory, int ageRange) {
        this.ageCategory = ageCategory;
        this.ageRange = ageRange;
    }
}
