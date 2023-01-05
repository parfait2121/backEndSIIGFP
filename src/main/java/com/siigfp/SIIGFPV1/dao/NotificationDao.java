package com.siigfp.SIIGFPV1.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.siigfp.SIIGFPV1.entity.demande.Notification;


@Repository
public interface NotificationDao extends CrudRepository<Notification, Long> {

	
	@Query("select n from Notification n where n.idUsers=:param1  order by n.idNotification desc")
	List<Notification> findHeader (@Param("param1")long idUsers);
	
	List<Notification> findTop3ByIdUsersOrderByIdNotificationDesc(long idUsers);
	
	List <Notification> findAllByIdUsersAndVue(long idUsers , boolean vue);
	
	public Page<Notification> findAllByIdUsersOrderByIdNotificationDesc (long idUsers , Pageable p);
}
