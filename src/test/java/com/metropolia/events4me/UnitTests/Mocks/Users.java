package com.metropolia.events4me.UnitTests.Mocks;

import com.metropolia.events4me.Model.User;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mahdiaza on 21/04/17.
 */
public class Users {

  private static int userID = 0;
  private Map<String, User> users;

  private static Users ourInstance = new Users();

  public static Users getInstance() {
    return ourInstance;
  }

  private Users() {
    users = new HashMap<>();
  }

  public  void makeUser(String f, String u, String p){

    User darlington = new User();
    darlington.setUsername(u);
    darlington.setFirstName(f);
    darlington.setPassword(p);
    //userService.saveOrUpdateUser(darlington);
    Users.ourInstance.users.put(darlington.getUsername(),darlington);
  }


  public User getUserbyusername(String un){
    return Users.getInstance().users.get(un);
  }


  public  Map<String, User> getUsers(){
    return getInstance().users;
  }

  public User findByUserName(String username){
    User user = null;
    for(String each: this.users.keySet()){
       user =  this.users.get(each);
    }
    return user;
  }

}
