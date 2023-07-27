package com.example.geumodoIsland.admin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.geumodoIsland.admin.model.Notice;
import com.example.geumodoIsland.admin.model.Report;
import com.example.geumodoIsland.admin.model.UserPhotos;
import com.example.geumodoIsland.admin.model.UserWithPhoto;



@Repository
@Mapper
public interface IAdminRepository {

    List<Report> getAllReports();  // 신고 내용 조회 (전체)
    void changeReportStatus(@Param("reportId") int reportId, @Param("reportStatus") String reportStatus);  // 신고 상태 변경
    void insertReport(int targetId, int reporterId,  String reportContent); // 신고 하기
    void insertReport(Report report);

    List<UserWithPhoto> getAllUsersWithPhotos();
    List<UserPhotos> getAllUserWithPhotos(int userId);
    void deletePhoto(int photoId); // 사진 삭제

    List<Notice> getNoticesByTargetId(int targetId);
    List<Notice> getNoticesByReportId(int targetId);
    void saveNotice(Notice notice);
    void changeTargetStatus(int userId);
    void insertNotice(int userId);

}
