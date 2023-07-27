package com.example.geumodoIsland.photo.service;

import com.example.geumodoIsland.photo.dao.IPhotoRepository;
import com.example.geumodoIsland.user.model.UserPhoto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PhotoService implements IPhotoService{
    @Autowired
    IPhotoRepository photoRepository;
    
    @Override
    public List<String> selectUserPhoto(int userId) {
        return photoRepository.selectUserPhoto(userId);
    }
}
