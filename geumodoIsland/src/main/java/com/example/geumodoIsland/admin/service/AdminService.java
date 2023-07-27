package com.example.geumodoIsland.admin.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.geumodoIsland.admin.dao.IAdminRepository;
import com.example.geumodoIsland.admin.model.Notice;
import com.example.geumodoIsland.admin.model.Report;
import com.example.geumodoIsland.photo.model.Photo;

@Service
public class AdminService implements IAdminService {

    private final IAdminRepository adminRepository;

    @Autowired
    public AdminService(IAdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Transactional
    public List<Report> getAllReports() {
        return adminRepository.getAllReports();
    }

    @Transactional
    public void changeReportStatus(int reportId, String reportStatus) {
        adminRepository.changeReportStatus(reportId, reportStatus);
    }

    @Transactional
    public void insertReport(int targetId, int reporterId, String reportContent) {
        Report report = new Report();
        report.setTargetId(targetId);
        report.setReporterId(reporterId);
        report.setReportContent(reportContent);
        adminRepository.insertReport(report);
    }

    @Transactional
    public List<Photo> getAllPhotos() {
        return adminRepository.getAllPhotos();
    }

    @Transactional
    public Map<Integer, List<Photo>> getAllPhotosByUserId() {
        List<Photo> allPhotos = adminRepository.getAllPhotos();
        return allPhotos.stream().collect(Collectors.groupingBy(Photo::getUserId));
    }

    @Transactional
    public void deletePhoto(int photoId) {
        adminRepository.deletePhoto(photoId);
    }

    @Transactional
    public List<Notice> getNoticesByUserId(int userId) {
        return adminRepository.getNoticesByTargetId(userId);
    }

    @Transactional
    public void saveNoticeFromReport(Report report) {
        // Report에서 필요한 정보를 추출하여 Notice 객체 생성
        Notice notice = new Notice();
        notice.setUserId(report.getTargetId()); // report의 targetId를 userId로 저장
        notice.setNoticeContent(report.getReportContent()); // report의 reportContent를 noticeContent로 저장
        // Notice 객체를 저장
        adminRepository.saveNotice(notice);
    }

    @Transactional
    public void setReportCreatedAtForNotices(List<Notice> notices) {
        for (Notice notice : notices) {
            Timestamp reportCreatedAt = notice.getReportCreatedAt();
            if (reportCreatedAt != null) {
                notice.setCreatedAt(reportCreatedAt);
            }
        }
    }
    @Transactional
    public List<Map<String, Object>> getUserCountByGender() {
        return adminRepository.getUserCountByGender();
    }

}
