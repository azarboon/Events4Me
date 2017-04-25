package com.metropolia.events4me;

import static org.junit.Assert.assertEquals;

import com.metropolia.events4me.Model.Interest;
import com.metropolia.events4me.Model.User;
import com.metropolia.events4me.Service.UserService;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback
public class UserServiceImplIT {
  private User[] testUsers = new User[4];


  @Autowired
  @Qualifier("UserServiceImpl")
  private UserService userService;


  public UserServiceImplIT(){

    // ?? why my @PreDestroy method is not being called at end of lifecycle?

  }


  @PostConstruct
  private void populateDB(){
    User test5 = new User();
    test5.setUsername("test5");
    test5.setFirstName("firstname5");
    test5.setLastName("lastname5");
    test5.setEmail("test5@email.com");
    test5.setPassword("admin");
    test5.getInterests().add(Interest.BUSINESS);
    test5.getInterests().add(Interest.SPORT);
    test5.getInterests().add(Interest.DANCE);
    userService.saveOrUpdateUser(test5);
    this.testUsers[0] = test5;

    User test6 = new User();
    test6.setUsername("test6");
    test6.setFirstName("firstname6");
    test6.setLastName("lastname6");
    test6.setPassword("user");
    test6.setEmail("test6@email.com");
    test6.getInterests().add(Interest.PARTY);
    test6.getInterests().add(Interest.ART);
    userService.saveOrUpdateUser(test6);
    this.testUsers[1] = test6;

    User test7 = new User();
    test7.setUsername("test7");
    test7.setFirstName("firstname7");
    test7.setLastName("lastname7");
    test7.setPassword("user");
    test7.setEmail("test7@email.com");
    test7.getInterests().add(Interest.BUSINESS);
    test7.getInterests().add(Interest.SPORT);
    test7.getInterests().add(Interest.DANCE);
    userService.saveOrUpdateUser(test7);
    this.testUsers[2] = test7;

    User test8 = new User();
    test8.setUsername("test8");
    test8.setFirstName("firstname8");
    test8.setLastName("lastname8");
    test8.setPassword("user");
    test8.setEmail("test8@email.com");
    test8.getInterests().add(Interest.BUSINESS);
    test8.getInterests().add(Interest.NATURE);
    userService.saveOrUpdateUser(test8);
    this.testUsers[3] = test8;
  }


  @PreDestroy
  public void cleanUpDB(){
    System.out.println("cleanUpDB method was called");
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

  @Test
  public void test_RecieveFriendRequest(){

    User test6 = userService.findByUsername("test6");
    User test7 = userService.findByUsername("test7");
    test6.sendFriendRequestTo(test7);
    Set<User> requestSenders = test7.getPendingFriendRequests();
    test7.acceptFriend(test6);
    userService.saveOrUpdateUser(test7);
    userService.saveOrUpdateUser(test6);
    Set<User> friends = test7.getFriends();
    assertEquals(true , friends.contains(test6));
  }

}
