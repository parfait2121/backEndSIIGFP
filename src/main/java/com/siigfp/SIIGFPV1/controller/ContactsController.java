package com.siigfp.SIIGFPV1.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.siigfp.SIIGFPV1.dao.ContactDao;
import com.siigfp.SIIGFPV1.entity.Contact;

@RestController
public class ContactsController {
	
	@Autowired
	private ContactDao contactDao;
	
	
	@PostMapping("/sendMessageContact")
	public Contact sendMessageContact (@RequestBody Contact contactBody) {
		 return contactDao.save(contactBody);
	}
	

	
}
