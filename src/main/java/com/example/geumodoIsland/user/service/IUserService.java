package com.example.geumodoIsland.user.service;

import com.example.geumodoIsland.user.model.User;
import com.example.geumodoIsland.user.model.UserIdAndPassword;
import com.example.geumodoIsland.user.model.UserProfile;
import com.example.geumodoIsland.user.model.UserUpdatePassword;

import java.util.List;
import java.util.Map;

public interface IUserService {
    public List<User> selectALLFishList();
    public Map<String, Object> selectAUserInfo(int userId) ;
    public List<User> selectFishListByAddress(String loginUserAddress, char loginUserSex, int userId);
    
    // 회원가입
    public void insertIntoUser(User user, String userEmail, List<String> photoFileNames);
    public int emailCheck(String userEmail);
    
    // 로그인
    public UserIdAndPassword selectUserPasswordAndUserIdByUserEmail(String userEmail);
    
    public List<User> selectFishListByCondition(int loginUserId, char loginUserSex,List<String> locationList, List<Integer> bornYearList,List<String> hobbyList,List<String> personalityList) ;

    public UserProfile selectUserProfileByUserId(int userId);
    
    public Integer selectUserIdByUserEmail(String userEmail);
    
    void updateUserPassword(UserUpdatePassword userUpdatePassword);

}
