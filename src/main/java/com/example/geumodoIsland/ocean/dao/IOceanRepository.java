package com.example.geumodoIsland.ocean.dao;

import com.example.geumodoIsland.user.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface IOceanRepository {
    List<User> selectFishListByLocal(@Param("userId") int userId);
    Object selectCountAllBait(@Param("userId") int loginUserId);
    int selectCountFreeBait(@Param("userId") int loginUserId);
    int selectCountNotFreeBait(@Param("userId") int loginUserId);
    void minusNotFreeBait(@Param("userId") int loginUserId);
    void minusFreeBait(@Param("userId") int loginUserId);
    void resetFreeBait();
}
