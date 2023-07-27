package com.example.geumodoIsland.aquarium.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.geumodoIsland.aquarium.dao.IAquaRepository;
import com.example.geumodoIsland.aquarium.model.Aquarium;

@Service
public class AquaService implements IAquaService {
    @Autowired
    IAquaRepository aquaRepository;

    //내 아쿠아리움 물고기 목록
    public List<Aquarium> showFishList(int fishermenId) {
		  List<Aquarium> aquariumList = aquaRepository.showFishList(fishermenId);
		  if (aquariumList == null) {
		    aquariumList = new ArrayList<>();
		  }
		  return aquariumList;
		}
    
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
