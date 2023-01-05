package com.siigfp.SIIGFPV1.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.siigfp.SIIGFPV1.entity.Contact;

@Repository
public interface ContactDao extends CrudRepository<Contact, Long> {

}
