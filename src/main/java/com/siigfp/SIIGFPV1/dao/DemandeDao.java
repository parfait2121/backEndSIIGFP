package com.siigfp.SIIGFPV1.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.siigfp.SIIGFPV1.entity.demande.Demande;

@Repository
public interface DemandeDao extends CrudRepository<Demande, Long>{
	public Page<Demande> findAllByIdUsersOrderByIdDemandeDesc (long idUsers , Pageable p);
	public Demande findByIdDemandeAndIdUsers (long idDemande , long idUsers );

}
