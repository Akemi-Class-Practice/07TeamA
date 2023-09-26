package teamA.ex.controller;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import teamA.ex.model.entity.CourseEntity;
import teamA.ex.model.entity.TransactionHistoryEntity;
import teamA.ex.model.entity.UserEntity;
import teamA.ex.service.TransactionHistoryService;
import teamA.ex.service.TransactionItemsService;
import teamA.ex.service.UserService;


@RequestMapping("/home")

@Controller
public class PaymentController {
	
	@Autowired
	private TransactionHistoryService transactionHistoryService;
	
	@Autowired
	private TransactionItemsService transactionItemsService;
	
	@Autowired 
	private UserService userService;
	
	@Autowired
	private HttpSession session;

	// 支払い方法の画面を表示
	@GetMapping("/choosepaymentmethod")
	public String getChoosePaymentMethodPage(Model model) {
		// セッションからユーザー情報を取得してUserEntityのオブジェクトとして確保
		UserEntity user = (UserEntity) session.getAttribute("user");
		// セッションからカートの中身を取得して、LinkedListに確保
		LinkedList<CourseEntity> cartList = (LinkedList<CourseEntity>) session.getAttribute("cart");
		
		// 次の３行はカートの合計金額を計算するためのメソッドです
		// １。合計金額を確保するためにtotalPriceというint の変数を制限して、０に設定する
		// ２。FOR繰り返しでカートの中身を反復する
		// ３。一つ一つカートの中身から講座価格を取得してtotalPriceに足す
		int totalPrice = 0;
		for (int idx = 0; idx < cartList.size(); idx++) {
			totalPrice += cartList.get(idx).getCourseFee();
		};
		int cartContentNumber = userService.getCartContentNumber();
		model.addAttribute("cartContentNumber", cartContentNumber);
		// 行き先のページに渡すためにtotalPriceの変数をMODELに設定する
		model.addAttribute("totalPrice", totalPrice);
		// 行き先のページに渡すためにuserの変数をMODELに設定する
		model.addAttribute("user", user);
		// 行き先のページに渡すためにcartlistの変数をMODELに設定する
		model.addAttribute("cartList", cartList);
		// 行き先のページにリダイレクト
		return "user_select_payment_method.html";
	}

	@GetMapping("/paymentdetails")
	public String getPaymentDetailsPage(Model model) {
		// セッションからユーザー情報を取得してUserEntityのオブジェクトとして確保
		UserEntity user = (UserEntity) session.getAttribute("user");
		// セッションからカートの中身を取得して、LinkedListに確保
		LinkedList<CourseEntity> cartList = (LinkedList<CourseEntity>) session.getAttribute("cart");
		
		// 次の３行はカートの合計金額を計算するためのメソッドです
		// １。合計金額を確保するためにtotalPriceというint の変数を制限して、０に設定する
		// ２。FOR繰り返しでカートの中身を反復する
		// ３。一つ一つカートの中身から講座価格を取得してtotalPriceに足す
		int totalPrice = 0;
		for (int idx = 0; idx < cartList.size(); idx++) {
			totalPrice += cartList.get(idx).getCourseFee();
		};
		
		int cartContentNumber = userService.getCartContentNumber();
		model.addAttribute("cartContentNumber", cartContentNumber);
		// 行き先のページに渡すためにtotalPriceの変数をMODELに設定する
		model.addAttribute("totalPrice", totalPrice);
		// 行き先のページに渡すためにuserの変数をMODELに設定する
		model.addAttribute("user", user);
		// 行き先のページに渡すためにcartlistの変数をMODELに設定する
		model.addAttribute("cartList", cartList);
		// 行き先のページにリダイレクト
		return "user_confirm_payment_details.html";
	}

	@GetMapping("/makepayment")
	public String makePayment(Model model) {
		UserEntity user = (UserEntity) session.getAttribute("user");
		Long userId = user.getStudentId();
		LinkedList<CourseEntity> cartList = (LinkedList<CourseEntity>) session.getAttribute("cart");
		// Make purchase list
		LinkedList<CourseEntity> purchaseList = (LinkedList<CourseEntity>) cartList.clone();
		
		// 次の３行はカートの合計金額を計算するためのメソッドです
		// １。合計金額を確保するためにtotalPriceというint の変数を制限して、０に設定する
		// ２。FOR繰り返しでカートの中身を反復する
		// ３。一つ一つカートの中身から講座価格を取得してtotalPriceに足す
		int totalPrice = 0;
		for (int idx = 0; idx < cartList.size(); idx++) {
			totalPrice += cartList.get(idx).getCourseFee();
		};
		
		// userIDとtotalPriceをtransactionHistoryServiceのcreateTransactionHistoryに渡して新しい取引を作ってデータベースに保存する
		transactionHistoryService.createTransactionHistory(userId, totalPrice);
		// ユーザーのIDを使ってあのユーザーの最新の取引のデータ‐を取得してtransactionの変数に保存する
		TransactionHistoryEntity transaction = transactionHistoryService.getTransactoinId(userId);
		// transactionから取引のIDを取得してtransactionIdの変数に保存する
		Long transactionId = transaction.getTransactionId();
		
		// for繰り返しでカートの中身を反復する
		for (int idx = 0; idx < cartList.size(); idx++) {
			// 講座のIDを取得してcourseIDの変数で保存
			Long courseId = cartList.get(idx).getCourseId();
			// transactionIdとcourseIdをtransactionItemsServiceのcreateTransactionHistoryに渡してtransactionItemをデータベースに保存する
			transactionItemsService.createTransactionHistory(transactionId, courseId);
		}
		
		model.addAttribute("purchaseList", purchaseList);
		cartList.clear();
		int cartContentNumber = userService.getCartContentNumber();
		model.addAttribute("cartContentNumber", cartContentNumber);
		// 行き先のページに渡すためにtotalPriceの変数をMODELに設定する
		model.addAttribute("totalPrice", totalPrice);
		// 行き先のページに渡すためにuserの変数をMODELに設定する
		model.addAttribute("user", user);
		// 行き先のページに渡すためにcartlistの変数をMODELに設定する
		model.addAttribute("cartList", cartList);
		// 行き先のページにリダイレクト	
		return "user_payment_confirmation.html";
	}
}
