package com.metropolia.events4me.Service.ServiceImpl;

import com.metropolia.events4me.DAO.UserDAO;
import com.metropolia.events4me.Model.Event;
import com.metropolia.events4me.Model.Interest;
import com.metropolia.events4me.Model.User;
import com.metropolia.events4me.Service.RoleService;
import com.metropolia.events4me.Service.UserService;
import com.metropolia.events4me.Service.security.EncryptionService;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
  public boolean checkUserExists(String username, String email) {
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
    if (user.getPassword() != null) {
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

  @Override
  public List<User> getUsersWithCommonInterest(String username) {
    return getTopMatches(username, 10);
  }


  private List<User> getTopMatches(String username, int numberOfTOpMatches){
    if(numberOfTOpMatches >= getSortedList(username).size()){
      numberOfTOpMatches = getSortedList(username).size() -1;
    }
    List<UserAndNumberOfCommonInterest> topUsersAnndNumberOfCommonnInterests = getSortedList(username).subList(
        getSortedList(username).size()-numberOfTOpMatches,  getSortedList(username).size());

    Collections.sort(topUsersAnndNumberOfCommonnInterests);
    /*
    System.out.println("list 2 : This is the retrieved sorted:");
    topUsersAnndNumberOfCommonnInterests.forEach(e -> System.out.println(e.getUser().getFirstName()
        + " number of common matches " + e.getCommonInterest()));
        */
    List<User> topMatches = new ArrayList<>();
    for(UserAndNumberOfCommonInterest each: topUsersAnndNumberOfCommonnInterests){
      topMatches.add(each.getUser());
    }
    return topMatches;
  }

  private List<UserAndNumberOfCommonInterest> getSortedList(String username){
    User currentUser = userDAO.findByUsername(username);
    List<User> allUsers = userDAO.findAll();
    List<UserAndNumberOfCommonInterest> usersWithNumberOfCommonInterests = new ArrayList<>();


    for (User each : allUsers) {
      int commonInterests = getNumberOfCommonInterests(currentUser, each);
      usersWithNumberOfCommonInterests.add(new UserAndNumberOfCommonInterest(each, commonInterests));
    }
     Collections.sort(usersWithNumberOfCommonInterests);
    return usersWithNumberOfCommonInterests;
  }

  private int getNumberOfCommonInterests(User user1, User user2){
    int commonInterests = 0;
    Set<Interest> interests1 = user1.getInterests();
    Set<Interest> interests2 = user2.getInterests();

    for(Interest each: interests1){
      if(interests2.contains(each)){
        commonInterests++;
      }
    }
    return commonInterests;
  }

  private class UserAndNumberOfCommonInterest implements Comparable<UserAndNumberOfCommonInterest>{
    private int commonInterest;
    private User user;


    public UserAndNumberOfCommonInterest(User user, int commonInterest){
      this.user = user;
      this.commonInterest = commonInterest;
    }

    public User getUser() {
      return user;
    }

    public void setUser(User user) {
      this.user = user;
    }

    public int getCommonInterest() {
      return commonInterest;
    }

    public void setCommonInterest(int commonInterest) {
      this.commonInterest = commonInterest;
    }


    @Override
    public int compareTo(UserAndNumberOfCommonInterest o) {
      int difference = o.getCommonInterest() -this.commonInterest;
      if(difference > 0){
        return 1;
      } else if(difference == 0){
        return 0;
      }
      return -1;
    }
  }

}
