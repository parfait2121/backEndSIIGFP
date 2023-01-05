package com.siigfp.SIIGFPV1.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.siigfp.SIIGFPV1.entity.demande.TypeDemande;



@Repository
public interface TypeDemandeDao extends CrudRepository<TypeDemande, Long> {
	@Query("Select u from TypeDemande u order by u.nomTypeDemande asc")
	public List<TypeDemande> listTypeDemande ( );
	TypeDemande findByURL (String URL);
	@Query("select t from TypeDemande t where lower(t.nomTypeDemande) like :x")
	public List<TypeDemande> chercherProduits (@Param("x")String mc );

}	
