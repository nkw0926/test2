package com.example.geumodoIsland.aquarium.service;

import java.util.List;

import com.example.geumodoIsland.aquarium.model.Aquarium;

public interface IAquaService {
	
	List<Aquarium> showFishList(int fishermenId); //아쿠아리움 물고기 목록 불러오기
    int selectRowByUserIdTargetId(int loginUserId, int targetUserId);

    void insertAquarium(int loginUserId, int targetUserId);
    void deleteAqua(int loginUserId, int targetUserId);
}
