package com.metropolia.events4me.Service.ServiceImpl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.metropolia.events4me.DAO.UserDAO;
import com.metropolia.events4me.Model.User;
import com.metropolia.events4me.Service.RoleService;
import com.metropolia.events4me.Service.security.EncryptionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

  UserDAO userDao;
  UserServiceImpl userService;
  EncryptionService encryptionService;
  RoleService roleService;

  @Before
  public void setUp(){
    userDao = mock(UserDAO.class);
    roleService = mock(RoleService.class);
    encryptionService = mock(EncryptionService.class);
    userService = new UserServiceImpl(userDao, roleService, encryptionService);
    when(userDao.findByUsername(anyString())).thenReturn(getMockUser());
    //when(userDao.findAll()).thenReturn(getUserList());
  }

  @Test
  public void testFindByUserName(){
    User user = userService.findByUsername("jack" );
    System.out.println("this is mockeduser firstname: " + getMockUser().getFirstName() +
        " and this is user.getUsername() " + user.getUsername());
    assertEquals("Username should equal", getMockUser().getLastName(), user.getUsername());
  }

  public User getMockUser(){
    User jack = new User();
    jack.setFirstName("jack");
    jack.setLastName("lastnameofJack");
    jack.setUsername("jack");
    jack.setEmail("jack@email.com");
    jack.setPassword("pass");
    return jack;

  }
}
