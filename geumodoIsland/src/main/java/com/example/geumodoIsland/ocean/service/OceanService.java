package com.example.geumodoIsland.ocean.service;


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

    @Override
    public List<User> selectFishListByLocal(int userId) {
        return oceanRepository.selectFishListByLocal(userId);
    }

    @Override
    public int selectCountAllBait(int loginUserId) {
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

    @Override
    public void minusFreeBait(int loginUserId) {
        oceanRepository.minusFreeBait(loginUserId);
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")
	public void resetFreeBait() {
		oceanRepository.resetFreeBait();
	}

}
