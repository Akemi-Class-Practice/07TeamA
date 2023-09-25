package teamA.ex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import teamA.ex.model.dao.ContactRepository;
import teamA.ex.model.entity.Contact;

@Service
public class NewContactService {

	@Autowired
	private ContactRepository contactRepository;
	
	public List<Contact> searchAll(){
		return contactRepository.findbyAllContactInfo();
	}
	
	public List<Contact> searchRead(){
		return contactRepository.findRead();
	}
	
	public List<Contact> searchUnread(){
		return contactRepository.findUnread();
	}
}
