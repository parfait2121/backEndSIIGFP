package com.siigfp.SIIGFPV1.entity.demande;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Notification {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idNotification;
	private String objet;
	private String contenue;
	private long idUsers;
	private int idDemande;
	private boolean vue = false;
	@Basic
	private Timestamp dateNotification = new Timestamp(System.currentTimeMillis());	
}
