package teamA.ex.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import teamA.ex.model.entity.CourseEntity;
import teamA.ex.model.entity.UserEntity;
import teamA.ex.service.UserService;

@RequestMapping("/home")

@Controller
public class EditUserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpSession session;
	
	@GetMapping("/edituserinfo")
	public String getEditUserInfoPage(Model model) {
		UserEntity user = (UserEntity) session.getAttribute("user");
		int cartContentNumber = userService.getCartContentNumber();
		model.addAttribute("cartContentNumber", cartContentNumber);
		model.addAttribute("user", user);
		return "user_edit_personal_info.html";
	}
	
	// Get new user info and pass the info to the confirmation page
	@PostMapping("/edituserinfo/confirmuserinfo")
	public String enterNewUserInfo(@RequestParam(required=false) MultipartFile newIcon, @RequestParam String newName, @RequestParam String newEmail, @RequestParam String newPassword, Model model) {
		UserEntity user = (UserEntity) session.getAttribute("user");
		String newFileName;
		
		if (!newIcon.isEmpty()) {
			newFileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date()) + newIcon.getOriginalFilename();
			try {
				// 保存処理
				Files.copy(newIcon.getInputStream(), Path.of("src/main/resources/static/user-img/" + newFileName));
			} catch (Exception e) {
				e.printStackTrace();
				}
		} else {
			newFileName = user.getStudentIcon();
		}
		
		int cartContentNumber = userService.getCartContentNumber();
		model.addAttribute("cartContentNumber", cartContentNumber);
		model.addAttribute("user", user);
		model.addAttribute("newIcon", newFileName);
		model.addAttribute("newName", newName);
		model.addAttribute("newEmail", newEmail);
		model.addAttribute("newPassword", newPassword);
		return "user_edit_user_confirm.html";
	}
	
	@PostMapping("/edituserinfo/updateuserinfo")
	public String updateNewUserInfo(@RequestParam String newIcon, @RequestParam String newName, @RequestParam String newEmail, @RequestParam(required=false) String newPassword, Model model) {
		UserEntity oldUser = (UserEntity)  session.getAttribute("user");
		String currentEmail = oldUser.getStudentEmail();

		// if new password save password
		System.out.println(newPassword);
		if (!newPassword.isEmpty()) {
			userService.updateUserPassword(currentEmail, newPassword);
			if (userService.updateUser(currentEmail, newIcon, newName, newEmail)) {
				LinkedList<CourseEntity> cartList = (LinkedList<CourseEntity>) session.getAttribute("cart");
				LinkedList<CourseEntity> newCartList = (LinkedList<CourseEntity>) cartList.clone();
				session.invalidate();
				UserEntity user = userService.login(newEmail, newPassword);
				session.setAttribute("cart", newCartList); 
				int cartContentNumber = userService.getCartContentNumber();
				model.addAttribute("cartContentNumber", cartContentNumber);
				session.setAttribute("user", user);
				model.addAttribute("user", user);
				return "redirect:/home/user/view/courses";
			} else {
				return "user_edit_personal_info.html";
			}
		} else {
			if (userService.updateUser(currentEmail, newIcon, newName, newEmail)) {
				UserEntity user = (UserEntity) session.getAttribute("user");
				Long userId = user.getStudentId();
				UserEntity newUser = (UserEntity) userService.findByStudentId(userId);
				session.setAttribute("user", newUser);
				model.addAttribute("user", newUser);
				return "redirect:/home/user/view/courses";
			} else {
				return "user_edit_personal_info.html";
			}
		}
	}
}



