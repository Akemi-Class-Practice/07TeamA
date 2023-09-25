package teamA.ex.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.text.SimpleDateFormat;
import java.util.Date;

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

import teamA.ex.model.entity.AdminEntity;
import teamA.ex.service.AdminService;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminRegisterControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	private MockHttpSession session;
	
	@MockBean
	private AdminService adminService;
	
	@BeforeEach
	public void prepareDate() {
		AdminEntity admin = new AdminEntity();
		admin.setAdminId(1L);
		admin.setAdminName("admin");
		admin.setAdminIcon("flower.jpg");
		session = new MockHttpSession();
		session.setAttribute("admin", admin);
	}
	
	//画面が正常に表示できるテスト
	@Test
	public void testGetAdminAddPage_Succeed() throws Exception{
		AdminEntity admin = new AdminEntity();
		admin.setAdminId(1L);
		admin.setAdminName("admin");
		admin.setAdminIcon("2023-09-20-17-21-11-flower.jpg");
		session = new MockHttpSession();
		session.setAttribute("admin", admin);
		
		RequestBuilder request = MockMvcRequestBuilders
				.get("/home/admin/register")
				.session(session);
		//adminIcon持ってこなくては
		mockMvc.perform(request)
		.andExpect(view().name("admin_add_admin.html"))
		.andExpect(model().attribute("admin", admin));
	}
	
	//登録が成功する場合のテスト
	@Test
	public void testAdminAdd_Succeed() throws Exception{
		when(adminService.createAdmin(anyString(), anyString(), anyString(), anyString())).thenReturn(true);
		
		String adminImg = "cat3.jpg";
		//String adminImgName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date()) + adminImg;
		
		MockMultipartFile adminIcon = new MockMultipartFile("admin_icon", adminImg, "image/jpeg", new byte[0]);
		
		mockMvc.perform(MockMvcRequestBuilders.multipart("/home/admin/register/process")
				.file(adminIcon)
				.param("admin_name","admin2")
				.param("admin_email","admin2@admin2.com")
				.param("password","admin2")
				.session(session))
		
		.andExpect(redirectedUrl("/home/admin/view/courses"));
	}
	
	//名前が空白で登録が失敗するテスト
		@Test
		public void testAdminAdd_NoAdminName_Unsuccessful() throws Exception{
			when(adminService.createAdmin(anyString(), anyString(), anyString(), anyString())).thenReturn(false);
			
			String adminImg = "cat.jpg";
			//String adminImgName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date()) + adminImg;
			
			MockMultipartFile adminIcon = new MockMultipartFile("admin_icon", adminImg, "image/jpeg", new byte[0]);
			
			mockMvc.perform(MockMvcRequestBuilders.multipart("/home/admin/register/process")
					.file(adminIcon)
					.param("admin_name","")
					.param("admin_email","admin2@admin2.com")
					.param("password","admin2")
					.session(session))
			
			.andExpect(redirectedUrl("/home/admin/register"));
		}
		
		//e-mailが空白で登録が失敗するテスト
		@Test
		public void testAdminAdd_NoAdminEmail_Unsuccessful() throws Exception{
			when(adminService.createAdmin(anyString(), anyString(), anyString(), anyString())).thenReturn(false);
			
			String adminImg = "cat.jpg";
			//String adminImgName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date()) + adminImg;
			
			MockMultipartFile adminIcon = new MockMultipartFile("admin_icon", adminImg, "image/jpeg", new byte[0]);
			
			mockMvc.perform(MockMvcRequestBuilders.multipart("/home/admin/register/process")
					.file(adminIcon)
					.param("admin_name","admin2")
					.param("admin_email","")
					.param("password","admin2")
					.session(session))
			
			.andExpect(redirectedUrl("/home/admin/register"));
		}
		
		//パスワードが空白で登録が失敗するテスト
		@Test
		public void testAdminAdd_NoPassword_Unsuccessful() throws Exception{
			when(adminService.createAdmin(anyString(), anyString(), anyString(), anyString())).thenReturn(false);
			
			String adminImg = "cat.jpg";
			//String adminImgName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date()) + adminImg;
			
			MockMultipartFile adminIcon = new MockMultipartFile("admin_icon", adminImg, "image/jpeg", new byte[0]);
			
			mockMvc.perform(MockMvcRequestBuilders.multipart("/home/admin/register/process")
					.file(adminIcon)
					.param("admin_name","admin2")
					.param("admin_email","admin2@admin2.com")
					.param("password","")
					.session(session))
			
			.andExpect(redirectedUrl("/home/admin/register"));
		}

		//名前が間違って登録が失敗するテスト
		@Test
		public void testAdminAdd_WrongName_Unsuccessful() throws Exception{
			when(adminService.createAdmin(anyString(), anyString(), anyString(), anyString())).thenReturn(false);
			
			String adminImg = "cat.jpg";
			//String adminImgName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date()) + adminImg;
			
			MockMultipartFile adminIcon = new MockMultipartFile("admin_icon", adminImg, "image/jpeg", new byte[0]);
			
			mockMvc.perform(MockMvcRequestBuilders.multipart("/home/admin/register/process")
					.file(adminIcon)
					.param("admin_name","admin3")
					.param("admin_email","admin2@admin2.com")
					.param("password","admin2")
					.session(session))
			
			.andExpect(redirectedUrl("/home/admin/register"));
		}
		

		//メールが間違って登録が失敗するテスト
		@Test
		public void testAdminAdd_WrongEmail_Unsuccessful() throws Exception{
			when(adminService.createAdmin(anyString(), anyString(), anyString(), anyString())).thenReturn(false);
			
			String adminImg = "cat.jpg";
			//String adminImgName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date()) + adminImg;
			
			MockMultipartFile adminIcon = new MockMultipartFile("admin_icon", adminImg, "image/jpeg", new byte[0]);
			
			mockMvc.perform(MockMvcRequestBuilders.multipart("/home/admin/register/process")
					.file(adminIcon)
					.param("admin_name","admin2")
					.param("admin_email","admin3@admin3.com")
					.param("password","admin2")
					.session(session))
			
			.andExpect(redirectedUrl("/home/admin/register"));
		}
		

		//パスワードが間違って登録が失敗するテスト
		@Test
		public void testAdminAdd_WrongPassword_Unsuccessful() throws Exception{
			when(adminService.createAdmin(anyString(), anyString(), anyString(), anyString())).thenReturn(false);
			
			String adminImg = "cat.jpg";
			//String adminImgName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date()) + adminImg;
			
			MockMultipartFile adminIcon = new MockMultipartFile("admin_icon", adminImg, "image/jpeg", new byte[0]);
			
			mockMvc.perform(MockMvcRequestBuilders.multipart("/home/admin/register/process")
					.file(adminIcon)
					.param("admin_name","admin2")
					.param("admin_email","admin2@admin2.com")
					.param("password","admin3")
					.session(session))
			
			.andExpect(redirectedUrl("/home/admin/register"));
		}
}
