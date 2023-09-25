package teamA.ex.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import teamA.ex.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class UserRegisterControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	//画面が正常に表示できるテスト
		@Test
		public void testGetUserRegisterPage_Succeed() throws Exception{
			RequestBuilder request = MockMvcRequestBuilders
					.get("/userregister");
			
			mockMvc.perform(request)
			.andExpect(view().name("user_register.html"));
		}
		
	//登録が成功する場合のテスト
		@Test
		public void testUserRegister_Succeed() throws Exception{
			when(userService.createAccount(anyString(), anyString(), anyString(), anyString())).thenReturn(true);
			
			String userImg = "cat.jpg";
			//String adminImgName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date()) + adminImg;
			
			MockMultipartFile userIcon = new MockMultipartFile("user_icon", userImg, "image/jpeg", new byte[0]);
			
			mockMvc.perform(MockMvcRequestBuilders.multipart("/userregister/process")
					.file(userIcon)
					.param("user_name","user")
					.param("email","user@user.com")
					.param("password","user"))
			
			.andExpect(redirectedUrl("/userlogin"));
		}
		
	//名前が空白で登録が失敗するテスト
		@Test
		public void testUserRegister_NoName_Unsuccessful() throws Exception{
			when(userService.createAccount(anyString(), anyString(), anyString(), anyString())).thenReturn(false);
			
			String userImg = "cat.jpg";
			//String adminImgName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date()) + adminImg;
			
			MockMultipartFile userIcon = new MockMultipartFile("user_icon", userImg, "image/jpeg", new byte[0]);
			
			mockMvc.perform(MockMvcRequestBuilders.multipart("/userregister/process")
					.file(userIcon)
					.param("user_name","")
					.param("email","user@user.com")
					.param("password","user"))
			
			.andExpect(redirectedUrl("/userregister"));
		}
		
	//メールが空白で登録が失敗するテスト
		@Test
		public void testUserRegister_NoEmail_Unsuccessful() throws Exception{
			when(userService.createAccount(anyString(), anyString(), anyString(), anyString())).thenReturn(false);
			
			String userImg = "cat.jpg";
			//String adminImgName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date()) + adminImg;
			
			MockMultipartFile userIcon = new MockMultipartFile("user_icon", userImg, "image/jpeg", new byte[0]);
			
			mockMvc.perform(MockMvcRequestBuilders.multipart("/userregister/process")
					.file(userIcon)
					.param("user_name","user")
					.param("email","")
					.param("password","user"))
			
			.andExpect(redirectedUrl("/userregister"));
		}
		
	//パスワードが空白で登録が失敗するテスト
		@Test
		public void testUserRegister_NoPassword_Unsuccessful() throws Exception{
			when(userService.createAccount(anyString(), anyString(), anyString(), anyString())).thenReturn(false);
			
			String userImg = "cat.jpg";
			//String adminImgName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date()) + adminImg;
			
			MockMultipartFile userIcon = new MockMultipartFile("user_icon", userImg, "image/jpeg", new byte[0]);
			
			mockMvc.perform(MockMvcRequestBuilders.multipart("/userregister/process")
					.file(userIcon)
					.param("user_name","user")
					.param("email","user@user.com")
					.param("password",""))
			
			.andExpect(redirectedUrl("/userregister"));
		}
		
	//名前が間違って登録が失敗するテスト
		@Test
		public void testUserRegister_WrongName_Unsuccessful() throws Exception{
			when(userService.createAccount(anyString(), anyString(), anyString(), anyString())).thenReturn(false);
			
			String userImg = "cat.jpg";
			//String adminImgName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date()) + adminImg;
			
			MockMultipartFile userIcon = new MockMultipartFile("user_icon", userImg, "image/jpeg", new byte[0]);
			
			mockMvc.perform(MockMvcRequestBuilders.multipart("/userregister/process")
					.file(userIcon)
					.param("user_name","user2")
					.param("email","user@user.com")
					.param("password","user"))
			
			.andExpect(redirectedUrl("/userregister"));
		}
		
	//メールが間違って登録が失敗するテスト
		@Test
		public void testUserRegister_WrongEmail_Unsuccessful() throws Exception{
			when(userService.createAccount(anyString(), anyString(), anyString(), anyString())).thenReturn(false);
			
			String userImg = "cat.jpg";
			//String adminImgName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date()) + adminImg;
			
			MockMultipartFile userIcon = new MockMultipartFile("user_icon", userImg, "image/jpeg", new byte[0]);
			
			mockMvc.perform(MockMvcRequestBuilders.multipart("/userregister/process")
					.file(userIcon)
					.param("user_name","user")
					.param("email","user@test.com")
					.param("password","user"))
			
			.andExpect(redirectedUrl("/userregister"));
		}
		
	//パスワードが間違って登録が失敗するテスト
		@Test
		public void testUserRegister_WrongPassword_Unsuccessful() throws Exception{
			when(userService.createAccount(anyString(), anyString(), anyString(), anyString())).thenReturn(false);
			
			String userImg = "cat.jpg";
			//String adminImgName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date()) + adminImg;
			
			MockMultipartFile userIcon = new MockMultipartFile("user_icon", userImg, "image/jpeg", new byte[0]);
			
			mockMvc.perform(MockMvcRequestBuilders.multipart("/userregister/process")
					.file(userIcon)
					.param("user_name","user")
					.param("email","user@user.com")
					.param("password","123456"))
			
			.andExpect(redirectedUrl("/userregister"));
		}
}
