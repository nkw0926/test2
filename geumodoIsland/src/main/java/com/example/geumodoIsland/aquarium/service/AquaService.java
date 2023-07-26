package com.example.geumodoIsland.aquarium.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.geumodoIsland.aquarium.dao.IAquaRepository;

@Service
public class AquaService implements IAquaService {
    @Autowired
    IAquaRepository aquaRepository;

    @Override
    public int selectRowByUserIdTargetId(int loginUserId, int targetUserId) {
        return aquaRepository.selectRowByUserIdTargetId(loginUserId, targetUserId);
    }

    @Override
    public void insertAquarium(int loginUserId, int targetUserId) {
        aquaRepository.insertAquarium(loginUserId, targetUserId);
    }
    @Override
    public void deleteAqua(int loginUserId, int targetUserId) {
        aquaRepository.deleteAqua(loginUserId, targetUserId);
    }
}
