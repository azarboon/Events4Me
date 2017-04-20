package com.metropolia.events4me;

import static org.junit.Assert.assertEquals;

import com.metropolia.events4me.DAO.UserDAO;
import com.metropolia.events4me.Model.User;
import com.metropolia.events4me.Service.UserService;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by mahdiaza on 18/04/17.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {


  @Autowired
  private UserService userService;
  @Autowired
  private UserDAO userDAO;



  /**
   * Users will be loaded by {@link com.metropolia.events4me.bootstrap.SpringDataBootstrap}.
   * Dimitry and Niklas has been defined in a way to have the highest match.
   *
   */

  @Test
  public void test_getUsersWithCommonInterest(){
    List<User> retrievedUsers = userService.getUsersWithCommonInterest("dima");
    User highestMatch = retrievedUsers.get(0);
    assertEquals("niklas", highestMatch.getFirstName());
  }

  @Test
  public void test_RecieveFriendRequest(){
    //TODO: create users here and make unit tests independent
    //TODO: enable persistence
      User dima = userDAO.findByUsername("dima");
      User martin = userDAO.findByUsername("martin");
      dima.sendFriendRequestTo(martin);
      Set<User> requestSenders = martin.getPendingFriendRequests();
      System.out.println("List 3: friend ship request recieved from following users:");
      requestSenders.forEach(user -> System.out.println(user.getUsername()));
      martin.acceptFriend(dima);
      userService.saveOrUpdateUser(martin);
      userService.saveOrUpdateUser(dima);
      Set<User> friends = martin.getFriends();
      assertEquals(true , friends.contains(dima));
  }

  @Test
  public void test_findByUsername(){
    User dima = userDAO.findByUsername("dima");
    assertEquals("dmitry", dima.getFirstName());
  }



}
