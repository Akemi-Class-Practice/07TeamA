package teamA.ex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import teamA.ex.model.entity.UserEntity;


@Controller
public class UserLoginController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private HttpSession session;
	
	// GetMappingで管理者のログイン画面の表示
	@GetMapping("/userlogin")
	public String getUserLoginPage() {
		return "user_login.html";
	}
	
	// PostMappingでuserlogin/processのPOST情報を取得
	// @RequestParamでHTMLのFORMAのINPUTを受け取る
	@PostMapping("/userlogin/process")
	public String login(@RequestParam String email, @RequestParam String password) {
		// 受け取ったemailとpasswordを使ってUserEntityのuserServiceでログインする
		UserEntity user = userService.login(email, password);
		
		// loginメソッドが失敗する場合、userloginのページに移動する
		// 成功した場合、ユーザのインスタンスを使ってセッションを初めてに移動する
		if (userService.login(email, password) == null) {
			return "redirect:/userlogin";
		} else {
			session.setAttribute("user", user);
			return "redirect:/user/viewcourses";
		}
	}
	
	//ログアウト処理
	@GetMapping("/userlogout")
	public String Logout() {
		
		// セッションを切ってログインページに移動する
		session.invalidate();
		return "redirect:/userlogin";
	}

}
