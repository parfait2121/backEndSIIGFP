package com.siigfp.SIIGFPV1.entity.demande;

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
public class TypeDemande {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idTypeDemande;
	private String nomTypeDemande;
	private String description;
	private String variable;
	private String URL;
}
