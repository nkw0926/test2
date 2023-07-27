package com.example.geumodoIsland.aquarium.service;

public interface IAquaService {
    int selectRowByUserIdTargetId(int loginUserId, int targetUserId);

    void insertAquarium(int loginUserId, int targetUserId);
    void deleteAqua(int loginUserId, int targetUserId);
}
