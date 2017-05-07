package com.metropolia.events4me.DAO;

import com.metropolia.events4me.Model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserDAO extends CrudRepository<User, Integer> {


    //TODO: delete this if no use
    User findByEmail(String email);

    User findByUsername(String username);

    List<User> findAll();



}
