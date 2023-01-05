package com.siigfp.SIIGFPV1.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.siigfp.SIIGFPV1.dao.UserDao;
import com.siigfp.SIIGFPV1.entity.EmailRequest;
import com.siigfp.SIIGFPV1.entity.ErrorResponse;
import com.siigfp.SIIGFPV1.entity.Users;
import com.siigfp.SIIGFPV1.service.UserService;

import java.util.List;;

@RestController
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserDao userDao;
	
	
	@PostConstruct 
	public void initRolesAndUsers () {
		userService.initRolesAndUser();
	}
	
	@PostMapping({"/registrerNewUser"})
	public ResponseEntity<?> registrerNewUser ( @RequestBody Users user) throws IOException {
		Users u = userService.registerNewUser(user); 
		if (u == null) {
			 ErrorResponse errorResponse = new ErrorResponse();
	          errorResponse.setMessage("Email deja utiliser");
	          return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(u, HttpStatus.OK) ;
		}	
	}
	
	@PostMapping({"/UpdateUser"})
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> updateUsers (@RequestBody Users user) throws IOException {
		Users u = userService.updateUsers(user);
		if (u == null) {
			 ErrorResponse errorResponse = new ErrorResponse();
	          errorResponse.setMessage("erreur est survenu lors de votre mise a jour");
	          return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(u, HttpStatus.OK) ;
		}	
	}
	
	@PostMapping("/uploadImage/{code}") 
    public int handleFileUpload(@PathVariable String code , @RequestParam("file") MultipartFile file, HttpSession session) throws IOException {  
		System.out.println(code);
		System.out.println("test");
		Path pathName = Paths.get(System.getProperty("user.home")+"/ecom/products/"+code);
		File f = new File(pathName.toString());
		f.mkdir();
		Files.write(Paths.get(System.getProperty("user.home")+"/ecom/products/"+code+"/"+file.getOriginalFilename()), file.getBytes());
		return 0;         
    }  
	
	
	@PostMapping({"/testMail"})
	public String testEmail (@RequestBody EmailRequest  email) {
		return userService.testEmail(email);
	}
	
	@GetMapping({"/getUserInfo/{userName}"})
	@PreAuthorize("hasAnyRole('Admin' , 'User')")
	public Users getUserInfo (@PathVariable("userName") String  userName) {
		return userService.getUser(userName);
	}
	
	
	@GetMapping({"/forAdmin"})
	@PreAuthorize("hasRole('Admin')")
	public String forAdmin () {
		return "This url is only accessible to admin";
	}
	
	
	
	
	@GetMapping({"/forUser"})
	@PreAuthorize("hasRole('User')")
	public String forUser () {
		return "This url is only accessible to User";
	}
	
	@PostMapping({"/forgotPass/getCode"})
	public String getCode (@RequestBody EmailRequest  email) {
		return userService.getCode(email);
	}
	@PostMapping({"/forgotPass/verifyCode"})
	public String verifyCode (@RequestBody EmailRequest  email) {
		return userService.verifyCode(email);
	}
	@PostMapping({"/forgotPass/changePassword"})
	public String changePassword (@RequestBody EmailRequest  email) {
		return userService.changePassword(email);
	}
	@PostMapping({"/forgotPass/addPerson"})
	public Users addPerson(@RequestBody Users u) {
		return userDao.save(u);
	}
	
	@GetMapping({"/forgotPass/getPerson"})
	public List<Users> getPerson() {
		return userDao.findAll();
	}
	
	@PostMapping({"/forgotPass/activerCompte"})
	public String activerCompte (@RequestBody EmailRequest  email) {
		return userService.activerCompte(email);
	}
	
	
	
}
