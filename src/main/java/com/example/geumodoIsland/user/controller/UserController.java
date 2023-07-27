package com.example.geumodoIsland.user.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.geumodoIsland.fishing.model.Baiting;
import com.example.geumodoIsland.user.model.User;
import com.example.geumodoIsland.user.model.UserFirstStep;
import com.example.geumodoIsland.user.model.UserFourthStep;
import com.example.geumodoIsland.user.model.UserIdAndPassword;
import com.example.geumodoIsland.user.model.UserLastStep;
import com.example.geumodoIsland.user.model.UserLogin;
import com.example.geumodoIsland.user.model.UserProfile;

import com.example.geumodoIsland.user.model.UserSecondStep;
import com.example.geumodoIsland.user.model.UserThirdStep;
import com.example.geumodoIsland.user.model.UserUpdatePassword;
import com.example.geumodoIsland.user.service.IUserService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

	static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private String totalChooseHobbies = "";
	private String totalChooseIntroductions = "";
	private int fileIsNotEmptyCount = 0 ;

	@Autowired
	JavaMailSender javaMailSender;

	@Autowired
	IUserService userService;

	@Autowired
	PasswordEncoder passwordEncoder;


	// 회원가입...(1~5단계)

	@GetMapping("/signup/firststep")
	public String signUpFirstStep(UserFirstStep userFirstStep,HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		model.addAttribute("session",session);
		return "user/signupFirstStep";
	}

	@PostMapping("/signup/firststep")
	public String signUpFirstStep(UserFirstStep userFirstStep, HttpSession session) {
		session.setAttribute("userEmail", userFirstStep.getUserEmail());
		session.setAttribute("userName", userFirstStep.getUserName());
		session.setAttribute("userPassword", userFirstStep.getUserPassword());
		logger.info("userEmail: " + String.valueOf(session.getAttribute("userEmail")) + ", " +
				"userName: " + String.valueOf(session.getAttribute("userName")) );
		return "redirect:/user/signup/secondstep";
	}

	@GetMapping("/signup/secondstep")
	public String signUpSecondStep(UserSecondStep userSecondStep) {
		return "user/signUpSecondStep";
	}

	@PostMapping("/signup/secondstep")
	public String signUpSecondStep(UserSecondStep userSecondStep, HttpSession session) {
		session.setAttribute("userSex", userSecondStep.getUserSex());
		session.setAttribute("userAge", userSecondStep.getUserAge());
		session.setAttribute("userHeight", Integer.parseInt(userSecondStep.getUserHeight()));
		session.setAttribute("userBody", userSecondStep.getUserBody());
		session.setAttribute("userAddress", userSecondStep.getUserAddress());
		session.setAttribute("userPhoneNumber", userSecondStep.getUserPhoneNumber());
		session.setAttribute("userJob", userSecondStep.getUserJob());

		logger.info("userAge: " + String.valueOf(session.getAttribute("userAge")) + ", " +
				"userSex: " + String.valueOf(session.getAttribute("userSex")) + ", " +
				"userHeight: " + String.valueOf(session.getAttribute("userHeight")) + ", " +
				"userBody: " + String.valueOf(session.getAttribute("userBody")) + ", " +
				"userAddress: " + String.valueOf(session.getAttribute("userAddress")) + ", " +
				"userPhoneNumber: " + String.valueOf(session.getAttribute("userPhoneNumber")) + ", " +
				"userJob: " + String.valueOf(session.getAttribute("userJob")));
		return "redirect:/user/signup/thirdstep";
	}


	@GetMapping("/signup/thirdstep")
	public String signUpThirdStep(UserThirdStep userThirdStep, Model model) {
		List<String> hobbies = new ArrayList<>();
		hobbies.add("운동");
		hobbies.add("독서");
		hobbies.add("산책");
		hobbies.add("음악감상");
		hobbies.add("영화/드라마감상");
		hobbies.add("노래부르기");
		hobbies.add("춤추기");
		hobbies.add("공예/만들기");
		hobbies.add("맛집탐방");
		model.addAttribute("hobbies", hobbies);
		return "user/signUpThirdStep";
	}


	@PostMapping("/signup/thirdstep")
	public String signUpThirdStep(UserThirdStep userThirdStep, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
		totalChooseHobbies = "";
		List<String> chooseHobbies = userThirdStep.getChooseHobbies();
		if (chooseHobbies.size() < 3) {
			redirectAttributes.addFlashAttribute("message", "취미 키워드를 3개 이상 골라주세요");
			return "redirect:/user/signup/thirdstep";
		} else {
			chooseHobbies.stream().forEach((hobby) -> {
				totalChooseHobbies += "," + hobby;
			});
			String userHobbies = totalChooseHobbies.substring(1,totalChooseHobbies.length());
			session.setAttribute("userHobbies", userHobbies);
			logger.info("userHobbies: " + String.valueOf(session.getAttribute("userHobbies")));
			return "redirect:/user/signup/fourthstep";
		}
	}


	@GetMapping("/signup/fourthstep")
	public String signUpFourthStep(UserFourthStep userFourthStep, Model model) {
		List<String> introductions = new ArrayList<>();
		introductions.add("다정다감");
		introductions.add("참을성만땅");
		introductions.add("애교쟁이");
		introductions.add("예의바른");
		introductions.add("소심소심");
		introductions.add("이끌어가는");
		introductions.add("감성폭발");
		introductions.add("똑똑이");
		introductions.add("입답천재");
		introductions.add("긍정에너지");
		introductions.add("인싸그잡채");
		model.addAttribute("introductions", introductions);
		return "user/signUpFourthStep";
	}


	@PostMapping("/signup/fourthstep")
	public String signUpFourthStep(UserFourthStep userFourthStep, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
		totalChooseIntroductions = "";
		List<String> chooseIntroductions = userFourthStep.getChooseIntrouctions();
		if (chooseIntroductions.size() < 3) {
			redirectAttributes.addFlashAttribute("message", "소개 키워드를 3개 이상 골라주세요");
			return "redirect:/user/signup/fourthstep";
		} else {
			chooseIntroductions.stream().forEach((introduction) -> {
				totalChooseIntroductions += "," + introduction;
			});
			String userIntroductions = totalChooseIntroductions.substring(1,totalChooseIntroductions.length());
			session.setAttribute("userIntroductions", userIntroductions);
			logger.info("userIntroductions: " + String.valueOf(session.getAttribute("userIntroductions")));
			return "redirect:/user/signup/laststep";
		}
	}


	@GetMapping("/signup/laststep")
	public String signUpLastStep() {
		return "user/signUpLastStep";
	}

	@PostMapping("/signup/laststep")
	public String signUpLastStep(UserLastStep userLastStep, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
		fileIsNotEmptyCount = 0;
		List<MultipartFile> files = userLastStep.getFiles();
		List<String> photoFileNames = new ArrayList<String>();

		files.stream().forEach((file) -> {
			if (!file.isEmpty()) {
				fileIsNotEmptyCount +=1;
			}
		});

		if(fileIsNotEmptyCount < 2 ) {
			redirectAttributes.addFlashAttribute("message", "프로필이미지를 2개 이상 골라주세요");
			return "redirect:/user/signup/laststep";
		}

		for(int i=0; i < files.size(); i++) {
			String userEmail = String.valueOf(session.getAttribute("userEmail"));
			String photoFileName = userEmail + "_" + (i+1) + ".jpg";
			session.setAttribute("photo" + (i+1), photoFileName);
			logger.info("photo" + (i+1) + ": " + String.valueOf(session.getAttribute("photo" + (i+1))));

			photoFileNames.add(photoFileName);
		}

		// db에 회원가입 정보 저장
		User user = User.builder()
				.userEmail(String.valueOf(session.getAttribute("userEmail")))
				.userName(String.valueOf(session.getAttribute("userName")))
				.userPassword(passwordEncoder.encode(String.valueOf(session.getAttribute("userPassword"))))
				.userAge(Integer.valueOf(String.valueOf(session.getAttribute("userAge")).substring(0,4)))
				.userSex(String.valueOf(session.getAttribute("userSex")).charAt(0))
				.userAddress(String.valueOf(session.getAttribute("userAddress")))
				.userHeight(Integer.valueOf(String.valueOf(session.getAttribute("userHeight"))))
				.userBody(String.valueOf(session.getAttribute("userBody")))
				.userPhoneNumber(String.valueOf(session.getAttribute("userPhoneNumber")))
				.userJob(String.valueOf(session.getAttribute("userJob")))
				.userHobbies(String.valueOf(session.getAttribute("userHobbies")))
				.userIntroductions(String.valueOf(session.getAttribute("userIntroductions")))
				.build();

		// 회원정보 db 저장
		userService.insertIntoUser(user,String.valueOf(session.getAttribute("userEmail")), photoFileNames);

		// save local
		for(int i=0; i < files.size(); i++) {
			String userEmail = String.valueOf(session.getAttribute("userEmail"));
			String photoFileName = userEmail + "_" + (i+1) + ".jpg";
			session.setAttribute("photo" + (i+1), photoFileName);
			logger.info("photo" + (i+1) + ": " + String.valueOf(session.getAttribute("photo" + (i+1))));

			photoFileNames.add(photoFileName);

			MultipartFile file = files.get(i);
			String savePath = "C:/Users/KOSA/git/Ocean/geumodoIsland/src/main/resources/static/ocean/assets/img/userImg/" + userEmail;
			try {
				File rootFile = new File(savePath);
				rootFile.mkdirs();
				String realFileRoot = savePath + File.separator + photoFileName;
				file.transferTo(new File(realFileRoot));
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		// 세션 종료
		session.invalidate();

		return "redirect:/user/login";
	}

	// 이메일 중복체크
	@PostMapping("/signup/emailcheck")
	public @ResponseBody int emailCheck(@RequestParam("userEmail") String userEmail) {
		return userService.emailCheck(userEmail);
	}

	// 로그인
	@GetMapping("/login")
	public String login(UserLogin userLogin) {
		return "user/login";
	}

	// 로그인, 로그아웃 처리
	@PostMapping("/getUserState")
	public @ResponseBody String getUserState(@RequestParam("userState") String userState, HttpSession session) {

		if (userState.equals("로그인")) {
			return "로그인";
		} else {
			session.invalidate();
			return "로그아웃";
		}
	}

	@PostMapping("/login")
	public String login(UserLogin userLogin, RedirectAttributes redirectAttributes, HttpSession session) {
		String userEmail = userLogin.getUserEmail();
		try {
			UserIdAndPassword userIdAndPassword = userService.selectUserPasswordAndUserIdByUserEmail(userEmail);
			if(passwordEncoder.matches(userLogin.getUserPassword(), userIdAndPassword.getUserPassword())) {
				if(userIdAndPassword.getUserStatus() == 'A') {
					session.setAttribute("userId", userIdAndPassword.getUserId());
					logger.info("userId: " + String.valueOf(session.getAttribute("userId")));
					return "redirect:/ocean/all";
				} else if (userIdAndPassword.getUserStatus() == 'P'){
					return "redirect:/admin/main";
				}
				else {
					redirectAttributes.addFlashAttribute("message", "탈퇴된 회원입니다! 다시 로그인해주세요");
					return "redirect:/user/login";
				}
			} else {
				redirectAttributes.addFlashAttribute("message", "비밀번호가 틀립니다! 다시 로그인해주세요");
				return "redirect:/user/login";
			}
		} catch(NullPointerException e) {
			redirectAttributes.addFlashAttribute("message", "회원이 아닙니다! 다시 로그인해주세요");
			return "redirect:/user/login";
		}
	}

	// 마이 페이지
	@GetMapping("/mypage")
	public String myPage(Model model, HttpSession session) {
		String userIdInSession = String.valueOf(session.getAttribute("userId"));


		if (userIdInSession == "null") {
			return "redirect:/user/login";
		} else {
			int userId = Integer.valueOf(userIdInSession);
			UserProfile userProfile = userService.selectUserProfileByUserId(userId);
			model.addAttribute("userProfile", userProfile);
			model.addAttribute("userId", userId);
			model.addAttribute("userState", "로그아웃");
			return "user/myPage";
		}
	}

	@GetMapping("/mypage/update/{userId}")
	public String myPageUpdate(@PathVariable int userId, Model model) {
		UserProfile userProfile = userService.selectUserProfileByUserId(userId);
		model.addAttribute("userProfile", userProfile);
		model.addAttribute("userId", userId);

		List<String> introductions = new ArrayList<>();
		introductions.add("다정다감");
		introductions.add("참을성만땅");
		introductions.add("애교쟁이");
		introductions.add("예의바른");
		introductions.add("소심소심");
		introductions.add("이끌어가는");
		introductions.add("감성폭발");
		introductions.add("똑똑이");
		introductions.add("입담천재");
		introductions.add("긍정에너지");
		introductions.add("인싸그잡채");
		model.addAttribute("introductions", introductions);

		List<String> hobbies = new ArrayList<>();
		hobbies.add("운동");
		hobbies.add("독서");
		hobbies.add("음악 감상");
		hobbies.add("영화,드라마 감상");
		hobbies.add("노래 부르기");
		hobbies.add("춤추기");
		hobbies.add("공예,만들기");
		hobbies.add("맛집 탐방");
		model.addAttribute("hobbies", hobbies);
		model.addAttribute("userState", "로그아웃");
		return "user/myPageUpdate";
	}

	// 비밀번호 찾기
	@GetMapping("/finduserpassword")
	public String findUserPassword(String userEmail) {
		return "user/findPassword";
	}

	@PostMapping("/finduserpassword")
	public String findUserPassword(String userEmail, RedirectAttributes redirectAttributes) throws MessagingException {
		if (userService.selectUserIdByUserEmail(userEmail) == null) {
			redirectAttributes.addFlashAttribute("message", "가입된 이메일이 아닙니다. 다시 입력해주세요!");
			return "redirect:/user/finduserpassword";
		} else {

			String tempPassword = getTempPassword();
			String encodeTempPassoword = passwordEncoder.encode(tempPassword);

			// 회원 비밀번호 업데이트
			UserUpdatePassword userUpdatePassword = new UserUpdatePassword();
			userUpdatePassword.setUserEmail(userEmail);
			userUpdatePassword.setUserPassword(encodeTempPassoword);
			userService.updateUserPassword(userUpdatePassword);

			// 이메일 보내기
			MimeMessage m = javaMailSender.createMimeMessage();
			MimeMessageHelper h = new MimeMessageHelper(m,"UTF-8");
			h.setFrom("cjw9977@naver.com");
			h.setTo(userEmail);
			h.setSubject("[금오도] 임시 비밀번호 발급");
			h.setText("임시 비밀번호는 " + tempPassword + " 입니다!");
			javaMailSender.send(m);

			redirectAttributes.addFlashAttribute("message", "임시번호가 발급되었습니다! 재로그인 해주세요!");
		}
		return "redirect:/user/login";
	}

	// 임시 비밀번호 만들기
	private String getTempPassword() {
		char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
				'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
		String str = "";
		int idx = 0;
		for (int i = 0; i < 10; i++) {
			idx = (int) (charSet.length * Math.random());
			str += charSet[idx];
		}
		return str;
	}
}
