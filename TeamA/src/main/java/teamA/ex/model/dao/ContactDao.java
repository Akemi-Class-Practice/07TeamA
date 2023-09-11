package teamA.ex.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import teamA.ex.model.entity.ContactEntity;

public interface ContactDao extends JpaRepository<ContactEntity, Long> {
	
	//データベースに保存
	ContactEntity save(ContactEntity contactEntity);
	
	//DBのCourseEntityを検索
	ContactEntity findByContactId(Long contactId);
}
