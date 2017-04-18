package com.metropolia.events4me;

import static org.junit.Assert.assertEquals;

import com.metropolia.events4me.Model.User;
import com.metropolia.events4me.Service.UserService;
import java.util.List;
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


  @Test
  public void test_getUsersWithCommonInterest(){

    // ask how can u implement the test?

    //UserServiceImpl userServiceImp = new UserServiceImpl();
    User currentUser = userService.findByUsername("dima");
    List<User> retrievedUsers = userService.getUsersWithCommonInterest("dima");

    User highestMatch = retrievedUsers.get(0);
    assertEquals("dmitry", currentUser.getFirstName());


  }

}
