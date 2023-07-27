package com.example.geumodoIsland.aquarium.service;

import java.util.ArrayList;
import java.util.List;

import com.example.geumodoIsland.fishing.dao.IFishingRepository;
import com.example.geumodoIsland.fishing.model.fishing;
import com.example.geumodoIsland.fishing.service.IFishingService;
import com.example.geumodoIsland.ocean.dao.IOceanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.geumodoIsland.aquarium.dao.IAquaRepository;
import com.example.geumodoIsland.aquarium.model.Aquarium;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AquaService implements IAquaService {
    @Autowired
    IAquaRepository aquaRepository;
    @Autowired
    IOceanRepository oceanRepository;

    @Autowired
    IFishingService fishingService;
    @Autowired
    IFishingRepository fishingRepository;


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

    @Transactional
    @Override
    public String throwBait(int userIdInSession, int targetUserId) {
        if (oceanRepository.selectCountAllBait(userIdInSession) == null) {
            // 가용 미끼 없으면?
            // 가용 미끼가 존재하지 않습니다!
            return "가용 미끼가 존재하지 않습니다!";
        } else if (oceanRepository.selectCountAllBait(userIdInSession).equals(0)) {
            return "가용 미끼가 존재하지 않습니다!";

        } else if (oceanRepository.selectCountAllBait(userIdInSession) != null && Integer.parseInt(oceanRepository.selectCountAllBait(userIdInSession).toString()) > 0 && fishingService.seclectRowByUserIdTargetId(userIdInSession, targetUserId) == 0) {
            // 가용 미끼가 있으면  무료 미끼 먼저 소진
            //무료 미끼 있냐?
            if (oceanRepository.selectCountFreeBait(userIdInSession) > 0) {
                // 무료 미끼 있으면
                // 무료미끼 하나 삭제
                oceanRepository.minusFreeBait(userIdInSession);
            } else {
                // 무료미끼 없으면 유료미끼 하나 삭제
                oceanRepository.minusNotFreeBait(userIdInSession);
            }
            // 그 낚시 테이블에 정보 기록
            fishingService.insertFishingInfo(userIdInSession, targetUserId);
            deleteAqua(userIdInSession, targetUserId);
            return "미끼를 성공적으로 던졌습니다! \n 물고기의 반응을 기다려주세요!";

        } else if (fishingService.seclectRowByUserIdTargetId(userIdInSession, targetUserId) != 0) {
            //이미 그 물고기에게 미끼를 던졌으면?
            return "해당 유저에게 미끼를 던진 과거 기록이 있습니다!";
        }
        return "/aquarium";
    }

    @Override
    public fishing selectRowByUserId(int fishermenId) {

        return fishingRepository.selectFishingStatusByUserId(fishermenId);
    }
}
