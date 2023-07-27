package com.example.geumodoIsland.admin.service;

import java.util.List;
import java.util.Map;

import com.example.geumodoIsland.admin.model.Notice;
import com.example.geumodoIsland.admin.model.Report;
import com.example.geumodoIsland.admin.model.UserPhotos;
import com.example.geumodoIsland.admin.model.UserWithPhoto;

public interface IAdminService {

    List<Report> getAllReports();  // 신고 내용 조회 (전체)
    void changeReportStatus(int reportId, String reportStatus);  // 신고 상태 변경
    void insertReport(int targetId, int reporterId, String reportContent);

    List<UserWithPhoto> getAllUsersWithPhotos();
    List<UserPhotos> getAllUserWithPhotos(int userId);
    void deletePhoto(int photoId, int userId);

    List<Notice> getNoticesByUserId(int userId);
    List<Notice> getNoticesByReportId(int userId);
    void saveNoticeFromReport(Report report);
    void setReportCreatedAtForNotices(List<Notice> notices);

}


