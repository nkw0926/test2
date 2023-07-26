package com.example.geumodoIsland.ocean.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Bait {
    private int baitId;
    private int userId;
    private int notFreeBaitCount;
    private int freeBaitCount;
    private int availableBait;

}
