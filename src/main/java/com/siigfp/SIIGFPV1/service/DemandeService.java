package com.siigfp.SIIGFPV1.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siigfp.SIIGFPV1.dao.CommentaireDao;
import com.siigfp.SIIGFPV1.dao.DemandeDao;
import com.siigfp.SIIGFPV1.dao.NotificationCommentaireDao;
import com.siigfp.SIIGFPV1.dao.TypeDemandeDao;
import com.siigfp.SIIGFPV1.entity.demande.Commentaire;
import com.siigfp.SIIGFPV1.entity.demande.Demande;
import com.siigfp.SIIGFPV1.entity.demande.NotificationCommentaire;
import com.siigfp.SIIGFPV1.entity.demande.TypeDemande;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

@Service
public class DemandeService {

	@Autowired
	private DemandeDao demandeDao;
	
	@Autowired
	private TypeDemandeDao typeDemandeDao;
	
	@Autowired
	private CommentaireDao commentaireDao;
	
	@Autowired
	private NotificationCommentaireDao notificationCommentaireDao;
	
	
	
	
	public Demande insererDemande (Demande d) {
		Set<TypeDemande> tpS =new HashSet<>();
		TypeDemande tp = typeDemandeDao.findByURL(d.getUrlTypeDemande());
		System.out.println(d.getUrlTypeDemande());
		tpS.add(tp);
		d.setTypeDemande(tpS);
		
		Demande dd = demandeDao.save(d);
		Commentaire c= new Commentaire();
		c.setIdDemande(dd.getIdDemande());
		c.setContenue("Bonjour ! Dites-nous comment nous pouvons vous aider.");
		c.setSender(false);
		c.setFile(false);
		commentaireDao.save(c);
		return dd;
		
	}
	

 

    public Resource download(String filename ,String chemin) {
        try {
            Path file = Paths.get(chemin)
                             .resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
	
    public Commentaire sendCommentaire (Commentaire c) {
    	c.setSender(true);
    	c.setFile(false);
    	return commentaireDao.save(c);
    }
	

    public Commentaire sendReponseCommentaire (Commentaire c) {
    	c.setSender(false);
    	c.setFile(false);
    	NotificationCommentaire ns = new NotificationCommentaire();    	
    	Commentaire cn = new Commentaire();
    	cn = commentaireDao.save(c);
    	ns.setIdDemande(cn.getIdDemande());
     	ns.setContenue( cn.getContenue());
     	ns.setDateNotification(cn.getDateNotification());
     	ns.setIdUser(cn.getIdUsers());
     	notificationCommentaireDao.save(ns);
    	 
    	return cn;
    }
    
}
