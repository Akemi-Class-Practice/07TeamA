package teamA.ex.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import teamA.ex.model.entity.Contact;
import teamA.ex.model.entity.ContactEntityPK;

@Repository
public interface ContactRepository extends JpaRepository<Contact, ContactEntityPK> {
	//SQL
	@Query(value="SELECT c.contact_id,c.student_id,s.student_email,c.contact_title,c.contact_date,c.is_done,c.contact_detail from contacts c Join students s on c.student_id=s.student_id ORDER By is_done ASC",nativeQuery=true)
	List<Contact>findbyAllContactInfo();
	
	@Query(value="SELECT c.contact_id,c.student_id,s.student_email,c.contact_title,c.contact_date,c.is_done,c.contact_detail from contacts c Join students s on c.student_id=s.student_id WHERE is_done = 1 ORDER By is_done ASC",nativeQuery=true)
	List<Contact>findRead();
	
	@Query(value="SELECT c.contact_id,c.student_id,s.student_email,c.contact_title,c.contact_date,c.is_done,c.contact_detail from contacts c Join students s on c.student_id=s.student_id WHERE is_done = 0 ORDER By is_done ASC",nativeQuery=true)
	List<Contact>findUnread();
}


