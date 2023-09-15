package teamA.ex.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import teamA.ex.model.dao.ContactDao;
import teamA.ex.model.entity.ContactEntity;

@Service
public class ContactService {
	@Autowired
	private ContactDao contactDao;

	// ユーザー側のcontactを追加するメソッドです
	public boolean createContact(String contactTitle, String contactDetail, LocalDate contactDate, Long studentId, int isDone) {
		if (studentId == null) {
			return false;

		} else {
			contactDao.save(new ContactEntity(contactTitle, contactDetail, contactDate, studentId, isDone));
			return true;
		}
	}

	public List<ContactEntity> findAllByOrderByIsDoneAsc() {
		return contactDao.findAllByOrderByIsDoneAsc();
	}

	//管理者側のcontactの表示するメソッドです
	public boolean isDone(Long contactId, int state) {
		ContactEntity ContactList = contactDao.findByContactId(contactId);
		if (ContactList == null) {
			return false;
		} else {
			ContactList.setIsDone(state);
			contactDao.save(ContactList);
			return true;
		}
	}
	
	public ContactEntity findByContactId(Long contactId) {
		return contactDao.findByContactId(contactId);
	}

}
