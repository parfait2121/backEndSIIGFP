package com.siigfp.SIIGFPV1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siigfp.SIIGFPV1.dao.RoleDao;
import com.siigfp.SIIGFPV1.entity.Role;

@Service
public class RoleService {
	@Autowired
	private RoleDao roleDao;
	public Role createNewRole( Role role ) {
		return roleDao.save(role);
	}
}
