package teamA.ex.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import teamA.ex.model.dao.AdminDao;
import teamA.ex.model.dao.UserDao;
import teamA.ex.model.entity.UserEntity;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	// Loginの処理
		public UserEntity login(String email, String password) {
			
			UserEntity userEntity = userDao.findByUserEmail(email);
			
			String salt = userEntity.getSalt();
			
			String hashPassword = hashPassword(password+salt);
			
			if (userEntity.getPassword().equals(hashPassword)) {
				return userEntity;
			} else {
				return null;
			}			
		}
		private String hashPassword(String password) {
			try {
				MessageDigest digest = MessageDigest.getInstance("SHA-256");
				byte[] hash = digest.digest(password.getBytes());
				return Base64.getEncoder().encodeToString(hash);
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException("Hashing Algorithm not found", e);
			}
		}
		
		
		//Registerの処理
		public boolean createUser(String name,String email,String password) {
			LocalDateTime registerDate = LocalDateTime.now();
			
			UserEntity userEntity = userDao.findByUserEmail(email);
			
			if(userEntity==null) {
				userDao.save(new UserEntity(name,email,password,registerDate));
				return true;
			}else {
				return false;
			}
		}
		
}
