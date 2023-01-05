package com.siigfp.SIIGFPV1.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.siigfp.SIIGFPV1.entity.demande.Commentaire;

@Repository
public interface CommentaireDao extends CrudRepository<Commentaire, Long>{
	
	public Page<Commentaire> findAllByIdDemandeOrderByIdCommentaireDesc (long idDemande , Pageable p);
	public Page<Commentaire> findAllByIdDemandeAndIdCommentaireLessThanEqualOrderByIdCommentaireDesc (long idDemande , long idCommentaire , Pageable p );
	public List<Commentaire> findAllByIdDemandeAndIdCommentaireGreaterThanOrderByIdCommentaireAsc (long idDemande , long idCommentaire);
}
