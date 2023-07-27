package com.example.geumodoIsland.fishing.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface IFishingRepository {

    void insertFishingInfo(@Param("userId") int loginUserId,@Param("targetUserId") int targetUserId );

    int seclectRowByUserIdTargetId(@Param("userId") int loginUserId,@Param("targetUserId") int targetUserId );

    Object seclectFishingStatus(@Param("userId") int loginUserId,@Param("targetUserId") int targetUserId );
}
