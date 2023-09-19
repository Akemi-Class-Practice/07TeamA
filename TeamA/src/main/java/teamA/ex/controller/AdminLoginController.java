package teamA.ex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import teamA.ex.model.entity.AdminEntity;
import teamA.ex.service.AdminService;
import teamA.ex.service.LoginAnalyticService;


@Controller
public class AdminLoginController {
	@Autowired
	private AdminService adminService;
	@Autowired 
	private LoginAnalyticService loginAnalyticService;
	@Autowired
	private HttpSession session;
	
	// GetMappingで管理者のログイン画面の表示
	@GetMapping("/adminlogin")
	public String getAdminLoginPage() {
		return "admin_login.html";
	}
	
	// PostMappingでadminlogin/processのPOST情報を取得
	// @RequestParamでHTMLのFORMAのINPUTを受け取る
	@PostMapping("/adminlogin/process")
	public String login(@RequestParam String email, @RequestParam String password) {
		// 受け取ったemailとpasswordを使ってAdminEntityのadminServiceでログインする
		AdminEntity admin = adminService.login(email, password);
		
		// loginメソッドが失敗する場合、admin loginのページに移動する
		// 成功した場合、ユーザのインスタンスを使ってセッションを初めてに移動する
		if (adminService.login(email, password) == null) {
			return "redirect:/adminlogin";
		} else {
			session.setAttribute("admin", admin);
			return "redirect:/home/admin/view/courses";
		}
	}
	
	//ログアウト処理
	@GetMapping("/adminlogout")
	public String Logout() {
		
		// セッションを切ってログインページに移動する
		session.invalidate();
		return "redirect:/adminlogin";
	}
}