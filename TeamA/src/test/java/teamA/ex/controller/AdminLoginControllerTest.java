package teamA.ex.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import jakarta.servlet.http.HttpSession;
import teamA.ex.model.entity.AdminEntity;
import teamA.ex.service.AdminService;





@SpringBootTest
@AutoConfigureMockMvc
public class AdminLoginControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AdminService adminService;
	
	@BeforeEach
	// 事前のデータ準備
	public void prepareData() {
		AdminEntity adminEntity = new AdminEntity(1L,"admin","admin@admin.com",
				LocalDateTime.now(),0,"cat.jpg","admin","admin");
		when(adminService.login(eq("admin@admin.com"),eq("admin"))).thenReturn(adminEntity);
		when(adminService.login(eq("admin@test.com"),eq("admin"))).thenReturn(null);
		when(adminService.login(eq(""),eq("admin"))).thenReturn(null);
		when(adminService.login(eq("admin@admin.com"),eq("123456"))).thenReturn(null);
		when(adminService.login(eq("admin@admin.com"),eq(""))).thenReturn(null);
		when(adminService.login(eq(""),eq(""))).thenReturn(null);
		when(adminService.login(eq("admin@test.com"),eq("123456"))).thenReturn(null);
		}
	
	// ログインが正しく取得できるかのテスト
	@Test
	public void testGetAdminLoginPage_Succeed() throws Exception{
		RequestBuilder request = MockMvcRequestBuilders.get("/adminlogin");
		mockMvc.perform(request).andExpect(view().name("admin_login.html"));
	}
	
	// ログインが成功した場合のテスト
	@Test
	public void testLogin_Successful() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.post("/adminlogin/process")
				.param("email", "admin@admin.com")
				.param("password", "admin");
		
		// リクエストの実行結果を返すためのメソッドがandReturn()
		MvcResult result = mockMvc.perform(request)
				.andExpect(redirectedUrl("/home/admin/view/courses")).andReturn();
		
		// セッションの取得
		HttpSession session = result.getRequest().getSession();
		
		// セッションがきちんと設定出来ているかの確認テスト
		AdminEntity adminList = (AdminEntity) session.getAttribute("admin");
		assertNotNull(adminList);
		assertEquals("admin",adminList.getAdminName());
		assertEquals("admin",adminList.getPassword());		
	}
	
	// 管理者Emailが間違ってログインが失敗した場合
	@Test
	public void testLogin_WrongEmail_Unsuccessful() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.post("/adminlogin/process")
				.param("email", "admin@test.com")
				.param("password", "admin");
		
		// リクエストの実行結果を返すためのメソッドがandReturn()
		MvcResult result = mockMvc.perform(request)
				.andExpect(redirectedUrl("/adminlogin")).andReturn();
		
		// セッションの取得
		HttpSession session = result.getRequest().getSession();
			
	}
	
	// 管理者Emailが空白でログインが失敗した場合
	@Test
	public void testLogin_EmailNull_Unsuccessful() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.post("/adminlogin/process")
				.param("email", "")
				.param("password", "admin");
		
		// リクエストの実行結果を返すためのメソッドがandReturn()
		MvcResult result = mockMvc.perform(request)
				.andExpect(redirectedUrl("/adminlogin")).andReturn();
		
		// セッションの取得
		HttpSession session = result.getRequest().getSession();
			
	}
	
	// パスワードが間違ってログインが失敗した場合
	@Test
	public void testLogin_WrongPassword_Unsuccessful() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.post("/adminlogin/process")
				.param("email", "admin@admin.com")
				.param("password", "123456");
		
		// リクエストの実行結果を返すためのメソッドがandReturn()
		MvcResult result = mockMvc.perform(request)
				.andExpect(redirectedUrl("/adminlogin")).andReturn();
		
		// セッションの取得
		HttpSession session = result.getRequest().getSession();
			
	}
	
	// パスワードが空白でログインが失敗した場合
	@Test
	public void testLogin_PasswordNull_Unsuccessful() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.post("/adminlogin/process")
				.param("email", "admin@admin.com")
				.param("password", "");
		
		// リクエストの実行結果を返すためのメソッドがandReturn()
		MvcResult result = mockMvc.perform(request)
				.andExpect(redirectedUrl("/adminlogin")).andReturn();
		
		// セッションの取得
		HttpSession session = result.getRequest().getSession();
			
	}
	
	// 管理者Emailとパスワードが空白でログインが失敗した場合
	@Test
	public void testLogin_EmailAndPasswordNull_Unsuccessful() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.post("/adminlogin/process")
				.param("email", "")
				.param("password", "");
		
		// リクエストの実行結果を返すためのメソッドがandReturn()
		MvcResult result = mockMvc.perform(request)
				.andExpect(redirectedUrl("/adminlogin")).andReturn();
		
		// セッションの取得
		HttpSession session = result.getRequest().getSession();
			
	}
	
	// 管理者Emailとパスワードが間違ってログインが失敗した場合
	@Test
	public void testLogin_EmailAndPasswordWrong_Unsuccessful() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.post("/adminlogin/process")
				.param("email", "admin@test.com")
				.param("password", "123456");
		
		// リクエストの実行結果を返すためのメソッドがandReturn()
		MvcResult result = mockMvc.perform(request)
				.andExpect(redirectedUrl("/adminlogin")).andReturn();
		
		// セッションの取得
		HttpSession session = result.getRequest().getSession();
			
	}
	
	
	

}























