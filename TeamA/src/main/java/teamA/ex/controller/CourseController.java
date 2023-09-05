package teamA.ex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@GetMapping("/adminviewcourses")
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
	
	@GetMapping("/userviewcourses")
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
	
	

}
