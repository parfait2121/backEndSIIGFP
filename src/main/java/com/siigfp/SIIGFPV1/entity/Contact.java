package com.siigfp.SIIGFPV1.entity;

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
public class Contact {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idContact;
	private String nom;
	private String contact;
	private String objet;
	private String message;
	@Basic
	private Timestamp dateEnvoye = new Timestamp(System.currentTimeMillis());
}
