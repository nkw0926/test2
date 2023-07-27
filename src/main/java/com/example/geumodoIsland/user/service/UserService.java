package com.example.geumodoIsland.user.service;

import com.example.geumodoIsland.photo.dao.IPhotoRepository;
import com.example.geumodoIsland.user.dao.IUserRepository;
import com.example.geumodoIsland.user.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService implements IUserService {
    @Autowired
    IUserRepository userRepository;

    @Autowired
    IPhotoRepository photoRepository;

    @Override
    public List<User> selectALLFishList() {
        return userRepository.selectAllFishList();
    }

    @Override
    public Map<String, Object> selectAUserInfo(int userId) {
        User userInfo = userRepository.selectAUserInfo(userId);
        String[] userHobbies = userInfo.getUserHobbies().split(",");
        Map<String, Object> userInfoMap = new HashMap<>();
        if( userInfo.getUserIntroductions() != null){
            String[] userIntroductions =  userInfo.getUserIntroductions().split(",");
            userInfoMap.put("userIntroductions", userIntroductions);
        }

        userInfoMap.put("userInfo", userInfo);
        userInfoMap.put("userHobbies", userHobbies);

        return userInfoMap;

    }

    @Override
    public List<User> selectFishListByAddress(String loginUserAddress, char loginUserSex, int userId) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("userId", userId);
        map.put("loginUserAddress", loginUserAddress);
        map.put("loginUserSex", loginUserSex);

        return userRepository.selectFishListByAddress(map);
    }

    @Transactional
	public void insertIntoUser(User user, String userEmail, List<String> photoFileNames) {
    	List<UserPhoto> userPhotos = new ArrayList<UserPhoto>();

		userRepository.insertIntoUser(user);
		int userId = userRepository.selectUserIdByUserEmail(userEmail);

		photoFileNames.stream().forEach((photoFileName) ->{
			UserPhoto userPhoto = new UserPhoto();
			userPhoto.setUserId(userId);
			userPhoto.setPhotoFileName(photoFileName);
			userPhotos.add(userPhoto);
		});

		userPhotos.stream().forEach((userPhoto) -> {
			photoRepository.insertUserPhoto(userPhoto);
		});

		UserBait userBait = new UserBait();
		userBait.setUserId(userId);

		userRepository.insertIntoBait(userBait);
	}

	@Override
	public int emailCheck(String userEmail) {
		return userRepository.emailCheck(userEmail);
	}

	@Override
	public UserIdAndPassword selectUserPasswordAndUserIdByUserEmail(String userEmail) {
		return userRepository.selectUserPasswordAndUserIdByUserEmail(userEmail);
	}

    @Override
    public List<User> selectFishListByCondition(int loginUserId, char loginUserSex,List<String> locationList, List<Integer> bornYearList,List<String> hobbyList,List<String> personalityList) {
        return userRepository.selectFishListByCondition(loginUserId, loginUserSex,locationList, bornYearList, hobbyList, personalityList);
    }

	@Override
	public UserProfile selectUserProfileByUserId(int userId) {
		UserProfile userProfile = userRepository.selectUserProfileByUserId(userId);
		List<String> userPhotoFileNames = photoRepository.selectUserPhoto(userId);
		userProfile.setUserPhotoFileNames(userPhotoFileNames);
		return userProfile;
	}

	@Override
	public Integer selectUserIdByUserEmail(String userEmail) {
		return userRepository.selectUserIdByUserEmail(userEmail);
	}

	@Override
	public void updateUserPassword(UserUpdatePassword userUpdatePassword) {
		userRepository.updateUserPassword(userUpdatePassword);
	}

	@Override
	public List<UserBarChart> selectALLFishListBySexAddress() {
		return userRepository.selectALLFishListBySexAddress();
	}

}
