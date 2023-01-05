package com.siigfp.SIIGFPV1.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.siigfp.SIIGFPV1.dao.NotificationDao;
import com.siigfp.SIIGFPV1.entity.demande.Notification;
import com.siigfp.SIIGFPV1.service.NotificationService;

@RestController
public class NotificationController {
	@Autowired
	private NotificationDao notificationDao;
	
	@Autowired
	private NotificationService notificationService;
	
	@PostMapping("/insertNotification")
	public Notification insertNotification (@RequestBody Notification notification) {
		 return notificationDao.save(notification);
	}
	
	@GetMapping("/getNotifHeader/{id}")
	@PreAuthorize("isAuthenticated()")
	public List<Notification> getNotifHeader (@PathVariable long id) {
		return notificationService.getNotifHeader(id);
	}
	
	@GetMapping("/getNombreNotification/{id}")
	@PreAuthorize("isAuthenticated()")
	public int getNombreNotification (@PathVariable long id) {
		return notificationService.getNumberNewNotification(id);
		
	}
	
	@GetMapping("/setVue/{id}")
	@PreAuthorize("isAuthenticated()")
	public Optional<Notification> setVue (@PathVariable long id) {
		return notificationService.setVue(id);
	}
	
	@GetMapping("/getNotification/{idUser}/{page}/{size}")
	@PreAuthorize("isAuthenticated()")
	public Page<Notification> getNotification (@PathVariable("idUser")long idUsers ,@PathVariable("page") int page ,@PathVariable("size") int size) {
		Pageable pageParam = PageRequest.of(page, size);
		Page<Notification> n = notificationDao.findAllByIdUsersOrderByIdNotificationDesc(idUsers, pageParam);
		return n;
	}
	
	
	
}
