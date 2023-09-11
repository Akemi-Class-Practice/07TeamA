package teamA.ex.controller;

import java.util.Iterator;
import java.util.LinkedList;
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

		model.addAttribute("logoutMessage", "ログアウトしました");
		return "guest_view_courses";
	}

	// 講座詳細の表示処理
	@GetMapping("/guest/view/courseinfo")
	public String getCourseInfoViewPage(@PathVariable Long courseId, Model model) {
		CourseEntity course = courseService.getCourse(courseId);
		model.addAttribute("course", course);
//		model.addAttribute("loginFlg",logincheck());
//		model.addAttribute("userName",loginUserName());
		return "user_view_course_info.html";
	}

	public boolean isCourseExist(Long courseId, LinkedList<CourseEntity> list) {
		Iterator<CourseEntity> ite = list.iterator();
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

	// カートへの追加機能
	@GetMapping("/add/to/cart/{courseId}")
	public String addCourseToCart(@PathVariable Long courseId) {
		LinkedList<CourseEntity> cartList = (LinkedList<CourseEntity>) session.getAttribute("cart");
		CourseEntity course = courseService.getCourse(courseId);
		cartList.add(course);
		return "redirect:/home/user/view/courses";
	}

	// カートからの削除機能
	@GetMapping("/delete/from/cart/{courseId}")
	public String deleteCourseFromCart(@PathVariable Long courseId, Model model) {
//		Long courseId = course.getCourseId();
		LinkedList<CourseEntity> cartList = (LinkedList<CourseEntity>) session.getAttribute("cart");

		for (int idx = 0; idx < cartList.size(); idx++) {
			if (cartList.get(idx).getCourseId().equals(courseId)) {
				cartList.remove(idx);
				break;
			}
		};
		return "redirect:/home/cart";
	}

//	// カートの中身を見るための機能(もしかしたらいらない)
//	@PostMapping("/home/user/view/cart")
//	public String getCartPage(@RequestParam Long courseId, Model model) {
//
//		
//			LinkedList<CourseEntity> list = (LinkedList<CourseEntity>) session.getAttribute("cart");
//			return "user_view_cart.html";
//		
//
//	}

	// カートの中身を見るための機能
	@GetMapping("/cart")
	public String getCartPage(Model model) {
		LinkedList<CourseEntity> list = (LinkedList<CourseEntity>) session.getAttribute("cart");
		UserEntity user = (UserEntity) session.getAttribute("user");
		int totalPrice = 0;
		for (int idx = 0; idx < list.size(); idx++) {
			totalPrice += list.get(idx).getCourseFee();
		};
		
		model.addAttribute("user", user);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("list", list);
		return "user_view_cart.html";
	}

}















