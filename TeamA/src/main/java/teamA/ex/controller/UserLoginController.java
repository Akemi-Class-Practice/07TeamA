package teamA.ex.controller;

import java.time.LocalDateTime;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import teamA.ex.model.entity.CourseEntity;
import teamA.ex.model.entity.UserEntity;
import teamA.ex.service.UserService;
import teamA.ex.service.LoginAnalyticService;


@Controller
public class UserLoginController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private HttpSession session;
	@Autowired 
	private LoginAnalyticService loginAnalyticService;
	
	// GetMappingで管理者のログイン画面の表示
	@GetMapping("/userlogin")
	public String getUserLoginPage() {
		return "user_login.html";
	}
	
	// PostMappingでuserlogin/processのPOST情報を取得
	// @RequestParamでHTMLのFORMAのINPUTを受け取る
	// ユーザーログインするとセッションが初期化する
	@PostMapping("/userlogin/process")
	public String login(@RequestParam String email, @RequestParam String password, HttpServletRequest request) {
		// 受け取ったemailとpasswordを使ってUserEntityのuserServiceでログインする
		UserEntity user = userService.login(email, password);
		//　以下のLinkedListが初期化を行う
		LinkedList<CourseEntity> cartList = new LinkedList<CourseEntity>(); 
		
		// loginメソッドが失敗する場合、userloginのページに移動する
		// 成功した場合、ユーザのインスタンスを使ってセッションを初めてに移動する
		if (userService.login(email, password) == null) {
			return "redirect:/userlogin";
		} else {
			// 次の２行はユーザーログインを分析データベースに入れるためのコード
			// 1. ユーザーのIDを取得する
			// 2.　ユーザーIDとHTTPSERVLETREQUESTをrecordVisitに渡してログイン情報をデータベースに保存する
			Long studentId = user.getStudentId();
			recordVisitAnalytics(request, studentId);
			// ユーザーのオブジェクトにセッションを保存する
			session.setAttribute("user", user);
			// リストにセッションを保存する
			session.setAttribute("cart", cartList);
			return "redirect:/home/user/view/courses";
		}
	}
	
	//ログアウト処理
	@GetMapping("/userlogout")
	public String Logout() {
		
		// セッションを切ってログインページに移動する
		session.invalidate();
		return "redirect:/userlogin";
	}
	
	// ログイン情報「分析データ‐」を保存するメソッド
	public void recordVisitAnalytics(HttpServletRequest request, Long studentId) {
		// ユーザーのIPAddressを取得する
		String userIpAddress = request.getRemoteAddr();
		// ログインする時、現在の日付と時間を取得する
		LocalDateTime visitDateTime = LocalDateTime.now();
		// loginAnalyticServiceのcreateAnalytic渡してデータベースに保存する
		loginAnalyticService.createAnalytic(visitDateTime, userIpAddress, studentId);
	}

}
