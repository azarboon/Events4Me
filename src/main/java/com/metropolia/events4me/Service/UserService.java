package com.metropolia.events4me.Service;

import com.metropolia.events4me.Model.Event;
import com.metropolia.events4me.Model.User;

import java.security.Principal;
import java.util.List;
import java.util.Set;

/**
 * Created by Dmitry on 11.04.2017.
 */
public interface UserService {

    User findByEmail(String email);

    User findByUsername(String username);

    User getById(Integer id);

    void delete(Integer id);

    boolean checkUserExists(String username, String email);

    boolean checkUsernameExists(String username);

    boolean checkEmailExists(String email);

    User saveOrUpdateUser(User user);

    List<User> listUsers();

    List<Event> listUserEvents(User user);

}
