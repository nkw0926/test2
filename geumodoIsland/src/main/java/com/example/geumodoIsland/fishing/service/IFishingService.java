package com.example.geumodoIsland.fishing.service;

public interface IFishingService {

    void insertFishingInfo(int loginUserId, int targetUserId);
    int seclectRowByUserIdTargetId(int loginUserId, int targetUserId);

    Object seclectFishingStatus(int loginUserId, int targetUserId);

}
