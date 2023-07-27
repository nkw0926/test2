package com.example.geumodoIsland.fishing.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.geumodoIsland.fishing.dao.IBaitingRepository;
import com.example.geumodoIsland.fishing.model.Baiting;

@Service
public class BaitingService implements IBaitingService{

	@Autowired
    IBaitingRepository baitingRepository;
	
	@Override //미끼 던진 내역
	public List<Baiting> showBaitingList(int fisherMenId) {
		List<Baiting> baitingList = baitingRepository.showBaitingList(fisherMenId);
		 if (baitingList == null) {
			 baitingList = new ArrayList<>();
		}
		return baitingList;
		}
	
	@Override //미끼 받은 내역
	public List<Baiting> showBaitedList(int fishId) {
		List<Baiting> baitedList = baitingRepository.showBaitedList(fishId);
		 if (baitedList == null) {
			 baitedList = new ArrayList<>();
		}
		return baitedList;
		}

	@Override
    public String getPhoneNumberWhenBaited(int fishId) {
        // 물고기 정보 조회 및 물고기 상태 확인
        Baiting fish = baitingRepository.findById(fishId);

        if (fish != null && fish.getBaitingStatus().equals("S")) {
            // 물고기가 미끼를 물었을 경우, 물고기(사용자)의 전화번호를 반환
            return baitingRepository.getPhoneNumberWhenBaited(fishId);
        }

        return null; // 물고기가 미끼를 물지 않았거나 전화번호가 없을 경우 null 반환
    }
	 
	@Override
    public void deleteBaiting(int baitingId) {
        baitingRepository.deleteBaiting(baitingId);
    }

	
	@Override
	public String getFishName(int fishId) {
		return baitingRepository.getFishName(fishId);
	}

	@Override
	public Integer getFishAge(int fishId) {
		return baitingRepository.getFishAge(fishId);
	}
	
	@Override
	public void acceptBaiting(int baitingId) {
	    baitingRepository.updateBaitingStatusToS(baitingId);
	}

	@Override
	public void rejectBaiting(int baitingId) {
	    baitingRepository.updateBaitingStatusToF(baitingId);
	}
}
