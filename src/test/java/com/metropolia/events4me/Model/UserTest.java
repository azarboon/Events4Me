package com.metropolia.events4me.Model;

import static org.junit.Assert.assertEquals;

import com.metropolia.events4me.DAO.UserDAO;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Mock
    private UserDAO userDAO;


    @Test
    public void RecieveFriendRequestTest() {

        getMockUsers();
        User dima = userDAO.findByUsername("dima");
        User martin = userDAO.findByUsername("martin");
        dima.sendFriendRequestTo(martin);
        Set<com.metropolia.events4me.Model.User> requestSenders = martin.getPendingFriendRequests();
        System.out.println("List 3: friend ship request recieved from following users:");
        requestSenders.forEach(user -> System.out.println(user.getUsername()));
        martin.acceptFriend(dima);

        Set<com.metropolia.events4me.Model.User> friends = martin.getFriends();
        assertEquals(true, friends.contains(dima));

    }

    public void getMockUsers() {

        Mockito.when(userDAO.findByUsername("dima")).thenReturn(new User("dima",
                "dmitry", "dmityLastName", "dimaPass", "dima@email.com"));

        Mockito.when(userDAO.findByUsername("martin")).thenReturn(new User("martin",
                "martinFIrstName", "martinLastName", "martinPass", "martin@email.com"));
    }

}
