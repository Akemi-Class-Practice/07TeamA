package teamA.ex.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import teamA.ex.model.entity.AdminEntity;
import teamA.ex.model.entity.ContactEntity;
import teamA.ex.model.entity.CourseEntity;
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
			List<ContactEntity> contactList = contactService.findAllByOrderByIsDoneAsc();
			AdminEntity admin = (AdminEntity) session.getAttribute("admin");
			
			model.addAttribute("contactList", contactList);
			model.addAttribute("admin", admin);
			
			return "admin_contact.html";

		}
		
	//既読機能
		@GetMapping("/admin/contact/isDone/{contactId}")
		public String isRead(@PathVariable Long contactId) {
			ContactEntity contact = (ContactEntity) contactService.findByContactId(contactId);
			
			if(contact == null) {
				//
				return "redirect:/home/admin/contact/view";
			}else {
				//
				if(contact.getIsDone() == 0) {
					contactService.isDone(contactId, 1);
					return "redirect:/home/admin/contact/view";
				}else {
					contactService.isDone(contactId, 0);
					return "redirect:/home/admin/contact/view";
				}
			}
		}
}
