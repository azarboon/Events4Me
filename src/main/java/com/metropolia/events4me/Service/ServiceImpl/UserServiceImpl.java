package com.metropolia.events4me.Service.ServiceImpl;

import com.metropolia.events4me.DAO.UserDAO;
import com.metropolia.events4me.Model.Event;
import com.metropolia.events4me.Model.User;
import com.metropolia.events4me.Service.RoleService;
import com.metropolia.events4me.Service.UserService;
import com.metropolia.events4me.Service.security.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

/**
 * Created by Dmitry on 11.04.2017.
 */

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;
    private EncryptionService encryptionService;
    private RoleService roleService;

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Autowired
    public void setEncryptionService(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public User findByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    @Override
    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    public boolean checkUserExists(String username, String email){
        return checkUsernameExists(username) || checkEmailExists(username);
    }

    @Override
    public boolean checkUsernameExists(String username) {
        return null != findByUsername(username);
    }

    @Override
    public boolean checkEmailExists(String email) {
        return null != findByEmail(email);
    }

    @Override
    public User saveOrUpdateUser(User user) {
        if(user.getPassword() != null){
            user.setEncryptedPassword(encryptionService.encryptString(user.getPassword()));
        }
        if (user.getUserId() == null) {
            roleService.listRoles().forEach(role -> {
                if (role.getRole().equals("USER")) {
                    user.addRole(role);
                }
            });
        }
        return userDAO.save(user);
    }

    @Override
    public List<User> findUserList() {
        return userDAO.findAll();
    }

    @Override
    public List<Event> listUserEvents(Principal principal) {
        User user = userDAO.findByUsername(principal.getName());
        return user.getEvents();
    }

    @Override
    public void joinEvent(Principal principal, Event event) {
        User user = userDAO.findByUsername(principal.getName());
        user.getEvents().add(event);
        userDAO.save(user);
    }


}
