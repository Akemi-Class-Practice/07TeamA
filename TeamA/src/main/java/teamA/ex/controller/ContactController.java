package teamA.ex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import teamA.ex.model.entity.ContactEntity;
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
	@GetMapping("/user/")
	public String getContactViewPage() {
		return "user_contact.html";
	}
	//ユーザー側のお問い合わせ内容の保存
	@PostMapping("#")
	public String ContactView( @RequestParam String contactTitle,
		@RequestParam String contactDetail,  Model model) {
		UserEntity user = (UserEntity) session.getAttribute("user");
		int isDone = 0;
		Long studentId = user.getStudentId();
		if (contactService.createContact( contactTitle, contactDetail, studentId, isDone)) {
			return "#";
		}else {
			return "#";
		}
		
	}
	//管理者側のお問い合わせの一覧画面
	@GetMapping("#")
	public String contactNoView( Model model) {
		List<ContactEntity> contactList = contactService.findAll();
		model.addAttribute("contactList", contactList);
		return "#";

	}

}