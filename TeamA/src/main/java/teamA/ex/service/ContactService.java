package teamA.ex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import teamA.ex.model.dao.ContactDao;
import teamA.ex.model.entity.ContactEntity;

@Service
public class ContactService {
	@Autowired
	private ContactDao contactDao;

	// contactを追加するメソッドです
	public boolean createContact(String contactTitle, String contactDetail, Long studentId, int isDone) {
		if (studentId == null) {
			return false;

		} else {
			contactDao.save(new ContactEntity(contactTitle, contactDetail, studentId, isDone));
			return true;
		}
	}

	public List<ContactEntity> findAll() {
		return contactDao.findAll();
	}

	
	public boolean isDone(Long contactId) {
		ContactEntity ContactList = contactDao.findByContactId(contactId);
		if (ContactList == null) {
			return false;
		} else {
			ContactList.setIsDone(1);
			contactDao.save(ContactList);
			return true;
		}
	}

}
