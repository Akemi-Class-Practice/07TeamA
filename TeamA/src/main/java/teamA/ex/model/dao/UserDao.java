package teamA.ex.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import teamA.ex.model.entity.UserEntity;

public interface UserDao extends JpaRepository<UserEntity, Long> {

		// UserEntityを引数として受け取り、UserEntityを保存し、保存したUserEntityを返す
		UserEntity save(UserEntity userEntity);

		// String型の引数を受け取り、その引数と一致するemailを持つUserEntityを返す
		UserEntity findByStudentEmail(String email);
		
		UserEntity findByStudentId(Long studentId);

		// データベース内のUserEntityの中から、emailとpasswordと一致するものを検索し、それを返す
//		UserEntity findByUserEmailAndPassword(String email, String password);
}
