package teamA.ex.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import teamA.ex.model.entity.ContactEntity;

public interface ContactDao extends JpaRepository<ContactEntity, Long> {
	
	// ContactEntityを引数として受け取り、DBに保存
	ContactEntity save(ContactEntity contactEntity);
	
	//DBのCourseEntityを検索
	ContactEntity findByContactId(Long contactId);
	
	//全てを見るためのメソッド
	List<ContactEntity> findAllByOrderByIsDoneAsc();
	
	// 未読のお問い合わせを数える
	@Query(value="SELECT COUNT(*) FROM contacts WHERE is_done = 0", nativeQuery = true)
	Long countTotalUnread(); 
}
