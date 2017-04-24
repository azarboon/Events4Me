package com.metropolia.events4me.UnitTests.Model;

import static org.junit.Assert.assertEquals;

import com.metropolia.events4me.Model.Interest;
import com.metropolia.events4me.Service.UserService;
import com.metropolia.events4me.UnitTests.Mocks.Users;
import java.util.Map;
import java.util.Set;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by mahdiaza on 18/04/17.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test_User {

  private Map<String, com.metropolia.events4me.Model.User> users;
  public Test_User(){

    this.users = Users.getInstance().getUsers();
    populateUsers();
  }


  @Autowired
  @Qualifier("UserServiceImpl")
  private UserService userService;

  public void populateUsers(){

    com.metropolia.events4me.Model.User testUser1 = new com.metropolia.events4me.Model.User();
    testUser1.setUsername("dima");
    testUser1.setFirstName("dmitry");
    testUser1.setPassword("admin");
    testUser1.getInterests().add(Interest.BUSINESS);
    testUser1.getInterests().add(Interest.SPORT);
    testUser1.getInterests().add(Interest.DANCE);
    this.users.put(testUser1.getUsername(),testUser1);

    com.metropolia.events4me.Model.User testUser2 = new com.metropolia.events4me.Model.User();
    testUser2.setUsername("martin");
    testUser2.setFirstName("martin");
    testUser2.setPassword("user");
    testUser2.getInterests().add(Interest.PARTY);
    testUser2.getInterests().add(Interest.ART);
    this.users.put(testUser2.getUsername(),testUser2);

    com.metropolia.events4me.Model.User testUser3 = new com.metropolia.events4me.Model.User();
    testUser3.setUsername("nilas");
    testUser3.setFirstName("niklas");
    testUser3.setPassword("user");
    testUser3.getInterests().add(Interest.BUSINESS);
    testUser3.getInterests().add(Interest.SPORT);
    testUser3.getInterests().add(Interest.DANCE);
    this.users.put(testUser3.getUsername(),testUser3);

    com.metropolia.events4me.Model.User testUser4 = new com.metropolia.events4me.Model.User();
    testUser4.setUsername("user4");
    testUser4.setFirstName("firstname4");
    testUser4.setPassword("user");
    testUser4.getInterests().add(Interest.BUSINESS);
    testUser4.getInterests().add(Interest.NATURE);
    this.users.put(testUser3.getUsername(),testUser3);

  }
@Test
  public void test_RecieveFriendRequest(){
  com.metropolia.events4me.Model.User dima = Users.getInstance().getUserbyusername("dima");
  com.metropolia.events4me.Model.User martin = Users.getInstance().getUserbyusername("martin");
  dima.sendFriendRequestTo(martin);
  Set<com.metropolia.events4me.Model.User> requestSenders = martin.getPendingFriendRequests();
  System.out.println("List 3: friend ship request recieved from following users:");
  requestSenders.forEach(user -> System.out.println(user.getUsername()));
  martin.acceptFriend(dima);

  Set<com.metropolia.events4me.Model.User> friends = martin.getFriends();
  assertEquals(true , friends.contains(dima));

}

}
