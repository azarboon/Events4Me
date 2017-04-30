package com.metropolia.events4me;

import static org.junit.Assert.assertEquals;

import com.metropolia.events4me.Model.Interest;
import com.metropolia.events4me.Model.User;
import com.metropolia.events4me.Service.UserService;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplIT {

  @Autowired
  @Qualifier("UserServiceImpl")
  private UserService userService;


  public UserServiceImplIT() {

  }

  // ?? why my @PreDestroy method is not being called at end of lifecycle?
  @PostConstruct
  private void populateDB() {
    /*
    User test5 = new User();
    test5.setUsername("test5");
    test5.setFirstName("firstname5");
    test5.setLastName("lastname5");
    test5.setEmail("test5@email.com");
    test5.setPassword("admin");
    test5.getInterests().add(Interest.BUSINESS);
    test5.getInterests().add(Interest.SPORT);
    test5.getInterests().add(Interest.DANCE);
    //TODO: find a way to remove such if condition. improve saveorupdate metohd
    userService.saveOrUpdateUser(test5);
    if(!userService.checkUsernameExists("test5")){
      userService.saveOrUpdateUser(test5);
    }


    User test6 = new User();
    test6.setUsername("test6");
    test6.setFirstName("firstname6");
    test6.setLastName("lastname6");
    test6.setPassword("user");
    test6.setEmail("test6@email.com");
    test6.getInterests().add(Interest.PARTY);
    test6.getInterests().add(Interest.ART);

    if(!userService.checkUsernameExists("test6")){
      userService.saveOrUpdateUser(test6);
    }


    User test7 = new User();
    test7.setUsername("test7");
    test7.setFirstName("firstname7");
    test7.setLastName("lastname7");
    test7.setPassword("user");
    test7.setEmail("test7@email.com");
    test7.getInterests().add(Interest.BUSINESS);
    test7.getInterests().add(Interest.SPORT);
    test7.getInterests().add(Interest.DANCE);
    if(!userService.checkUsernameExists("test7")){
      userService.saveOrUpdateUser(test7);
    }

    User test8 = new User();
    test8.setUsername("test8");
    test8.setFirstName("firstname8");
    test8.setLastName("lastname8");
    test8.setPassword("user");
    test8.setEmail("test8@email.com");
    test8.getInterests().add(Interest.BUSINESS);
    test8.getInterests().add(Interest.NATURE);
    if(!userService.checkUsernameExists("test8")){
      userService.saveOrUpdateUser(test8);
    }
    */
  }

  @Test
  public void test_getUsersWithCommonInterest() {
    List<User> retrievedUsers = userService.getUsersWithCommonInterest("test5");
    User highestMatch = retrievedUsers.get(0);
    assertEquals("firstname7", highestMatch.getFirstName());
  }


  @Test
  public void test_RecieveFriendRequest() {
    User test6 = userService.findByUsername("test6");
    User test7 = userService.findByUsername("test7");
    test6.sendFriendRequestTo(test7);
    test7.acceptFriend(test6);
    userService.saveOrUpdateUser(test7);
    userService.saveOrUpdateUser(test6);
    User retrievedUserFromDB = userService.findByUsername("test7");
    Set<User> friends = retrievedUserFromDB.getFriends();
    //TODO: change following method to "contain"
    assertEquals("test6", friends.iterator().next().getUsername());

  }

}
