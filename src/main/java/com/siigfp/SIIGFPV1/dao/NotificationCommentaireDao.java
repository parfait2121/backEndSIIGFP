package com.siigfp.SIIGFPV1.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.siigfp.SIIGFPV1.entity.demande.NotificationCommentaire;

@Repository
public interface NotificationCommentaireDao  extends CrudRepository<NotificationCommentaire, Long>  {
	List<NotificationCommentaire> findTop5ByIdUserOrderByIdNotificationCommentaireDesc(long idUser);
	List<NotificationCommentaire> findAllByIdUserAndVue(long idUser , boolean vue);
	@Modifying
	@Query("update NotificationCommentaire u set u.vue=true where u.idDemande=:i")
	public void updateToVue ( @Param("i")long mc );


}
