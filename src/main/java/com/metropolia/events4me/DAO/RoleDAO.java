package com.metropolia.events4me.DAO;

import com.metropolia.events4me.Model.security.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface RoleDAO extends CrudRepository<Role, Integer> {

    List<Role> findAll();
}
