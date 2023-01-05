package com.siigfp.SIIGFPV1.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siigfp.SIIGFPV1.dao.NotificationDao;
import com.siigfp.SIIGFPV1.entity.demande.Notification;

@Service
public class NotificationService {
	
	@Autowired
	private NotificationDao notificationDao;
	
	public List<Notification> getNotifHeader (long id) {
		List<Notification> allNotif = new ArrayList<Notification>() ;
		try {
			allNotif = notificationDao.findTop3ByIdUsersOrderByIdNotificationDesc(id);
		} catch (Exception e) {
			
		}
		
		return allNotif;
	}
	public int getNumberNewNotification (long id) {
		int i = 0;
		List<Notification> allNotif = new ArrayList<Notification>() ;
		try {
			allNotif = notificationDao.findAllByIdUsersAndVue(id, false);
			
		} catch (Exception e) {
			
		}
		i = allNotif.size();
	
		return i;
	}
	public Optional<Notification> setVue (Long id) {
		Optional<Notification> n = null;
		n = notificationDao.findById(id);
		n.get().setVue(true);
		notificationDao.save(n.get());
		return  n;
	}
}
