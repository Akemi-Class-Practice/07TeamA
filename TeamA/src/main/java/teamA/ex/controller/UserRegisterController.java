package teamA.ex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserRegisterController {

	@Autowired
	private UserService userService;
	@Autowired 
	private HttpSession session;
	
	// User register画面の表示
	@GetMapping("/userregister")
	public String getUserRegisterPage() {
		return "user_register.html";
	}
	
	// Register処理
	//PostMappingで/userregister/processのPOST情報を受取る
	// @RequestParamでHTMLのFORMAのINPUTを受け取る
	@PostMapping("/userregister/process")
	public String register(@RequestParam MultipartFile user_icon, @RequestParam String user_name, 
			@RequestParam String email, @RequestParam String password) {
		
		userService.createAccount(user_icon, user_name, email, password);
		return "redirect:/userlogin";
	}
}
