package com.example.geumodoIsland.aquarium.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.geumodoIsland.aquarium.model.Aquarium;

@Repository
@Mapper
public interface IAquaRepository {
    List<String> selectFishList(int fishId);
    void deleteAqua(@Param("userId") int loginUserId,@Param("targetUserId") int targetUserId );
    int selectRowByUserIdTargetId(@Param("userId") int loginUserId, @Param("targetUserId") int targetUserId);

    void insertAquarium(@Param("userId") int loginUserId, @Param("targetUserId") int targetUserId);
}
