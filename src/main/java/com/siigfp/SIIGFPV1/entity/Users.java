package com.siigfp.SIIGFPV1.entity;

import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
  
@Entity 
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Users {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	private String userName;
	private String userFirstname;
	private String userLastName;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String userPassword;
	private String adresse;
	private int codeActivation;
	private long phone;
	private int etatCompte = 0;
	private String code;
	private String nomFonction;
	@ManyToMany(fetch= FetchType.EAGER , cascade = CascadeType.MERGE)
	@JoinTable(name = "USER_ROLE" , joinColumns = {@JoinColumn(name = "USER_ID")} , inverseJoinColumns = {@JoinColumn(name = "ROLE_ID")})
	private Set<Role> role;
	private String photoName;
	@Transient
	private String roleJSON;

	
	
}
