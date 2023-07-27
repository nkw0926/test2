package com.example.geumodoIsland.fishing.service;

import com.example.geumodoIsland.fishing.dao.IFishingRepository;
import com.example.geumodoIsland.ocean.dao.IOceanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FishingService implements IFishingService{
    @Autowired
    IFishingRepository fishingRepository;
    @Override
    public void insertFishingInfo(int loginUserId, int targetUserId) {
        fishingRepository.insertFishingInfo(loginUserId, targetUserId);
    }

    @Override
    public int seclectRowByUserIdTargetId(int loginUserId, int targetUserId) {
        return fishingRepository.seclectRowByUserIdTargetId(loginUserId,targetUserId);
    }

    @Override
    public Object seclectFishingStatus(int loginUserId, int targetUserId) {
        return fishingRepository.seclectFishingStatus(loginUserId,targetUserId);
    }


}
