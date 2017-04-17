package com.metropolia.events4me.DAO;

import com.metropolia.events4me.Model.security.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Dmitry on 13.04.2017.
 */
public interface RoleDAO extends CrudRepository<Role, Integer> {

    List<Role> findAll();
}
