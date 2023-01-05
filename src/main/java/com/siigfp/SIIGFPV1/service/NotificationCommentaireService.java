package com.siigfp.SIIGFPV1.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siigfp.SIIGFPV1.dao.NotificationCommentaireDao;
import com.siigfp.SIIGFPV1.entity.demande.Notification;
import com.siigfp.SIIGFPV1.entity.demande.NotificationCommentaire;

@Service
public class NotificationCommentaireService {

	@Autowired
	private NotificationCommentaireDao notificationCommentaireDao;
	
	public List<NotificationCommentaire> listTop5NewMessage(long idUser) {
		List<NotificationCommentaire> allNotif = new ArrayList<NotificationCommentaire>() ;
		try {
			allNotif = notificationCommentaireDao.findTop5ByIdUserOrderByIdNotificationCommentaireDesc(idUser);
		} catch (Exception e) {
			
		}
		
		return allNotif;
	}
	
	public int nbrNewMessage (long idUser) {
		int i = 0;
		List<NotificationCommentaire> allNotif = new ArrayList<NotificationCommentaire>() ;
		try {
			allNotif = notificationCommentaireDao.findAllByIdUserAndVue(idUser, false);
			
		} catch (Exception e) {
			
		}
		i = allNotif.size();
	
		return i;
	}
	
	@Transactional
	public boolean vueAllMessage (long idDemande) {
		try {
			notificationCommentaireDao.updateToVue(idDemande);
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
}
