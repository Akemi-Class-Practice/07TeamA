package teamA.ex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import teamA.ex.model.entity.UserEntity;
import teamA.ex.service.UserService;

@Controller
public class EditUserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpSession session;
	
	@GetMapping("/edituserinfo")
	public String getEditUserInfoPage(Model model) {
		UserEntity user = (UserEntity) session.getAttribute("user");
		model.addAttribute("user", user);
		return "user_edit_personal_info.html";
	}
	
	// Get new user info and pass the info to the confirmation page
	@PostMapping("/confirmuserinfo")
	public String enterNewUserInfo(@RequestParam String newName, @RequestParam String newEmail, @RequestParam String newPassword, Model model) {
		model.addAttribute("new_name", newName);
		model.addAttribute("new_name", newEmail);
		model.addAttribute("new_name", newPassword);
		return "user_edit_user_confirm.html";
	}
	
	@PostMapping("/updateuserinfo")
	public String updateNewUserInfo(@RequestParam String newName, @RequestParam String newEmail, @RequestParam String newPassword, Model model) {
		UserEntity oldUser = (UserEntity)  session.getAttribute("user");
		String currentEmail = oldUser.getStudentEmail();
		
		if (userService.updateUser(currentEmail, newName, newEmail, newPassword)) {
			session.invalidate();
			UserEntity user = userService.login(newEmail, newPassword);
			session.setAttribute("user", user);
			model.addAttribute("user", user);
			return "redirect:/user/viewcourses";
		} else {
			return "user_edit_personal_info.html";
		}
	}
	
}
