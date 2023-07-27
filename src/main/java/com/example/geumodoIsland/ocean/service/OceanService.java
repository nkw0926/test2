package com.example.geumodoIsland.ocean.service;


import com.example.geumodoIsland.fishing.service.IFishingService;
import com.example.geumodoIsland.ocean.dao.IOceanRepository;
import com.example.geumodoIsland.user.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OceanService implements IOceanService {
    @Autowired
    IOceanRepository oceanRepository;
    @Autowired
    IFishingService fishingService;

    @Override
    public List<User> selectFishListByLocal(int userId) {
        return oceanRepository.selectFishListByLocal(userId);
    }

    @Override
    public Object selectCountAllBait(int loginUserId) {
        return oceanRepository.selectCountAllBait(loginUserId);
    }

    @Override
    public int selectCountFreeBait(int loginUserId) {
        return oceanRepository.selectCountFreeBait(loginUserId);
    }

    @Override
    public int selectCountNotFreeBait(int loginUserId) {
        return oceanRepository.selectCountNotFreeBait(loginUserId);
    }

    @Override
    public void minusNotFreeBait(int loginUserId) {
        oceanRepository.minusNotFreeBait(loginUserId);
    }

    @Transactional

    public void minusFreeBait(int loginUserId) {
        oceanRepository.minusFreeBait(loginUserId);
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")
    public void resetFreeBait() {
        oceanRepository.resetFreeBait();
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
            return "미끼를 성공적으로 던졌습니다! \n 물고기의 반응을 기다려주세요!";

        } else if (fishingService.seclectRowByUserIdTargetId(userIdInSession, targetUserId) != 0) {
            //이미 그 물고기에게 미끼를 던졌으면?
            return "해당 유저에게 미끼를 던진 과거 기록이 있습니다!";
        }
        return "/ocean/userDetail?userId=" + targetUserId;
    }
}
