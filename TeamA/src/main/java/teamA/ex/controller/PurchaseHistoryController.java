package teamA.ex.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

import teamA.ex.model.entity.UserEntity;
import teamA.ex.model.entity.CourseEntity;
import teamA.ex.model.entity.TransactionHistoryEntity;
import teamA.ex.model.entity.TransactionItemsEntity;
import teamA.ex.service.CourseService;
import teamA.ex.service.TransactionHistoryService;
import teamA.ex.service.TransactionItemsService;
import teamA.ex.service.UserService;

@RequestMapping("/home")

@Controller
public class PurchaseHistoryController {
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	private CourseService courseService;
	
	@Autowired 
	private TransactionHistoryService transactionHistoryService;
	
	@Autowired 
	private TransactionItemsService transactionItemsService;
	
	@Autowired
	private HttpSession session;
	
	//Mapping
	@GetMapping("/user/viewpurchasehistory")
	public String getEditAdminInfoPage(Model model) {
		UserEntity user = (UserEntity) session.getAttribute("user");
		Long userID = user.getStudentId();
		List<TransactionHistoryContents> transactions = new ArrayList<TransactionHistoryContents>();
		
		List<TransactionHistoryEntity> transactionList = transactionHistoryService.findUserTransactionHistoryByUserId(userID);		
		
		for (int i = 0; i < transactionList.size(); i++) {
			Long transactionId = transactionList.get(i).getTransactionId();
			LocalDateTime rawDate = transactionList.get(i).getTransactionDate(); 
			DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			String date = rawDate.format(formatDate);
			int amount = transactionList.get(i).getAmount();
			TransactionHistoryContents transaction = new TransactionHistoryContents(date, amount);
//			System.out.println(transaction);
			
			List<TransactionItemsEntity> transactionItems = transactionItemsService.findByTransactionId(transactionId);
			for (int x = 0; x < transactionItems.size(); x++) {
				Long courseId = transactionItems.get(x).getCourseId();
				CourseEntity course = courseService.getCourse(courseId);
				transaction.courses.add(course);
			}
			
			transactions.add(transaction);
//			System.out.println(transaction.courses);
		}
		
		model.addAttribute("transactions", transactions);
		model.addAttribute("user", user);
		return "user_purchase_history.html";
	}
	
}
