package teamA.ex.controller;

import java.time.LocalDateTime;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import teamA.ex.model.entity.CourseEntity;
import teamA.ex.model.entity.TransactionHistoryEntity;
import teamA.ex.model.entity.UserEntity;
import teamA.ex.service.TransactionHistoryService;
import teamA.ex.service.TransactionItemsService;


@RequestMapping("/home")

@Controller
public class PaymentController {
	
	@Autowired
	private TransactionHistoryService transactionHistoryService;
	
	@Autowired
	private TransactionItemsService transactionItemsService;
	
	@Autowired
	private HttpSession session;

	@GetMapping("/choosepaymentmethod")
	public String getChoosePaymentMethodPage(Model model) {
		UserEntity user = (UserEntity) session.getAttribute("user");
		LinkedList<CourseEntity> cartList = (LinkedList<CourseEntity>) session.getAttribute("cart");
		
		int totalPrice = 0;
		for (int idx = 0; idx < cartList.size(); idx++) {
			totalPrice += cartList.get(idx).getCourseFee();
		};
		
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("user", user);
		model.addAttribute("cartList", cartList);
		return "user_select_payment_method.html";
	}

	@GetMapping("/paymentdetails")
	public String getPaymentDetailsPage(Model model) {
		UserEntity user = (UserEntity) session.getAttribute("user");
		LinkedList<CourseEntity> cartList = (LinkedList<CourseEntity>) session.getAttribute("cart");
		
		int totalPrice = 0;
		for (int idx = 0; idx < cartList.size(); idx++) {
			totalPrice += cartList.get(idx).getCourseFee();
		};
		
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("user", user);
		model.addAttribute("cartList", cartList);
		return "user_confirm_payment_details.html";
	}

	@GetMapping("/makepayment")
	public String makePayment(Model model) {
		UserEntity user = (UserEntity) session.getAttribute("user");
		Long userId = user.getStudentId();
		LinkedList<CourseEntity> cartList = (LinkedList<CourseEntity>) session.getAttribute("cart");
		
		int totalPrice = 0;
		for (int idx = 0; idx < cartList.size(); idx++) {
			totalPrice += cartList.get(idx).getCourseFee();
		};
		
		transactionHistoryService.createTransactionHistory(userId, totalPrice);
		TransactionHistoryEntity transaction = transactionHistoryService.getTransactoinId(userId);
		Long transactionId = transaction.getTransactionId();
		
		for (int idx = 0; idx < cartList.size(); idx++) {
			Long courseId = cartList.get(idx).getCourseId();
			transactionItemsService.createTransactionHistory(transactionId, courseId);
		}
		
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("cartList", cartList);
		model.addAttribute("user", user);
		return "user_payment_confirmation.html";
	}
}
