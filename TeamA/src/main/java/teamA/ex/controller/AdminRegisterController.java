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
import teamA.ex.service.AdminService;

@Controller
public class AdminRegisterController {
	
	@Autowired
	private AdminService adminService;

	@Autowired 
	private HttpSession session;
	
	//Admin register画面の表示
	@GetMapping("/admin/register")
	public String getUserRegisterPage() {
		return "admin_add_admin.html";
	}
	
	//Register処理
	@PostMapping("/admin/register/process")
	public String registerAdmin(@RequestParam MultipartFile admin_icon, @RequestParam String admin_name, 
			@RequestParam String admin_email, @RequestParam String password) {
		
		String adminImgFileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date()) + admin_icon.getOriginalFilename();
		try {
			Files.copy(admin_icon.getInputStream(), Path.of("src/main/resources/static/admin-img/" + adminImgFileName));
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		adminService.createAdmin(adminImgFileName, admin_name, admin_email, password);
		return "redirect:/admin/register";
	}
}
