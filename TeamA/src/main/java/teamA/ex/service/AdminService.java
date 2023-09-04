package teamA.ex.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.springframework.stereotype.Service;

import teamA.ex.model.entity.AdminEntity;

@Service
public class AdminService {

	// Loginの処理
	public AdminEntity login(String email, String password) {
		// AdminDaoのfindByEmailを使ってユーザデータベースからユーザの情報を取得してAdminEntityでユーザーインスタンスを作る
		AdminEntity adminEntity = adminDao.findByEmail(email);
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
}
