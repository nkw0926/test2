package com.example.geumodoIsland.ocean.service;

import com.example.geumodoIsland.user.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IOceanService {
    List<User> selectFishListByLocal(int userId);

    Object selectCountAllBait(int loginUserId);

    int selectCountFreeBait(int loginUserId);

    int selectCountNotFreeBait(int loginUserId);
    void minusNotFreeBait(int loginUserId);
    void minusFreeBait(int loginUserId);
    void resetFreeBait();
    public String throwBait(int userIdInSession, int targetUserId) ;
}
