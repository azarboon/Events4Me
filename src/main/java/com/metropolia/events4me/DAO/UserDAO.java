package com.metropolia.events4me.DAO;

import com.metropolia.events4me.Model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Dmitry on 11.04.2017.
 */

public interface UserDAO extends CrudRepository<User, Integer> {

    User findByEmail(String email);

    User findByUsername(String username);

    List<User> findAll();

}
