package com.example.geumodoIsland.admin.controller;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.example.geumodoIsland.admin.model.*;
import com.example.geumodoIsland.user.model.User;
import com.example.geumodoIsland.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.geumodoIsland.admin.service.IAdminService;

import jakarta.servlet.http.HttpSession;
// 커밋 테스트

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    IUserService userService;
    @Autowired
    IAdminService adminService;


    @GetMapping("/main")
    public String adminMain(Model model) {
        List<User> allUserList = userService.selectALLFishList();
        model.addAttribute("allUser", allUserList);
        return "admin/main";
    }

    @GetMapping("/reports")
    public String getAllReports(Model model) {
        List<Report> reports = adminService.getAllReports();
        // Report ID를 기준으로 정렬
        reports = reports.stream()
                .sorted(Comparator.comparingInt(Report::getReportId))
                .collect(Collectors.toList());
        model.addAttribute("reports", reports);
//        return "admin/admin-reports";
        return "admin/r-test";
    }

    @PostMapping("/reports/change-status")
    public String changeReportStatus(@RequestParam int reportId, @RequestParam String reportStatus, RedirectAttributes redirectAttributes) {
        adminService.changeReportStatus(reportId, reportStatus);
        redirectAttributes.addFlashAttribute("message", "신고 처리 상태가 변경되었습니다.");
        return "redirect:/admin/reports";
    }


    @GetMapping("/submit")
    public String showSubmitPage(@RequestParam("targetId") int targetId, Model model) {
        // targetId 값을 model에 추가하여 submit.html 페이지로 전달
        List<String> reportContents = new ArrayList<>();
        reportContents.add("부적절한 언어 사용으로 신고");
        reportContents.add("스팸 또는 광고 무단 게시로 신고");
        reportContents.add("허위 정보 게시로 신고");
        reportContents.add("사기 또는 부정 행위로 신고");
        reportContents.add("저작권 위반으로 신고");
        reportContents.add("명예 훼손 및 비방으로 신고");
        reportContents.add("다른 사용자의 개인정보 노출로 신고");
        reportContents.add("불법적인 활동 진행으로 신고");
        model.addAttribute("reportContents", reportContents);
        model.addAttribute("targetId", targetId);
        model.addAttribute("userState", "로그아웃");
//        return "admin/submit";
        return "admin/s-test";
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public String submitReport(@RequestParam("targetId") int targetId, String reportContent,
                               HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        if (reportContent == null) {
            redirectAttributes.addFlashAttribute("message", "신고 사유를 선택해주세요!");
            return "redirect:/admin/submit?targetId=" + targetId;
        } else {
            int reporterId = (int) session.getAttribute("userId"); //나중에 이걸로!!
//             int reporterId = 10; //임시로 지정


            // targetId, reporterId, reportContent를 사용하여 신고 기능 처리
            adminService.insertReport(targetId, reporterId, reportContent);
            model.addAttribute("message", "신고가 완료되었습니다.");
            model.addAttribute("searchUrl", "/ocean/all");

            return "/admin/message";
        }
    }

    @GetMapping("/photos")
    public String getAllUsersWithPhotos(Model model) {
        List<UserWithPhoto> allUsersWithPhotos = adminService.getAllUsersWithPhotos();
        List<UserWithPhotos> allUsersWithPhotosFinal = new ArrayList<UserWithPhotos>();
        allUsersWithPhotos.stream().forEach((user) -> {
            List<UserPhotos> userPhotoFileNames = adminService.getAllUserWithPhotos(user.getUserId());
            UserWithPhotos userWithPhotos = UserWithPhotos.builder()
                    .userId(user.getUserId())
                    .userName(user.getUserName())
                    .userEmail(user.getUserEmail())
                    .photos(userPhotoFileNames)
                    .build();
            allUsersWithPhotosFinal.add(userWithPhotos);
        });

        model.addAttribute("allUsersWithPhotosFinal", allUsersWithPhotosFinal);
//        return "admin/admin-user-photos";
        return "admin/p-test";
    }

    @PostMapping("/photos/delete/{photoId}/{userId}")
    public String deletePhoto(@PathVariable int photoId, @PathVariable int userId, RedirectAttributes redirectAttributes) {

        adminService.deletePhoto(photoId, userId);
        redirectAttributes.addFlashAttribute("message", "사진이 삭제되었습니다.");
        return "redirect:/admin/photos";
    }

    @GetMapping("/notices")
    public String getNoticesForUser(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String userIdInSession = String.valueOf(session.getAttribute("userId"));

        if (userIdInSession == "null") {
            redirectAttributes.addFlashAttribute("userState", "로그인");
            return "redirect:/user/login";
        } else {
            int userId = Integer.valueOf(userIdInSession);
            List<Notice> notices = adminService.getNoticesByUserId(userId);
            Collections.sort(notices, (n1, n2) -> n2.getCreatedAt().compareTo(n1.getCreatedAt())); //정렬
            adminService.setReportCreatedAtForNotices(notices); //reportCreatedAt 값을 notice의 createdAt으로 설정
            model.addAttribute("notices", notices);
            model.addAttribute("userState", "로그아웃");
//        return "admin/admin-notices";
            return "admin/n-test";
        }
    }


    @GetMapping("/agerange/pieChart")
    @ResponseBody
    public List<AgeRange> getPieChartAgeRange() {
        List<User> allUser = userService.selectALLFishList();
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        int formatedNow = Integer.parseInt(formatter.format(now));
        int earlyTwenties = 0;
        int lateTwenties = 0;
        int earlyThirties = 0;
        int lateThirties = 0;
        int old = 0;

        for (int i = 0; i < allUser.size(); i++) {
            int bornYear = allUser.get(i).getUserAge();
            if (20 <= (formatedNow - bornYear) && (formatedNow - bornYear) <= 24) {
                earlyTwenties++;
            } else if (25 <= (formatedNow - bornYear) && (formatedNow - bornYear) <= 29) {
                lateTwenties++;
            } else if (30 <= (formatedNow - bornYear) && (formatedNow - bornYear) <= 34) {
                earlyThirties++;
            } else if (35 <= (formatedNow - bornYear) && (formatedNow - bornYear) <= 39) {
                lateThirties++;
            } else {
                old++;
                //DB에 값 이상하게 들어가있다 나이는 나이로 다 바꿔
            }
        }

        List<AgeRange> ageRangeList = new ArrayList<>();
        ageRangeList.add(new AgeRange("earlyTwenties", earlyTwenties));
        ageRangeList.add(new AgeRange("lateTwenties", lateTwenties));
        ageRangeList.add(new AgeRange("earlyThirties", earlyThirties));
        ageRangeList.add(new AgeRange("lateThirties", lateThirties));
        ageRangeList.add(new AgeRange("old", old));

        return ageRangeList;
    }

    @GetMapping("/sex/pieChart")
    @ResponseBody
    public List<Sex> getPieChartSex() {
        List<User> allUser = userService.selectALLFishList();

        int femaleCount = 0;
        int maleCount = 0;

        for (int i = 0; i < allUser.size(); i++) {
            char sex = allUser.get(i).getUserSex();
            if (sex == 'F') {
                femaleCount++;
            } else {
                maleCount++;
            }
        }

        List<Sex> sexList = new ArrayList<>();
        sexList.add(new Sex("femaleCount", femaleCount));
        sexList.add(new Sex("maleCount", maleCount));

        return sexList;
    }

    @GetMapping("/agerange/sex/barChart")
    @ResponseBody
    public List<Object> getPieChartAgeRangeSex() {

        List<User> allUser = userService.selectALLFishList();

        int e2FemaleCount = 0;
        int l2FemaleCount = 0;
        int e3FemaleCount = 0;
        int l3FemaleCount = 0;
        int oldFCount = 0;

        int e2MaleCount = 0;
        int l2MaleCount = 0;
        int e3MaleCount = 0;
        int l3MaleCount = 0;
        int oldMCount = 0;

        for (int i = 0; i < allUser.size(); i++) {
            Date now = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
            int formatedNow = Integer.parseInt(formatter.format(now));
            char sex = allUser.get(i).getUserSex();
            int bornYear = allUser.get(i).getUserAge();
            if (20 <= (formatedNow - bornYear) && (formatedNow - bornYear) <= 24) {
                if (sex == 'F') {
                    e2FemaleCount++;
                } else {
                    e2MaleCount++;
                }
            } else if (25 <= (formatedNow - bornYear) && (formatedNow - bornYear) <= 29) {
                if (sex == 'F') {
                    l2FemaleCount++;
                } else {
                    l2MaleCount++;
                }
            } else if (30 <= (formatedNow - bornYear) && (formatedNow - bornYear) <= 34) {
                if (sex == 'F') {
                    e3FemaleCount++;
                } else {
                    e3MaleCount++;
                }
            } else if (35 <= (formatedNow - bornYear) && (formatedNow - bornYear) <= 39) {
                if (sex == 'F') {
                    l3FemaleCount++;
                } else {
                    l3MaleCount++;
                }
            } else {
                if (sex == 'F') {
                    oldFCount++;
                } else {
                    oldMCount++;
                }
                //DB에 값 이상하게 들어가있다 나이는 나이로 다 바꿔
            }
        }

        List<Integer> femaleCountList = new ArrayList<>();
        femaleCountList.add(e2FemaleCount);
        femaleCountList.add(l2FemaleCount);
        femaleCountList.add(e3FemaleCount);
        femaleCountList.add(l3FemaleCount);
        femaleCountList.add(oldFCount);

        List<Integer> maleCountList = new ArrayList<>();
        maleCountList.add(-e2MaleCount);
        maleCountList.add(-l2MaleCount);
        maleCountList.add(-e3MaleCount);
        maleCountList.add(-l3MaleCount);
        maleCountList.add(-oldMCount);

        List<Object> sexCountList = new ArrayList<>();
        sexCountList.add(femaleCountList);
        sexCountList.add(maleCountList);
        return sexCountList;
    }

    // 로그인, 로그아웃 처리
 	@PostMapping("/getUserState")
 	public @ResponseBody String getUserState(@RequestParam("userState") String userState, HttpSession session) {
 		System.out.println(userState);
 		if (userState.equals("로그인")) {
 			return "로그인";
 		} else {
 			session.invalidate();
 			return "로그아웃";
 		}
 	}
}

