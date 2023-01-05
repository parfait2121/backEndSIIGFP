package com.siigfp.SIIGFPV1.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.siigfp.SIIGFPV1.dao.TypeDemandeDao;
import com.siigfp.SIIGFPV1.entity.demande.TypeDemande;

@RestController
public class TypeDemandeController {
	
	@Autowired
	private TypeDemandeDao typeDemandeDao;

	@GetMapping({"/getAllTypeDemande"})
	@PreAuthorize("isAuthenticated()")
	public List<TypeDemande> getAllTypeDemande () {
		List<TypeDemande> tpd = new ArrayList<TypeDemande>();
		tpd = (List<TypeDemande>) typeDemandeDao.listTypeDemande();
		return tpd;
	}
	
	@GetMapping({"/rechercheTypeDemande/{motClef}"})
	@PreAuthorize("isAuthenticated()")
	public List<TypeDemande> getRechercheDemande (@PathVariable("motClef")String motClef) {
		List<TypeDemande> tpd = new ArrayList<TypeDemande>();
	    motClef = motClef.toLowerCase();
		tpd = (List<TypeDemande>) typeDemandeDao.chercherProduits("%"+motClef+"%");
		return tpd;
	}
}
