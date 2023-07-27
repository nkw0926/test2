package com.example.geumodoIsland.photo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Photo {
    private int photoId;
    private int userId;
    private String photoFileName;

}
