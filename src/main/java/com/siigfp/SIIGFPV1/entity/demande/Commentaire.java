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
public class Commentaire {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCommentaire;
	private Long idDemande;
	private Long idUsers;
	private Long idAssistance;
	@Basic
	private Timestamp dateNotification = new Timestamp(System.currentTimeMillis());	
	private String contenue;
	private boolean sender;
	private boolean file;

}
