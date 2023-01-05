package com.siigfp.SIIGFPV1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.siigfp.SIIGFPV1.dao.RoleDao;
import com.siigfp.SIIGFPV1.entity.Role;
import com.siigfp.SIIGFPV1.service.RoleService;

@RestController
public class RoleController {
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RoleDao roleDao;
	
	@PostMapping({"/createNewRole"})
	public Role createNewRole(@RequestBody Role role) {
		return roleService.createNewRole(role);
	}
	
	@GetMapping({"/util/getAllRole"})
	public List<Role> getRole() {
		return  (List<Role>) roleDao.findAll();
	}
	
}
