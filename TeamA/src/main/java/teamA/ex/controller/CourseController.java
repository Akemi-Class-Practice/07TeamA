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
		AdminEntity admin = (AdminEntity) session.getAttribute("admin");
		
		//courseServiceのfindAllCoursePostメソッドを呼び出し、現在の管理者に関する講座を取得
		//戻り値はCourseEntityのリストで、このリストをmodelに追加
		List<CourseEntity> courseList = courseService.findAll();
		
		model.addAttribute("admin", admin);
		model.addAttribute("courseList", courseList);
		return "admin_view_courses.html";
	}
	
	@GetMapping("/user/view/courses")
	public String getUserCourseViewPage(Model model) {
		
		// セッションから現在の管理者情報を取得するため、sessionオブジェクトを使用
		UserEntity user = (UserEntity) session.getAttribute("user");
			
		//courseServiceのfindAllCoursePostメソッドを呼び出し、現在の管理者に関する講座を取得
		//戻り値はCourseEntityのリストで、このリストをmodelに追加
		 List<CourseEntity> courseList = courseService.findCoursesForUser();
		
		model.addAttribute("user", user);
		model.addAttribute("courseList", courseList);
		return "user_view_courses.html";
	}
	
	
	//
	//user_view_courses_info.htmlを表示する
	@GetMapping("/user/view/courses/{courseId}")
	public String getCourseInfoPage(@PathVariable Long courseId, Model model) {
		
		UserEntity user = (UserEntity) session.getAttribute("user");
		model.addAttribute("user", user);
		
		CourseEntity course = courseService.getCourse(courseId);
		model.addAttribute("course", course);
		
		return "user_view_course_info.html";
		
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
	@GetMapping("/admin/view/courses/addcourse")
	public String getAddCoursePage(Model model) {
		AdminEntity admin = (AdminEntity) session.getAttribute("admin");
		model.addAttribute("admin", admin);
		return "admin_add_course.html";
	}
	
	//講座追加内容を習得しDBに保存
	@PostMapping("/admin/view/courses/addcourse/save")
	public String addCourse(@RequestParam String courseName, @RequestParam int courseFee,
			@RequestParam MultipartFile courseImage,
			@RequestParam LocalDate courseStartDate, @RequestParam LocalDate courseFinishDate,
			@RequestParam LocalTime lessonStartTime, @RequestParam int lessonDuration,
			@RequestParam String courseInfo,  Model model) {
		
		// セッションから現在の管理者情報を取得するため、sessionオブジェクトを使用
		AdminEntity adminList = (AdminEntity) session.getAttribute("admin");
		
		// adminListから現在ログインしている人のユーザー名を取得
		Long adminId = adminList.getAdminId();
		
		// deleteFlagを宣言する
		int deleteFlag = 0;
		
		LocalDate registerDate = LocalDate.now();
		
		// courseImageの名前を習得し保存する処理
		String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date()) + courseImage.getOriginalFilename();
		try {
			// 保存処理
			Files.copy(courseImage.getInputStream(), Path.of("src/main/resources/static/course-img/" + fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		courseService.createCourse(courseName, courseFee, fileName, registerDate, courseStartDate, courseFinishDate, lessonStartTime, lessonDuration, adminId, deleteFlag, courseInfo);
		return "redirect:/home/admin/view/courses";
	}
	
	// 講座編集機能
	@GetMapping("/admin/view/courses/editcourse/{courseId}")
		public String getEditPage(@PathVariable Long courseId, Model model) {
		// courseIdを使ってCourseEntityを作って
		CourseEntity course = courseService.getCourse(courseId);
		
		if (course == null) {
			return "redirect:/home/admin/view/courses";
		} else {
			// 行き先のページにCourseのインスタンスをモデルでビューに渡して
			model.addAttribute("course", course);
			return "admin_edit_course.html";
		}
	}
	

	@PostMapping("/admin/view/courses/editcourse/save")
	public String saveEdit(@RequestParam String courseName, @RequestParam int courseFee,
			@RequestParam MultipartFile courseImage,
			@RequestParam LocalDate courseStartDate, @RequestParam LocalDate courseFinishDate,
			@RequestParam LocalTime lessonStartTime, @RequestParam int lessonDuration,
			@RequestParam String courseInfo, @RequestParam Long courseId) {
		
		
		// courseImageの名前を習得し保存する処理
		String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date()) + courseImage.getOriginalFilename();
		try {
			// 保存処理
			Files.copy(courseImage.getInputStream(), Path.of("src/main/resources/static/course-img/" + fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		courseService.editCoursePost(courseName, courseFee, fileName, courseStartDate, courseFinishDate, lessonStartTime, lessonDuration, courseInfo, courseId);
		return "redirect:/home/admin/view/courses";
	}
	
	
	//削除機能
	@PostMapping("/course/delete")
	public String courseDelete(@RequestParam Long courseId,Model model) {
		//現在ログインしている管理者情報を取得する
		AdminEntity admin = (AdminEntity) session.getAttribute("admin");
		//現在ログインしている人の名前を取得する
		String loginAdminName = admin.getAdminName();
		courseService.deleteCourse(courseId);
		model.addAttribute("loginAdminName",loginAdminName);
		return "redirect:/home/admin/view/courses";
	}
	
	
}
