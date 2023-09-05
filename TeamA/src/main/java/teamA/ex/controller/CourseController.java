package teamA.ex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/home")

@Controller
public class CourseController {
	
	// CourseServiceクラスのメソッドを呼び出し、指定した処理を実行できるようにする
	@Autowired
	private CourseService courseService;
	
	// HTTP GETリクエストを受け取るメソッド
	@Autowired
	private HttpSession session;
	
	@GetMapping("/viewcourses")
	
	public String getCourseViewPage(Model model) {
		
		// セッションから現在の管理者情報を取得するため、sessionオブジェクトを使用
		AdminEntity adminList = (AdminEntity) session.getAttribute("admin");
		Long adminId = adminList.getAdminId();
		
		// adminListから現在ログインしている人のユーザー名を取得
		String adminName = adminList.getAdminName();
		
		//courseServiceのfindAllCoursePostメソッドを呼び出し、現在の管理者に関する講座を取得
		//戻り値はCourseEntityのリストで、このリストをmodelに追加
		 List<CourseEntity> courselist = courseService.findAll();
		
		model.addAttribute("adminName", adminName);
		model.addAttribute("courselist", courselist);
		return "admin_view_courses.html";
		
	}
	
	

}
