package teamA.ex.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import teamA.ex.model.entity.AdminEntity;
import teamA.ex.model.entity.CourseEntity;
import teamA.ex.model.entity.UserEntity;
import teamA.ex.service.CourseService;


@RequestMapping("/home")

@Controller
public class CourseController {
	
	// CourseServiceクラスのメソッドを呼び出し、指定した処理を実行できるようにする
	@Autowired
	private CourseService courseService;
	
	// HTTP GETリクエストを受け取るメソッド
	@Autowired
	private HttpSession session;
	
	@GetMapping("/admin/view/courses")
	public String getAdminCourseViewPage(Model model) {
		
		// セッションから現在の管理者情報を取得するため、sessionオブジェクトを使用
		AdminEntity adminList = (AdminEntity) session.getAttribute("admin");
		
		// adminListから現在ログインしている人のユーザー名を取得
		String adminName = adminList.getAdminName();
		
		//courseServiceのfindAllCoursePostメソッドを呼び出し、現在の管理者に関する講座を取得
		//戻り値はCourseEntityのリストで、このリストをmodelに追加
		 List<CourseEntity> courselist = courseService.findAll();
		
		model.addAttribute("adminName", adminName);
		model.addAttribute("courselist", courselist);
		return "admin_view_courses.html";
	}
	
	@GetMapping("/user/view/courses")
	public String getUserCourseViewPage(Model model) {
		
		// セッションから現在の管理者情報を取得するため、sessionオブジェクトを使用
		UserEntity user = (UserEntity) session.getAttribute("user");
		Long userId = user.getStudentId();
		
		// adminListから現在ログインしている人のユーザー名を取得
		String userName = user.getStudentName();
		
		//courseServiceのfindAllCoursePostメソッドを呼び出し、現在の管理者に関する講座を取得
		//戻り値はCourseEntityのリストで、このリストをmodelに追加
		 List<CourseEntity> courselist = courseService.findAll();
		
		model.addAttribute("userName", userName);
		model.addAttribute("courselist", courselist);
		return "user_view_courses.html";
	}


	@GetMapping("/editcourse/{courseId}")
	public String getEditCoursePage(@PathVariable Long courseId, Model model)  {
		CourseEntity course = courseService.getCourse(courseId);
		if(course == null) {
			return "redirect:/home/admin/view/courses";
		} else {
			model.addAttribute("course", course);
			return "admin_edit_course.html";
		}
	}
	
	
//講座追加機能
	//講座追加画面(admin_add_course.html)を表示
	@GetMapping("/addcourse")
	public String getAddCoursePage() {
		return "admin_add_course.html";
	}
	
	//講座追加内容を習得しDBに保存
	@PostMapping("/addcourse/save")
	public String addCourse(@RequestParam String courseName, @RequestParam int courseFee,
			@RequestParam MultipartFile courseImage, @RequestParam LocalDate registerDate,
			@RequestParam LocalDate courseStartDate, @RequestParam LocalDate courseFinishDate,
			@RequestParam LocalTime lessonStartTime, @RequestParam int lessonDuration,  Model model) {
		
		// セッションから現在の管理者情報を取得するため、sessionオブジェクトを使用
		AdminEntity adminList = (AdminEntity) session.getAttribute("admin");
		
		// adminListから現在ログインしている人のユーザー名を取得
		Long adminId = adminList.getAdminId();
		
		// deleteFlagを宣言する
		int deleteFlag = 0;
			
		// courseImageの名前を習得し保存する処理
		String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date()) + courseImage.getOriginalFilename();
		try {
			// 保存処理
			Files.copy(courseImage.getInputStream(), Path.of("src/main/resources/static/img/" + fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		courseService.createCourse(courseName, courseFee, courseName, registerDate, courseStartDate, courseFinishDate, lessonStartTime, lessonDuration, adminId, deleteFlag);
		return "redirect:/admin/view/courses";
	}
}
