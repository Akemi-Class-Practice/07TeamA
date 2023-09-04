package teamA.ex.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminDao extends JpaRepository<AdminEntity, Long>{
	
	// AdminEntityを引数として受け取り、AdminEntityを保存し、保存したAdminEntityを返す
	AdminEntity save(AdminEntity adminEntity);
	
	// String型の引数を受け取り、その引数と一致するemailを持つAdminEntityを返す
	AdminEntity findByEmail(String email);
	
	// データベース内のAdminEntityの中から、emailとpasswordと一致するものを検索し、それを返す
	AdminEntity findByEmailAndPassword(String email,String password);
	
	

}
