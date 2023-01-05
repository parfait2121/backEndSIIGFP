package com.siigfp.SIIGFPV1.service;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.siigfp.SIIGFPV1.dao.NotificationDao;
import com.siigfp.SIIGFPV1.dao.RoleDao;
import com.siigfp.SIIGFPV1.dao.TypeDemandeDao;
import com.siigfp.SIIGFPV1.dao.UserDao;
import com.siigfp.SIIGFPV1.entity.EmailDetails;
import com.siigfp.SIIGFPV1.entity.EmailRequest;
import com.siigfp.SIIGFPV1.entity.Role;
import com.siigfp.SIIGFPV1.entity.Users;
import com.siigfp.SIIGFPV1.entity.demande.Notification;
import com.siigfp.SIIGFPV1.entity.demande.TypeDemande;



@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private TypeDemandeDao typeDemandeDao;
	
	@Autowired
	private NotificationDao notificationDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailService emailService;

	
	
	@JsonProperty("return")
	private String returnValue;
	
	public Users registerNewUser (Users user) {
		
		
		Users testEmail = userDao.findByUserName(user.getUserName());
		if (testEmail == null) {
		String[] roleString = user.getRoleJSON().split(": ");
		long idRole = (long)Integer.valueOf(roleString[roleString.length - 1]);
		Role role = roleDao.findById(idRole).get();
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		user.setRole(roles);
		user.setUserPassword(getEncodedPassword(user.getUserPassword())); 
		 int min = 100000;
		 int max = 999999;
	     int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
	     user.setCodeActivation(random_int);
		return userDao.save(user);
		}else {
			return null;
		}
	}
	
	public Users updateUsers ( Users newUser) {
		return userDao.save(newUser);
	}
	
	@Transactional(rollbackOn = {Exception.class})
	public void initRolesAndUser () {
		Role adminRole = new Role ();
		adminRole.setRoleName("Admin");
		roleDao.save(adminRole);
		
		Role userRole = new Role ();
		userRole.setRoleName("User");
		roleDao.save(userRole);
		
		Role regisseur = new Role ();
		regisseur.setRoleName("ETAT");
		regisseur.setDesabreviation("fonctionnaire d'Etat");
		roleDao.save(regisseur);
		
		Role gestionnaire = new Role ();
		gestionnaire.setRoleName("CIVILE");
		gestionnaire.setDesabreviation("Personne civile");
		roleDao.save(gestionnaire);
		
		Role arme = new Role ();
		arme.setRoleName("ARME");
		arme.setDesabreviation("arme malagasy");
		roleDao.save(arme);
		
		
		
		
		
		
		Users adminUser = new Users();
		adminUser.setUserFirstname("admin");
		adminUser.setUserLastName("admin");
		adminUser.setUserName("admin123");
		adminUser.setUserPassword(getEncodedPassword("123"));
		adminUser.setEtatCompte(1);
		Set<Role> adminRoles = new HashSet<>();
		adminRoles.add(adminRole);
		adminUser.setRole(adminRoles);
		

		userDao.save(adminUser);
		

		Users user = new Users();
		user.setUserFirstname("parfait");
		user.setUserLastName("rb");
		user.setUserName("user123");
		user.setUserPassword(getEncodedPassword("123"));
		user.setEtatCompte(1);
		Set<Role> userRoles = new HashSet<>();
		userRoles.add(userRole);
		user.setRole(userRoles);
		userDao.save(user);
		
		TypeDemande t4 = new TypeDemande();
		t4.setNomTypeDemande("Demande Specifique");
		t4.setURL("demandeSpecifique");
		t4.setVariable("nom|prenom|localisation|adresse|objet");
		t4.setDescription("Lorem ipsum dolor, sit amet consectetur adipisicing elit. Eos officiis eaque quia adipisci voluptatibus temporibus est amet ab, fuga hic. Iure quasi neque velit aut error voluptatum. Exercitationem, asperiores tempora");
		typeDemandeDao.save(t4);
		
		
		TypeDemande t1 = new TypeDemande();
		t1.setNomTypeDemande("Demande Login");
		t1.setURL("demandeLogin");
		t1.setVariable("IM|nom|prenom|adresse");
		t1.setDescription("Lorem ipsum dolor, sit amet consectetur adipisicing elit. Eos officiis eaque quia adipisci voluptatibus temporibus est amet ab, fuga hic. Iure quasi neque velit aut error voluptatum. Exercitationem, asperiores tempora");
		typeDemandeDao.save(t1);
		
		TypeDemande t2 = new TypeDemande();
		t2.setNomTypeDemande("Insertion Tiers");
		t2.setURL("insertionTiers");
		t2.setDescription("Lorem ipsum dolor, sit amet consectetur adipisicing elit. Eos officiis eaque quia adipisci voluptatibus temporibus est amet ab, fuga hic. Iure quasi neque velit aut error voluptatum. Exercitationem, asperiores tempora");
		t2.setVariable("reference|nom|prenom|code|localisation|adresse");
		typeDemandeDao.save(t2);
		
		TypeDemande t3 = new TypeDemande();
		t3.setNomTypeDemande("Insertion RIB");
		t3.setURL("insertionRIB");
		t3.setVariable("reference|rib|code|nom|prenom|adresse");
		t3.setDescription("Lorem ipsum dolor, sit amet consectetur adipisicing elit. Eos officiis eaque quia adipisci voluptatibus temporibus est amet ab, fuga hic. Iure quasi neque velit aut error voluptatum. Exercitationem, asperiores tempora");
		typeDemandeDao.save(t3);
		
		
		TypeDemande t5 = new TypeDemande();
		t5.setNomTypeDemande("Export de bordereau de mandat (BMAND)");
		t5.setURL("exportBmand");
		t5.setVariable("NBmand|nom|prenom|adresse");
		t5.setDescription("Lorem ipsum dolor, sit amet consectetur adipisicing elit. Eos officiis eaque quia adipisci voluptatibus temporibus est amet ab, fuga hic. Iure quasi neque velit aut error voluptatum. Exercitationem, asperiores tempora");
		typeDemandeDao.save(t5);
		
		TypeDemande t6 = new TypeDemande();
		t6.setNomTypeDemande("Export Tiers vers SIIGMP ");
		t6.setURL("exportTiersSiigmp");
		t6.setVariable("NIF|STAT|nom|prenom|adresse");
		t6.setDescription("Lorem ipsum dolor, sit amet consectetur adipisicing elit. Eos officiis eaque quia adipisci voluptatibus temporibus est amet ab, fuga hic. Iure quasi neque velit aut error voluptatum. Exercitationem, asperiores tempora");
		typeDemandeDao.save(t6);
		
		TypeDemande t7 = new TypeDemande();
		t7.setNomTypeDemande("Export de bordereau d'ordre de payement (BOP)");
		t7.setURL("exportBop");
		t7.setVariable("NBop|nom|prenom|adresse");
		t7.setDescription("Lorem ipsum dolor, sit amet consectetur adipisicing elit. Eos officiis eaque quia adipisci voluptatibus temporibus est amet ab, fuga hic. Iure quasi neque velit aut error voluptatum. Exercitationem, asperiores tempora");
		typeDemandeDao.save(t7);
		
		TypeDemande t8 = new TypeDemande();
		t8.setNomTypeDemande("Mandat");
		t8.setURL("mandat");
		t8.setVariable("reference|objet|nom|prenom|adresse");
		t8.setDescription("Lorem ipsum dolor, sit amet consectetur adipisicing elit. Eos officiis eaque quia adipisci voluptatibus temporibus est amet ab, fuga hic. Iure quasi neque velit aut error voluptatum. Exercitationem, asperiores tempora");
		typeDemandeDao.save(t8);
		
		TypeDemande t9 = new TypeDemande();
		t9.setNomTypeDemande("Import Marche");
		t9.setURL("importMarche");
		t9.setVariable("RefMarche|nom|prenom|adresse");
		t9.setDescription("Lorem ipsum dolor, sit amet consectetur adipisicing elit. Eos officiis eaque quia adipisci voluptatibus temporibus est amet ab, fuga hic. Iure quasi neque velit aut error voluptatum. Exercitationem, asperiores tempora");
		typeDemandeDao.save(t9);
		
		TypeDemande t10 = new TypeDemande();
		t10.setNomTypeDemande("Import budget EPN/CTD");
		t10.setURL("importBudgetEpnCtd");
		t10.setVariable("reference|exercice|code|nom|prenom|adresse");
		t10.setDescription("Lorem ipsum dolor, sit amet consectetur adipisicing elit. Eos officiis eaque quia adipisci voluptatibus temporibus est amet ab, fuga hic. Iure quasi neque velit aut error voluptatum. Exercitationem, asperiores tempora");
		typeDemandeDao.save(t10);
		
		
	}
	
	public String  getEncodedPassword (String password) {
		return passwordEncoder.encode(password);
	}
	
	public String testEmail (EmailRequest email) {
		
		Users emailReturn = userDao.findByUserName(email.getEmail());
		
		if (emailReturn == null) {
			emailReturn = new Users();
			emailReturn.setUserName("okey");
		}
		
		return emailReturn.getUserName();
	}
	
	public Users getUser (String userName) {
		return userDao.findByUserName(userName);
	}
	
	public String getCode (EmailRequest email) {
		String notif = "";
		Users testEmail = userDao.findByUserName(email.getEmail());
		if (testEmail != null) {
			EmailDetails sendEmail = new EmailDetails();
			sendEmail.setRecipient(email.getEmail());
			sendEmail.setSubject("Code pour votre compte SIGFP");
			int min = 100000;
			int max = 999999;
		    int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
		    testEmail.setCodeActivation(random_int);
			sendEmail.setMsgBody("Voici le code :" + random_int);
			userDao.save(testEmail);
			emailService.sendSimpleMail(sendEmail);
			notif = "Veuillez verifier votre email pour prendre le code";
		}else {
			notif = "E-mail inexistant";
		}
		return notif;
	}
	
	public String verifyCode (EmailRequest email) {
		String notif = "";
		Users verificationUser = userDao.findByUserNameAndCodeActivation(email.getEmail(), email.getCode());
		if (verificationUser != null) {
			notif ="okey";
		}else {
			notif = "verifier votre code";
		}
		return notif;
	}
	
	public String changePassword (EmailRequest email) {
		String notif = "";
		Users verificationUser = userDao.findByUserNameAndCodeActivation(email.getEmail(), email.getCode());
		if (verificationUser != null) {
			verificationUser.setUserPassword(getEncodedPassword(email.getPassword()));
			notif ="votre mot de passe est changer avec succes";
			userDao.save(verificationUser);
		}else {
			notif = "une erreur est survenu lors du changement de mot de passe";
		}
		return notif;
	}
	
	public String activerCompte (EmailRequest email) {
		String notif = "";
		Users verificationUser = userDao.findByUserNameAndCodeActivation(email.getEmail(), email.getCode());
		if (verificationUser != null) {
			Notification n = new Notification();
			n.setIdUsers(verificationUser.getUserId());
			n.setObjet("Bienvenue dans notre site web SIGFP");
			n.setContenue("vous etes la bienvenue dans notre site web portail SIGFP , vous pouvez des maintenant commencez a faire votre premiere demande");
			notificationDao.save(n);
			
			Notification n1 = new Notification();
			n1.setIdUsers(verificationUser.getUserId());
			n1.setObjet("Activation du compte");
			n1.setContenue("L'activation de votre compte a ete reussie avec succes");
			notificationDao.save(n1);
			
			
			verificationUser.setEtatCompte(1);
			notif ="votre compte est activer";
			userDao.save(verificationUser);
		}else {
			notif = "une erreur est survenue";
		}
		return notif;
	}
	
}
