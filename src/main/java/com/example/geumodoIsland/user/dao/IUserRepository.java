package com.example.geumodoIsland.user.dao;

import com.example.geumodoIsland.user.model.*;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
@Mapper
public interface IUserRepository {
    List<User> selectAllFishList();

    User selectAUserInfo(int userId);

    void insertIntoUser(User user);

    int emailCheck(String userEmail);

    Integer selectUserIdByUserEmail(String userEmail);

    UserIdAndPassword selectUserPasswordAndUserIdByUserEmail(String userEmail);

    List<User> selectFishListByAddress(HashMap<String, Object> map);

    List<User> selectFishListByCondition(@Param("loginUserId") int loginUserId, @Param("loginUserSex") char loginUserSex,@Param("locationList") List<String> locationList, @Param("ageList") List<Integer> bornYearList, @Param("hobbyList") List<String> hobbyList, @Param("personalityList") List<String> personalityList);

    UserProfile selectUserProfileByUserId(int userId);

    void insertIntoBait(UserBait userBait);

    void updateUserPassword(UserUpdatePassword userUpdatePassword);

    List<UserBarChart> selectALLFishListBySexAddress();
}
