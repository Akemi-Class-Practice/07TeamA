package teamA.ex.controller;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import teamA.ex.model.entity.UserEntity;
import teamA.ex.service.ContactService;

@RequestMapping("/home")
@Controller
public class ContactController {

	@Autowired
	private ContactService contactService;

	@Autowired
	HttpSession session;

	// お問い合わせの画面の表示
	@GetMapping("/user/contact/view")

	public String getContactViewPage(Model model) {
		UserEntity user = (UserEntity) session.getAttribute("user");
		model.addAttribute("user", user);
        return "user_contact.html";
	}

	// ユーザー側のお問い合わせ内容の保存
	@PostMapping("/user/contact/process")
	public String ContactView(@RequestParam String contactTitle, @RequestParam String contactDetail, Model model) {
		UserEntity user = (UserEntity) session.getAttribute("user");
		int isDone = 0;
		Long studentId = user.getStudentId();
		LocalDate contactDate = LocalDate.now();
		
		if (contactService.createContact(contactTitle, contactDetail, contactDate, studentId, isDone)) {
			return "redirect:/user/contact/view";
		} else {
			return "redirect:/userlogin";
		}

	}

}