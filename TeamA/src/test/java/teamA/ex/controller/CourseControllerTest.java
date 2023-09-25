package teamA.ex.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import teamA.ex.model.entity.AdminEntity;
import teamA.ex.model.entity.CourseEntity;

@SpringBootTest
@AutoConfigureMockMvc
public class CourseControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	
	private MockHttpSession session;

	@BeforeEach
	public void prepareData() {
		AdminEntity admin = new AdminEntity();
		admin.setAdminId(1L);
		admin.setAdminName("admin");

		List<CourseEntity> courseList = new ArrayList<>();
		courseList.add(new CourseEntity());
		

		session = new MockHttpSession();
		session.setAttribute("admin", admin);
		}
	
		//コース一覧画面の飛ぶ
		@Test
		public void testGetCourseListPage() throws Exception {
			RequestBuilder request = MockMvcRequestBuilders.get("/admin/course/list")
					.session(session);

			mockMvc.perform(request)
			.andExpect(status().isOk())
			.andExpect(view().name("admin_view_courses.html"))
			.andExpect(model().attributeExists("adminName", "courseList"));
		}
		//コースの追加
		@Test
		public void testGetCourseRegisterPage() throws Exception {
			AdminEntity admin = new AdminEntity();
			admin.setAdminId(1L);
			admin.setAdminName("admin");

			session.setAttribute("admin", admin);

			mockMvc.perform(get("/admin/edit/course").session(session))
			.andExpect(status().isOk())
			.andExpect(view().name("admin_edit_course.html"))
			.andExpect(model().attributeExists("adminName"));

		}	
		
		//コースの編集
		@Test
		public void testGetCourseEditPage() throws Exception {
			AdminEntity admin = new AdminEntity();
			admin.setAdminId(1L);
			admin.setAdminName("admin");
			session.setAttribute("admin", admin);

			CourseEntity course = new CourseEntity();
			course.setCourseId(1L);
			course.setCourseName("Test Course");
			course.setRegisterDate(LocalDate.parse("2023-09-21"));

			// テスト実行
			mockMvc.perform(get("/admin/view/courses/editcourse/{courseId}", 1L).session(session))
			.andExpect(status().isOk())
			.andExpect(view().name("admin_edit_course.html"))
			.andExpect(model().attributeExists("adminName", "courseList"))
			.andExpect(model().attribute("adminName", "admin"))
			.andExpect(model().attribute("course", course));
			
		}
		
		//コースの削除
		@Test
		public void testGetCourseDeleteListPage() throws Exception {
		    AdminEntity admin = new AdminEntity();
		    admin.setAdminId(1L);
		    admin.setAdminName("admin");

		    session.setAttribute("admin", admin);

		    List<CourseEntity> courseList = new ArrayList<>();
		    courseList.add(new CourseEntity());
		    courseList.add(new CourseEntity());

		    

		    mockMvc.perform(get("/admin/course/delete/list").session(session))
		        .andExpect(status().isOk())
		        .andExpect(model().attributeExists("adminName", "courseList"))
		        .andExpect(model().attribute("adminName", "admin"))
		        .andExpect(model().attribute("courseList", courseList));
		    
		}

		
	
}