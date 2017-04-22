package com.metropolia.events4me.IntegrationTests;

import static org.junit.Assert.assertEquals;

import com.metropolia.events4me.Model.Interest;
import com.metropolia.events4me.Model.User;
import com.metropolia.events4me.Service.UserService;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by mahdiaza on 22/04/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Test_UserServiceImpl {
  private User[] testUsers = new User[4];

  @Autowired
  @Qualifier("UserServiceImpl")
  private UserService userService;

  public Test_UserServiceImpl(){

  }


  @PostConstruct
  private void populateDB(){
    User test5 = new User();
    test5.setUsername("test5");
    test5.setFirstName("firstname5");
    test5.setPassword("admin");
    test5.getInterests().add(Interest.BUSINESS);
    test5.getInterests().add(Interest.SPORT);
    test5.getInterests().add(Interest.DANCE);
    userService.saveOrUpdateUser(test5);
    this.testUsers[0] = test5;

    User test6 = new User();
    test6.setUsername("test6");
    test6.setFirstName("firstname6");
    test6.setPassword("user");
    test6.getInterests().add(Interest.PARTY);
    test6.getInterests().add(Interest.ART);
    userService.saveOrUpdateUser(test6);
    this.testUsers[1] = test6;

    User test7 = new User();
    test7.setUsername("test7");
    test7.setFirstName("firstname7");
    test7.setPassword("user");
    test7.getInterests().add(Interest.BUSINESS);
    test7.getInterests().add(Interest.SPORT);
    test7.getInterests().add(Interest.DANCE);
    userService.saveOrUpdateUser(test7);
    this.testUsers[2] = test7;

    User test8 = new User();
    test8.setUsername("test8");
    test8.setFirstName("firstname8");
    test8.setPassword("user");
    test8.getInterests().add(Interest.BUSINESS);
    test8.getInterests().add(Interest.NATURE);
    userService.saveOrUpdateUser(test8);
    this.testUsers[3] = test8;
  }


  @PreDestroy
  public void cleanUpDB(){
    for(User each: this.testUsers){
      userService.delete(each);
    }
  }
  @Test
  public void test_getUsersWithCommonInterest(){

    List<User> retrievedUsers = userService.getUsersWithCommonInterest("test5");
    User highestMatch = retrievedUsers.get(0);
    assertEquals("firstname7", highestMatch.getFirstName());
  }
/*
  @Test
  public void test_RecieveFriendRequest(){

    //TODO: enable persistence

    Test_User dmitry = new Test_User();
    dmitry.setUsername("dima");
    dmitry.setFirstName("dmitry");
    dmitry.setPassword("admin");
    dmitry.getInterests().add(Interest.BUSINESS);
    dmitry.getInterests().add(Interest.SPORT);
    dmitry.getInterests().add(Interest.DANCE);
    testUserSerivce.saveOrUpdateUser(dmitry);

    Test_User a = new Test_User();
    a.setUsername("martin");
    a.setFirstName("martin");
    a.setPassword("user");
    a.getInterests().add(Interest.PARTY);
    a.getInterests().add(Interest.ART);
    testUserSerivce.saveOrUpdateUser(a);


      Test_User dima = userDAO.findByUsername("dima");
      Test_User martin = userDAO.findByUsername("martin");
      dima.sendFriendRequestTo(martin);
      Set<Test_User> requestSenders = martin.getPendingFriendRequests();
      System.out.println("List 3: friend ship request recieved from following users:");
      requestSenders.forEach(user -> System.out.println(user.getUsername()));
      martin.acceptFriend(dima);
      testUserSerivce.saveOrUpdateUser(martin);
      testUserSerivce.saveOrUpdateUser(dima);
      Set<Test_User> friends = martin.getFriends();
      assertEquals(true , friends.contains(dima));
  }
*/
}
