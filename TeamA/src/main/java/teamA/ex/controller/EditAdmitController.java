package teamA.ex.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import teamA.ex.model.entity.AdminEntity;
import teamA.ex.model.entity.UserEntity;
import teamA.ex.service.AdminService;
import teamA.ex.service.UserService;

@RequestMapping("/home")

@Controller
public class EditAdmitController {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private HttpSession session;
	
	@GetMapping("/editadmininfo")
	public String getEditAdminInfoPage(Model model) {
		AdminEntity admin = (AdminEntity) session.getAttribute("admin");
		model.addAttribute("admin", admin);
		return "admin_edit_personal_info.html";
	}
	
	// Get new user info and pass the info to the confirmation page
	@PostMapping("/editadmininfo/confirmadmininfo")
	public String enterNewAdminInfo(@RequestParam(required=false) MultipartFile newIcon, @RequestParam String newName, @RequestParam String newEmail, @RequestParam String newPassword, Model model) {
		AdminEntity admin = (AdminEntity) session.getAttribute("admin");
		String newFileName;
		
		if (!newIcon.isEmpty()) {
			newFileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date()) + newIcon.getOriginalFilename();
			try {
				// 保存処理
				Files.copy(newIcon.getInputStream(), Path.of("src/main/resources/static/admin-img/" + newFileName));
			} catch (Exception e) {
				e.printStackTrace();
				}
		} else {
			newFileName = admin.getAdminIcon();
		}
		
		model.addAttribute("admin", admin);
		model.addAttribute("newIcon", newFileName);
		model.addAttribute("newName", newName);
		model.addAttribute("newEmail", newEmail);
		model.addAttribute("newPassword", newPassword);
		return "admin_edit_user_confirm.html";
	}
	
	@PostMapping("/editadmininfo/updateadmininfo")
	public String updateNewAdminInfo(@RequestParam String newIcon, @RequestParam String newName, @RequestParam String newEmail, @RequestParam(required=false) String newPassword, Model model) {
		AdminEntity oldAdmin = (AdminEntity) session.getAttribute("admin");
		String currentEmail = oldAdmin.getAdminEmail();

		// if new password save password
		System.out.println(newPassword);
		if (!newPassword.isEmpty()) {
			adminService.updateAdminPassword(currentEmail, newPassword);
			if (adminService.updateAdmin(currentEmail, newIcon, newName, newEmail)) {
				session.invalidate();
				AdminEntity admin = adminService.login(newEmail, newPassword);
				session.setAttribute("admin", admin);
				model.addAttribute("admin", admin);
				return "redirect:/home/admin/view/courses";
			} else {
				return "admin_edit_personal_info.html";
			}
		} else {
			if (adminService.updateAdmin(currentEmail, newIcon, newName, newEmail)) {
				AdminEntity admin = (AdminEntity) session.getAttribute("admin");
				Long adminId = admin.getAdminId();
				AdminEntity newAdmin = (AdminEntity) adminService.findByAdminId(adminId);
				session.setAttribute("admin", newAdmin);
				model.addAttribute("admin", newAdmin);
				return "redirect:/home/admin/view/courses";
			} else {
				return "admin_edit_personal_info.html";
			}
		}
				
	}
	
}



