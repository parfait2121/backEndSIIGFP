package com.siigfp.SIIGFPV1.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.siigfp.SIIGFPV1.entity.Role;

@Repository
public interface RoleDao extends CrudRepository<Role, Long> {

}
