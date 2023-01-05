package com.siigfp.SIIGFPV1.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.siigfp.SIIGFPV1.dao.CommentaireDao;
import com.siigfp.SIIGFPV1.dao.DemandeDao;
import com.siigfp.SIIGFPV1.entity.ErrorResponse;
import com.siigfp.SIIGFPV1.entity.demande.Commentaire;
import com.siigfp.SIIGFPV1.entity.demande.Demande;
import com.siigfp.SIIGFPV1.entity.demande.Notification;
import com.siigfp.SIIGFPV1.entity.demande.NotificationCommentaire;
import com.siigfp.SIIGFPV1.service.DemandeService;
import com.siigfp.SIIGFPV1.service.NotificationCommentaireService;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;

import org.springframework.web.bind.annotation.ResponseBody;


@RestController
public class DemandeController {

	@Value("${file-root}")
	private String root;
	
	@Autowired
	private DemandeService demandeService;
	
	@Autowired
	private CommentaireDao commentaireDao;
	
	@Autowired
	private NotificationCommentaireService notificationCommentaireService;
	
	@Autowired
    public DemandeController(DemandeService demandeService) {
        this.demandeService = demandeService;
    }
	
	@Autowired
	private DemandeDao demandeDao;
	@PostMapping({"/insererDemande"})
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> insererDemande ( @RequestBody Demande d) throws IOException {
		Demande u = demandeService.insererDemande(d);
		if (u == null) {
			 ErrorResponse errorResponse = new ErrorResponse();
	          errorResponse.setMessage("Une erreur est survenue lors de l'insertion");
	          return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(u, HttpStatus.OK) ;
		}	
	}
	
	@PostMapping("/uploadImageDemande/{code}") 
	@PreAuthorize("isAuthenticated()")
    public int handleFileUpload(@PathVariable String code , @RequestParam("file") MultipartFile file, HttpSession session) throws IOException {  
		System.out.println(code);
		System.out.println("test");
		Path pathName = Paths.get(root+"Demande/"+code);
		File f = new File(pathName.toString());
		f.mkdir();
		Files.write(Paths.get(root+"Demande/"+code+"/"+file.getOriginalFilename()), file.getBytes());
		return 0;             
    }
	
	@GetMapping("/getDemande/{idUser}/{idDemande}")
	@PreAuthorize("isAuthenticated()")
	public Demande getDemande (@PathVariable("idUser")long idUsers ,@PathVariable("idDemande") long idDemande ) {
		Demande d = demandeDao.findByIdDemandeAndIdUsers(idDemande, idUsers);
		return d;
	}
	
	
	@GetMapping("/getListDemande/{idUser}/{page}")
	@PreAuthorize("isAuthenticated()")
	public Page<Demande> getNotification (@PathVariable("idUser")long idUsers ,@PathVariable("page") int page ) {
		Pageable pageParam = PageRequest.of(page, 5);
		Page<Demande> n = demandeDao.findAllByIdUsersOrderByIdDemandeDesc(idUsers, pageParam);
		return n;
	}
	
	@GetMapping(path="/getPhotoDemande/{photoName}/{code}")
	public byte[] getPhoto ( @PathVariable("photoName") String photoName , @PathVariable("code") String code) throws Exception {
		System.out.println("antso");
		return Files.readAllBytes(Paths.get( root+"Demande/" +code+"/"+ photoName));
	}
	
