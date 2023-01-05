package com.siigfp.SIIGFPV1.entity.demande;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity 
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Demande {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idDemande;
	@ManyToMany(fetch= FetchType.EAGER , cascade = CascadeType.MERGE)
	@JoinTable(name = "DEMANDE_TYPEDEMANDE" , joinColumns = {@JoinColumn(name = "DEMANDE_ID")} , inverseJoinColumns = {@JoinColumn(name = "TYPEDEMANDE_ID")})
	private Set<TypeDemande> typeDemande;
	private String remarque;
	private long idUsers;
	private int status = 0;
	private String image;
	private String donner;
	@Transient
	private String urlTypeDemande;
	private String ticket;
	@Basic
	private Timestamp dateDeDemande = new Timestamp(System.currentTimeMillis());
}
