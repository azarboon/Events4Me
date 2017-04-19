package com.metropolia.events4me.DAO;

import com.metropolia.events4me.Model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Dmitry on 11.04.2017.
 */

public interface UserDAO extends CrudRepository<User, Integer> {
//?? where are following two methods declared? And where are they used? Samem etohds are in UserSerivce too,
// so why have u repeated them here?
    User findByEmail(String email);

    User findByUsername(String username);

    List<User> findAll();

    // Martin declared this method
    @Override
    void delete(Integer integer);
}
