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
	private CourseService courseService;
	
	@Autowired 
	private TransactionHistoryService transactionHistoryService;
	
	@Autowired 
	private TransactionItemsService transactionItemsService;
	
	@Autowired 
	private UserService userService;
	
	@Autowired
	private HttpSession session;
	
	//Mapping
	@GetMapping("/user/viewpurchasehistory")
	public String getViewPurchaseHistoryPage(Model model) {
		UserEntity user = (UserEntity) session.getAttribute("user");
		Long userID = user.getStudentId();
		// 取引の歴史を入れるための空きリストを初期化
		List<TransactionHistoryContents> transactions = new ArrayList<TransactionHistoryContents>();
		
		// ログインされているユーザーのIDで取引の歴史を取得してリストに入れる
		List<TransactionHistoryEntity> transactionList = transactionHistoryService.findUserTransactionHistoryByUserId(userID);		
		
		// For繰り返しでtransactionListの要素を反復する
		for (int i = 0; i < transactionList.size(); i++) {
			// 取引のIDを取得して
			Long transactionId = transactionList.get(i).getTransactionId();
			
			// 購入日付を取得するためのコード。TransactionHistoryEntityに保存されている日付はLocalDateTimeだから、LocalDateTimeから読みやすいフォーマットに変えないといけない
			//　１．取引の日付を取得してrawDateの変数で保存する
			// 2.DateTimeFormatterを使ってdd-MM-yyyyのフォマットを準備してformatDateの変数で保存する
			// ３．フォマットを使ってrawDateをformatDateで読みやすいのフォマットに帰って文字列としてdateで保存する
			LocalDateTime rawDate = transactionList.get(i).getTransactionDate(); 
			DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			String date = rawDate.format(formatDate);
			
			// 取引の合計金額を取得してamountの変数で保存する
			int amount = transactionList.get(i).getAmount();
			
			// dateとamountをTransactionHistoryContentsに渡して新しいTransactionHistoryContentsのオブジェクトを作る
			TransactionHistoryContents transaction = new TransactionHistoryContents(date, amount);
			
			// 取引のIDを使って取引の購入した講座をTransactionItemsEntityのリストに保存する
			List<TransactionItemsEntity> transactionItems = transactionItemsService.findByTransactionId(transactionId);
			
			// For繰り返しでtransactionItemsの要素を反復する
			for (int x = 0; x < transactionItems.size(); x++) {
				// 講座のIDを取得する
				Long courseId = transactionItems.get(x).getCourseId();
				// 講座のIDでCourseEntityを作る
				CourseEntity course = courseService.getCourse(courseId);
				// 講座のオブジェクトをtransactionのオブジェクトの中に講座リストに入れる
				transaction.courses.add(course);
			}
			
			// 取引のオブジェクトをtransactionsのリストに入れる
			transactions.add(transaction);
		}
		
		int cartContentNumber = userService.getCartContentNumber();
		model.addAttribute("cartContentNumber", cartContentNumber);
		//取引の歴史を画面に表示できるようにtransactionsのリストをモデルに設定する
		model.addAttribute("transactions", transactions);
		//ユーザー情報を画面に表示できるようにuserのオブジェクトをモデルに設定する
		model.addAttribute("user", user);
		// 適切のHTMLファイルを戻す
		return "user_purchase_history.html";
	}
	
}
