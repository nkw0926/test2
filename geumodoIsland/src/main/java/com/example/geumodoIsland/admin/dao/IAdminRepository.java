package com.example.geumodoIsland.admin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.geumodoIsland.admin.model.Notice;
import com.example.geumodoIsland.admin.model.Report;
import com.example.geumodoIsland.photo.model.Photo;



@Repository
@Mapper
public interface IAdminRepository {

    List<Report> getAllReports();  // 신고 내용 조회 (전체)
    void changeReportStatus(@Param("reportId") int reportId, @Param("reportStatus") String reportStatus);  // 신고 상태 변경
    void insertReport(int targetId, int reporterId,  String reportContent);
    void insertReport(Report report);

    List<Photo> getAllPhotos();
    void deletePhoto(int photoId);

    List<Notice> getNoticesByTargetId(int targetId);
    void saveNotice(Notice notice);

    List<Map<String, Object>> getUserCountByGender();
}
