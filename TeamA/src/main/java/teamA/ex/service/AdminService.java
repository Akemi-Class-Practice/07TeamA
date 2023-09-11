package teamA.ex.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import teamA.ex.model.dao.AdminDao;
import teamA.ex.model.entity.AdminEntity;
import teamA.ex.model.entity.UserEntity;

@Service
public class AdminService {

	@Autowired
	private AdminDao adminDao;
	
	// Loginの処理
	public AdminEntity login(String email, String password) {
		// AdminDaoのfindByEmailを使ってユーザデータベースからユーザの情報を取得してAdminEntityでユーザーインスタンスを作る
		AdminEntity adminEntity = adminDao.findByAdminEmail(email);
		// 管理者インスタンスからユーザーのSALTを取得して
		String salt = adminEntity.getSalt();
		// ユーザーが入力したパスワードとSALTを組み合わせてhashPasswordに渡してハッシュする
		String hashPassword = hashPassword(password+salt);
		
		// あのhashPasswordがデータベースに保存してるhashedPasswordと同じ場合、adminEntityを戻す
		//　同じではない場合、NUllを戻す
		if (adminEntity.getPassword().equals(hashPassword)) {
			return adminEntity;
		} else {
			return null;
		}
	}
	
	// SHA-256を使ってplain-textのパスワードを暗号化する
		private String hashPassword(String password) {
			try {
				MessageDigest digest = MessageDigest.getInstance("SHA-256");
				byte[] hash = digest.digest(password.getBytes());
				return Base64.getEncoder().encodeToString(hash);
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException("Hashing Algorithm not found", e);
			}
		}
		
	public AdminEntity findByAdminId(Long adminId) {
		return adminDao.findByAdminId(adminId);
	}
		
	//Registerの処理
	public boolean createAdmin(String adminIcon, String adminName, String adminEmail, String password) {
		AdminEntity adminEntity = adminDao.findByAdminEmail(adminEmail);
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
		
		if(adminEntity==null) {
			adminDao.save(new AdminEntity(adminName, adminEmail, registerDate, deleteFlag, adminIcon, hashedPassword, salt));
			return true;
		}else {
			return false;
		}
	}
	
	public boolean updateAdmin(String currentUserEmail, String newIcon, String newUserName, String newUserEmail) {
		AdminEntity admin = adminDao.findByAdminEmail(currentUserEmail);
		
		if (admin==null) {
			return false;
		} else {
			admin.setAdminIcon(newIcon);
			admin.setAdminName(newUserName);
			admin.setAdminEmail(newUserEmail);
			adminDao.save(admin);
			return true;
		}
	}
	
	public boolean updateAdminPassword(String currentUserEmail, String newPassword) {
		AdminEntity admin = adminDao.findByAdminEmail(currentUserEmail);
		String salt = admin.getSalt();
		String newPasswordHash = hashPassword(newPassword+salt);
		
		if (admin==null) {
			return false;
		} else {
			admin.setPassword(newPasswordHash);
			adminDao.save(admin);
			return true;
		}
	}
}