	@GetMapping(path="/downloadPieceJointe/{filename}/{code}")
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Resource> downloadFile(@PathVariable("filename") String filename ,  @PathVariable("code") String code) throws IOException {
        String chemin =root+"Demande/" +code+"/";
		Resource file = demandeService.download(filename , chemin);
        Path path = file.getFile()
                        .toPath();

        return ResponseEntity.ok()
                             .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(path))
                             .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                             .body(file);
    }
	
	@PostMapping({"/sendCommentaire"})
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> sendCommentaire ( @RequestBody Commentaire c) throws IOException {
		Commentaire u = demandeService.sendCommentaire(c);
		if (u == null) {
			 ErrorResponse errorResponse = new ErrorResponse();
	          errorResponse.setMessage("Une erreur est survenue lors de l'insertion");
	          return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(u, HttpStatus.OK) ;
		}	
	}
	

	
	@PostMapping({"/test/reponseCommentaire"})
	public ResponseEntity<?> reponseCommentaire ( @RequestBody Commentaire c) throws IOException {
		Commentaire u = demandeService.sendReponseCommentaire(c);
		if (u == null) {
			 ErrorResponse errorResponse = new ErrorResponse();
	          errorResponse.setMessage("Une erreur est survenue lors de l'insertion");
	          return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(u, HttpStatus.OK) ;
		}	
	}
	
	
	
	@GetMapping("/getListCommentaireFirst/{idDemande}/{page}")
	@PreAuthorize("isAuthenticated()")
	public Page<Commentaire> getListCommentaireFirst (@PathVariable("idDemande")long idDemande , @PathVariable("page")int page ) {
		Pageable pageParam = PageRequest.of(0, 10);
		Page<Commentaire> n = commentaireDao.findAllByIdDemandeOrderByIdCommentaireDesc(idDemande, pageParam);
		return n;
	}
	@GetMapping("/chargerPlus/{idDemande}/{page}/{limit}")
	@PreAuthorize("isAuthenticated()")
	public Page<Commentaire> chargerPlus (@PathVariable("idDemande")long idDemande , @PathVariable("page")int page , @PathVariable("limit")long limit ) {
		Pageable pageParam = PageRequest.of(page, 10);
		Page<Commentaire> n = commentaireDao.findAllByIdDemandeAndIdCommentaireLessThanEqualOrderByIdCommentaireDesc(idDemande, limit, pageParam);
		return n;
	}
	@GetMapping("/chargerNouveauMessage/{idDemande}/{limit}")
	@PreAuthorize("isAuthenticated()")
	public List<Commentaire> chargerNouveauMessage (@PathVariable("idDemande")long idDemande , @PathVariable("limit")long limit ) {
		List<Commentaire> n = commentaireDao.findAllByIdDemandeAndIdCommentaireGreaterThanOrderByIdCommentaireAsc(idDemande, limit);
		return n;
	}
	
	@PostMapping("/uploadFileComs/{code}/{idUsers}") 
	@PreAuthorize("isAuthenticated()")
    public Commentaire sendComsFile(@PathVariable("code") long code , @PathVariable("idUsers") long idUsers , @RequestParam("file") MultipartFile file, HttpSession session) throws IOException {  
		System.out.println(code);
		Commentaire c = new Commentaire();
		c.setSender(true);
		c.setIdDemande(code);
		c.setIdUsers(idUsers);
		c.setContenue(file.getOriginalFilename());		
		c.setFile(true);
		Path pathName = Paths.get(root+"Demande/"+code+"/commentaire");
		File f = new File(pathName.toString());
		f.mkdir();
		Files.write(Paths.get(root+"Demande/"+code+"/commentaire/"+file.getOriginalFilename()), file.getBytes());
		return commentaireDao.save(c);
    }
	
	@GetMapping(path="/downloadPieceJointeComms/{filename}/{code}")
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Resource> downloadFileComms(@PathVariable("filename") String filename ,  @PathVariable("code") String code) throws IOException {
        String chemin =  root+"Demande/" +code+"/commentaire";
		Resource file = demandeService.download(filename , chemin);
        Path path = file.getFile()
                        .toPath();

        return ResponseEntity.ok()
                             .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(path))
                             .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                             .body(file);
    }
	
	@GetMapping("/getNombreNewMessage/{id}")
	@PreAuthorize("isAuthenticated()")
	public int getNombreNotification (@PathVariable long id) {
		return notificationCommentaireService.nbrNewMessage(id);
		
	}
	
	@GetMapping("/getTop5NewMessage/{id}")
	@PreAuthorize("isAuthenticated()")
	public List<NotificationCommentaire> getNotifHeader (@PathVariable long id) {
		return notificationCommentaireService.listTop5NewMessage(id);
	}
	
	@GetMapping("/updateToVue/{idDemande}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> UpdateVue ( @PathVariable long idDemande ) throws IOException {
		boolean req = notificationCommentaireService.vueAllMessage(idDemande);
		if (req == false) {
			 ErrorResponse errorResponse = new ErrorResponse();
	          errorResponse.setMessage("Une erreur est survenue lors de l'insertion");
	          return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(req, HttpStatus.OK) ;
		}	
	}
	
	
}
