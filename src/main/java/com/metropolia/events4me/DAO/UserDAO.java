package com.metropolia.events4me.DAO;

import com.metropolia.events4me.Model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserDAO extends CrudRepository<User, Integer> {

    //?? where are these implmented? i mean, how hibernate knows how does User findByUsername(String username) work?
    //TODO: delete this if no use
    User findByEmail(String email);

    User findByUsername(String username);

    List<User> findAll();



}
