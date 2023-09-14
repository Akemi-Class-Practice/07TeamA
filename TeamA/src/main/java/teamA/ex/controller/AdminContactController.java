package teamA.ex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import teamA.ex.model.entity.AdminEntity;
import teamA.ex.model.entity.ContactEntity;
import teamA.ex.service.ContactService;

@RequestMapping("/home")
@Controller
public class AdminContactController {

	@Autowired
	private ContactService contactService;
	@Autowired 
	private HttpSession session;
	//管理者側のお問い合わせの一覧画面
		@GetMapping("/admin/contact/view")
		public String contactNoView( Model model) {
			List<ContactEntity> contactList = contactService.findAll();
			model.addAttribute("contactList", contactList);
			AdminEntity admin = (AdminEntity) session.getAttribute("admin");
			model.addAttribute("admin", admin);
			return "admin_contact.html";

		}
}
