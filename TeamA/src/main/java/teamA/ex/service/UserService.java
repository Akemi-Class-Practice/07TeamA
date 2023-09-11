package teamA.ex.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

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
			UserEntity userEntity = userDao.findByStudentEmail(email);
			String salt = userEntity.getSalt();
			String hashPassword = hashPassword(password+salt);
			
			if (userEntity.getPassword().equals(hashPassword)) {
				return userEntity;
			} else {
				return null;
			}			
		}
		
		
		public UserEntity findByStudentId(Long studentId) {
			return userDao.findByStudentId(studentId);
		}
		
		//Registerの処理
		public boolean createAccount(String studentIcon,String studentName,String studentEmail, String password) {
			UserEntity userEntity = userDao.findByStudentEmail(studentEmail);
			LocalDateTime registerDate = LocalDateTime.now();
			int deleteFlag = 0;
			// UUIDを使ってRandom文字を作る
			UUID uuid = UUID.randomUUID();
			// あの文字配列を文字配列化
			String saltStr = uuid.toString();
			// あの文字配列を最初の１０文字に絞り込む
			String salt = saltStr.substring(0,10);
			// あの文字配列をユーザーを入力したパスワードと組み合わせてHasedPasswordに渡してHashedPasswordを作る
			String hashedPassword = hashPassword(password+salt);
			
			
			if(userEntity==null) {
				userDao.save(new UserEntity(studentName,studentEmail,registerDate, deleteFlag, studentIcon, hashedPassword, salt));
				return true;
			}else {
				return false;
			}
		}
		
		public boolean updateUser(String currentUserEmail, String newIcon, String newUserName, String newUserEmail) {
			UserEntity user = userDao.findByStudentEmail(currentUserEmail);
			
			if (user==null) {
				return false;
			} else {
				user.setStudentIcon(newIcon);
				user.setStudentName(newUserName);
				user.setStudentEmail(newUserEmail);
				userDao.save(user);
				return true;
			}
		}
		
		public boolean updateUserPassword(String currentUserEmail, String newPassword) {
			UserEntity user = userDao.findByStudentEmail(currentUserEmail);
			String salt = user.getSalt();
			String newPasswordHash = hashPassword(newPassword+salt);
			
			if (user==null) {
				return false;
			} else {
				user.setPassword(newPasswordHash);
				userDao.save(user);
				return true;
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
		
		
}
