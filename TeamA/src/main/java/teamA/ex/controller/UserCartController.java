package teamA.ex.controller;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

import teamA.ex.model.entity.CourseEntity;
import teamA.ex.model.entity.UserEntity;
import teamA.ex.service.CourseService;

@Controller
@RequestMapping("/home")
public class UserCartController {
	
	// 講座(Course)にアクセスして操作するため、CourseServiceクラスを使えるようにする。
	@Autowired
	CourseService courseService;
	
	// HttpSessionを取得可能にしておく
	@Autowired
	HttpSession session;
	
	
	// 購入履歴情報テーブル(transaction_history)にアクセスして操作するため、
	// TransasctionHistoryServiceクラス　を使えるようにする
//	@Autowired
//	TransactionHistoryService transactionHistoryService;
	
	// 購入履歴情報テーブル(transaction_history)にアクセスして操作するため、
	// TransactoinItemServiceクラス を使えるようにしておく
//	@Autowired
//	TransactionItemService transactionItemService;
	
	
	// メール送信用クラス
//	@Autowired
//	MailSender mailSender;
	
	// ユーザーが購入した商品一覧テーブルにアクセスして操作するため、
	// SubscriptionServiceクラスを使えるようにしておく
//	@Autowired
//	SubscriptionService subscriptionService;
	
	
//	// メニュー画面 表示の処理
//	@GetMapping("")
//	public String getCourseViewPage(Model model) {
//		
//		// courseテーブルから公開されている講座情報のみ取得する
//		List<CourseEntity> courseList = courseService.findCoursesForUser();
//		
//		// modelへ、courseテーブルから取得した情報をセットする。
//		model.addAttribute("courseList", courseList);
//		
//		// modelへ、logincheckメソッドから取得した情報をセットする
////		model.addAttribute("loginflg",logincheck());
//		
//		//modelへ、loginUserNameメソッドから取得した情報をセットする。
////		model.addAttribute("userName",loginUserName());
//		
//		return"user_view_courses.html";
//	
//	
//	}
//	
	
	// ログアウトの処理
	@GetMapping("/guest/view/courses")
	public String getCourseLogoutViewPage(Model model) {
		
		// courseテーブルから公開されている講座情報のみ取得する
		List<CourseEntity> courseList = courseService.findCoursesForUser();
		
		// modelへ、courseテーブルから取得した情報をセットする。
		model.addAttribute("courseList", courseList);
		
		// modelへ、ログアウト処理のため、ログインフラグにfalseをセットする。
//		model.addAttribute("loginflg",false);
		
		// modelへ、ログアウト処理のため、ログインしているユーザー情報にnullをセットする。
//		model.addAttribute("userName", null);
		
		model.addAttribute("logoutMessage","ログアウトしました");
		return"guest_view_courses";	
	}
	
	// 講座詳細の表示処理
	@GetMapping("/guest/view/courseinfo")
	public String getCourseInfoViewPage(@PathVariable Long courseId,Model model) {
		CourseEntity course = courseService.getCourse(courseId);
		model.addAttribute("course",course);
//		model.addAttribute("loginFlg",logincheck());
//		model.addAttribute("userName",loginUserName());
		return"user_view_course_info.html";
	}
	
	public boolean isCourseExist(Long courseId,LinkedList<CourseEntity> list) {
		Iterator<CourseEntity>ite = list.iterator();
		boolean isExist = false;
		while (ite.hasNext()) {
			CourseEntity course = ite.next();
			if (course.getCourseId().equals(courseId)) {
				isExist = true;
				break;
			}
		}
		return isExist;

	}
	
//	public boolean loginCheck() {
//		if (session.getAttribute("user") == null) {
//			return false;
//		} else {
//			return true;
//		}
//	}
//
//	public String loginUserName() {
//		if (loginCheck() == true) {
//			UserEntity student = (UserEntity) session.getAttribute("user");
//			String userName = user.getUserName();
//			return userName;
//		} else {
//			return null;
//		}
//	}
//	
	
	
	
	
	

}
















