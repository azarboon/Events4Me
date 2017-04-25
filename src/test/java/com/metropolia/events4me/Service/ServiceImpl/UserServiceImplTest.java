package com.metropolia.events4me.Service.ServiceImpl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import com.metropolia.events4me.DAO.UserDAO;
import com.metropolia.events4me.Model.Interest;
import com.metropolia.events4me.Model.User;
import com.metropolia.events4me.Service.RoleService;
import com.metropolia.events4me.Service.UserService;
import com.metropolia.events4me.Service.security.EncryptionService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

/*
?? using @Mock anotation, I get @Nullpoinexcpetion, unless I use @Before. why is it so?
also, why does this test class still run DB?
 */

  private UserDAO userDao;
  private EncryptionService encryptionService;
  private RoleService roleService;
  private UserService userServiceImpl;

  public UserServiceImplTest(){
    userDao = mock(UserDAO.class);
    roleService = mock(RoleService.class);
    encryptionService = mock(EncryptionService.class);
    userServiceImpl = new UserServiceImpl(userDao, roleService, encryptionService);


  }

  @Test
  public void getUsersWithCommonInterest_test(){
    Mockito.when(userDao.findAll()).thenReturn(getMockUsers());
    Mockito.when(userDao.findByUsername("test5")).thenReturn(
        getMockUsers().get(0));
    List<User> retrievedUsers = userServiceImpl.getUsersWithCommonInterest("test5");
    User highestMatch = retrievedUsers.get(0);
    assertEquals("firstname7", highestMatch.getFirstName());
  }

  private List<User> getMockUsers(){
    List<User> mockUsers = new ArrayList<>();

    User test5 = new User();
    test5.setUsername("test5");
    test5.setFirstName("firstname5");
    test5.setLastName("lastname5");
    test5.setEmail("test5@email.com");
    test5.setPassword("admin");
    test5.getInterests().add(Interest.BUSINESS);
    test5.getInterests().add(Interest.SPORT);
    test5.getInterests().add(Interest.DANCE);
    mockUsers.add(test5);

    User test6 = new User();
    test6.setUsername("test6");
    test6.setFirstName("firstname6");
    test6.setLastName("lastname6");
    test6.setPassword("user");
    test6.setEmail("test6@email.com");
    test6.getInterests().add(Interest.PARTY);
    test6.getInterests().add(Interest.ART);
    mockUsers.add(test6);



    User test7 = new User();
    test7.setUsername("test7");
    test7.setFirstName("firstname7");
    test7.setLastName("lastname7");
    test7.setPassword("user");
    test7.setEmail("test7@email.com");
    test7.getInterests().add(Interest.BUSINESS);
    test7.getInterests().add(Interest.SPORT);
    test7.getInterests().add(Interest.DANCE);
    mockUsers.add(test7);

    User test8 = new User();
    test8.setUsername("test8");
    test8.setFirstName("firstname8");
    test8.setLastName("lastname8");
    test8.setPassword("user");
    test8.setEmail("test8@email.com");
    test8.getInterests().add(Interest.BUSINESS);
    test8.getInterests().add(Interest.NATURE);
    mockUsers.add(test8);

    return mockUsers;
  }
}
