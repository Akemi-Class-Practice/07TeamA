package teamA.ex.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import teamA.ex.model.entity.ContactEntity;

public interface ContactDao extends JpaRepository<ContactEntity, Long> {
	
	// ContactEntityを引数として受け取り、DBに保存
	ContactEntity save(ContactEntity contactEntity);
	
	//DBのCourseEntityを検索
	ContactEntity findByContactId(Long contactId);
	
	//全てを見るためのメソッド
	List<ContactEntity> findAllByOrderByIsDoneAsc();
}
