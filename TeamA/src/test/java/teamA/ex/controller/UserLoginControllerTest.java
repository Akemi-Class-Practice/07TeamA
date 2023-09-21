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
import teamA.ex.model.entity.UserEntity;
import teamA.ex.service.UserService;



@SpringBootTest
@AutoConfigureMockMvc
public class UserLoginControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	@BeforeEach
	// 事前のデータ準備
	public void prepareData() {
	UserEntity userEntity = new UserEntity(1L,"user","user@user.com",
			LocalDateTime.now(),0,"cat.jpg","user","user");
	when(userService.login(eq("user@user.com"),eq("user"))).thenReturn(userEntity);
	when(userService.login(eq("user@test.com"),eq("user"))).thenReturn(null);
	when(userService.login(eq(""),eq("user"))).thenReturn(null);
	when(userService.login(eq("user@user.com"),eq("123456"))).thenReturn(null);
	when(userService.login(eq("user@user.com"),eq(""))).thenReturn(null);
	when(userService.login(eq(""),eq(""))).thenReturn(null);
	when(userService.login(eq("user@test.com"),eq("123456"))).thenReturn(null);
	}
	
	// ログインが正しく取得できるかのテスト
	@Test
	public void testGetUserLoginPage_Succeed() throws Exception{
		RequestBuilder request = MockMvcRequestBuilders.get("/userlogin");
		mockMvc.perform(request).andExpect(view().name("user_login.html"));
		
	}
	
	// ログインが成功した場合のテスト
	@Test
	public void testLogin_Successful() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.post("/userlogin/process")
				.param("email", "user@user.com")
				.param("password", "user");
		
		// リクエストの実行結果を返すためのメソッドがandReturn()
		MvcResult result = mockMvc.perform(request)
				.andExpect(redirectedUrl("/home/user/view/courses")).andReturn();
		
		// セッションの取得
		HttpSession session = result.getRequest().getSession();
		
		// セッションがきちんと設定出来ているかの確認テスト
		UserEntity userList = (UserEntity) session.getAttribute("user");
		assertNotNull(userList);
		assertEquals("user",userList.getStudentName());
		assertEquals("user",userList.getPassword());		
	}
	
	// ユーザーEmailが間違ってログインが失敗した場合
	@Test
	public void testLogin_WrongEmail_Unsuccessful() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.post("/userlogin/process")
				.param("email", "user@test.com")
				.param("password", "user");
		
		// リクエストの実行結果を返すためのメソッドがandReturn()
		MvcResult result = mockMvc.perform(request)
				.andExpect(redirectedUrl("/userlogin")).andReturn();
		
		// セッションの取得
		HttpSession session = result.getRequest().getSession();
			
	}
	
	// ユーザーEmailが空白でログインが失敗した場合
	@Test
	public void testLogin_EmailNull_Unsuccessful() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.post("/userlogin/process")
				.param("email", "")
				.param("password", "user");
		
		// リクエストの実行結果を返すためのメソッドがandReturn()
		MvcResult result = mockMvc.perform(request)
				.andExpect(redirectedUrl("/userlogin")).andReturn();
		
		// セッションの取得
		HttpSession session = result.getRequest().getSession();
			
	}
	
	// パスワードが間違ってログインが失敗した場合
	@Test
	public void testLogin_WrongPassword_Unsuccessful() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.post("/userlogin/process")
				.param("email", "user@user.com")
				.param("password", "123456");
		
		// リクエストの実行結果を返すためのメソッドがandReturn()
		MvcResult result = mockMvc.perform(request)
				.andExpect(redirectedUrl("/userlogin")).andReturn();
		
		// セッションの取得
		HttpSession session = result.getRequest().getSession();
			
	}
	
	// パスワードが空白でログインが失敗した場合
	@Test
	public void testLogin_PasswordNull_Unsuccessful() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.post("/userlogin/process")
				.param("email", "user@user.com")
				.param("password", "");
		
		// リクエストの実行結果を返すためのメソッドがandReturn()
		MvcResult result = mockMvc.perform(request)
				.andExpect(redirectedUrl("/userlogin")).andReturn();
		
		// セッションの取得
		HttpSession session = result.getRequest().getSession();
			
	}
	
	// ユーザーEmailとパスワードが空白でログインが失敗した場合
	@Test
	public void testLogin_EmailAndPasswordNull_Unsuccessful() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.post("/userlogin/process")
				.param("email", "")
				.param("password", "");
		
		// リクエストの実行結果を返すためのメソッドがandReturn()
		MvcResult result = mockMvc.perform(request)
				.andExpect(redirectedUrl("/userlogin")).andReturn();
		
		// セッションの取得
		HttpSession session = result.getRequest().getSession();
			
	}
	
	// ユーザーEmailとパスワードが間違ってログインが失敗した場合
	@Test
	public void testLogin_EmailAndPasswordWrong_Unsuccessful() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.post("/userlogin/process")
				.param("email", "user@test.com")
				.param("password", "123456");
		
		// リクエストの実行結果を返すためのメソッドがandReturn()
		MvcResult result = mockMvc.perform(request)
				.andExpect(redirectedUrl("/userlogin")).andReturn();
		
		// セッションの取得
		HttpSession session = result.getRequest().getSession();
			
	}
	
	
	
	
	
	
	
	

}






















