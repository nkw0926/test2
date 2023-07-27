package com.example.geumodoIsland.photo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.geumodoIsland.user.model.UserPhoto;

import java.util.List;

@Repository
@Mapper
public interface IPhotoRepository {
    List<String> selectUserPhoto(@Param("userId") int userId);
    void insertUserPhoto(UserPhoto userPhoto);
}
