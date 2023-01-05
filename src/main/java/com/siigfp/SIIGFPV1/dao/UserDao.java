package com.siigfp.SIIGFPV1.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.siigfp.SIIGFPV1.entity.Users;

@Repository
public interface UserDao extends JpaRepository<Users, Long>{
    
	List<Users> findAllByUserName (String userName);
	Users findByUserName (String userName);
	Users findByUserNameAndCodeActivation (String userName , int codeActivation);
}
