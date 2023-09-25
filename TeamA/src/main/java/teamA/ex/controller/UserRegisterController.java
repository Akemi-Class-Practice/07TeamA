package teamA.ex.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import teamA.ex.service.UserService;

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
		String imgFileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date()) + user_icon.getOriginalFilename();

		try {
			Files.copy(user_icon.getInputStream(), Path.of("src/main/resources/static/user-img/" + imgFileName));
		}catch(Exception e) {
			e.printStackTrace();
		}
		if(userService.createAccount(imgFileName, user_name, email, password)) {
			return "redirect:/userlogin";
		}else {
			return "redirect:/userregister";
		}
	}
}