package com.metropolia.events4me.Service;

import com.metropolia.events4me.Model.Event;
import com.metropolia.events4me.Model.User;
import java.security.Principal;
import java.util.List;
import java.util.Set;

/**
 * Created by Dmitry on 11.04.2017.
 */
public interface UserService {

    User findByEmail(String email);

    User findByUsername(String username);

    boolean checkUserExists(String username, String email);

    boolean checkUsernameExists(String username);

    boolean checkEmailExists(String email);

    User saveOrUpdateUser(User user);

    List<User> findUserList();

    List<Event> listUserEvents(Principal principal);

    void joinEvent(Principal principal, Event event);

    List<User> getUsersWithCommonInterest(String username);

    void sendFriendRequestTo(String sender, String reciever);
    void recieveFriendRequestFrom(String sender, String reciever);
    void acceptFriend(String sender, String reciever);
    Set<User> getPendingFriendRequests(String username);

}
