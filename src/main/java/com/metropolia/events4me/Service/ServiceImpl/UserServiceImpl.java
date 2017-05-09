package com.metropolia.events4me.Service.ServiceImpl;

import com.metropolia.events4me.DAO.UserDAO;
import com.metropolia.events4me.Model.Days;
import com.metropolia.events4me.Model.Event;
import com.metropolia.events4me.Model.Interest;
import com.metropolia.events4me.Model.User;
import com.metropolia.events4me.Service.RoleService;
import com.metropolia.events4me.Service.UserService;
import com.metropolia.events4me.Service.security.EncryptionService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service("UserServiceImpl")
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;
    private BCryptPasswordEncoder encryptionService;
    private RoleService roleService;

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Autowired
    public void setEncryptionService(BCryptPasswordEncoder encryptionService) {
        this.encryptionService = encryptionService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }


    @Override
    public User findByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    @Override
    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    public boolean checkUserExists(String username, String email) {
        return checkUsernameExists(username) || checkEmailExists(username);
    }


    @Override
    public boolean checkUsernameExists(String username) {
        return null != findByUsername(username);
    }

    @Override
    public boolean checkEmailExists(String email) {
        return null != findByEmail(email);
    }

    @Override
    public User saveOrUpdateUser(User user) {

        if (user.getPassword() != null) {
            user.setEncryptedPassword(encryptionService.encode(user.getPassword()));
        }
        if (user.getUserId() == null) {
            roleService.listRoles().forEach(role -> {
                if (role.getRole().equals("USER")) {
                    user.addRole(role);
                }
            });
        }
        return userDAO.save(user);
    }


    public List<User> findUserList() {
        return userDAO.findAll();
    }


    @Override
    public void joinEvent(User user, Event event) {
        user.getAttendingEvents().add(event);
        userDAO.save(user);
    }

    @Override
    public List<User> getUsersWithCommonInterest(String username) {
        return getTopMatches(username, 10);
    }


    @Override
    public void sendFriendRequestTo(String senderUserName, String recieverUserName) {
        if ((userDAO.findByUsername(senderUserName) != null) && (
                userDAO.findByUsername(recieverUserName) != null)) {
            User sender = userDAO.findByUsername(senderUserName);
            User reciever = userDAO.findByUsername(recieverUserName);
            sender.sendFriendRequestTo(reciever);
            userDAO.save(reciever);
            userDAO.save(sender);

        }
    }

    @Override
    public void recieveFriendRequestFrom(String senderUserName, String recieverUserName) {
        if ((userDAO.findByUsername(senderUserName) != null) && (
                userDAO.findByUsername(recieverUserName) != null)) {
            User sender = userDAO.findByUsername(senderUserName);
            User reciever = userDAO.findByUsername(recieverUserName);
            reciever.recieveFriendRequestFrom(sender);
            userDAO.save(reciever);
            userDAO.save(sender);
        }
    }

    @Override
    public void acceptFriend(String senderUserName, String recieverUserName) {
        if ((userDAO.findByUsername(senderUserName) != null) && (
                userDAO.findByUsername(recieverUserName) != null)) {
            User sender = userDAO.findByUsername(senderUserName);
            User reciever = userDAO.findByUsername(recieverUserName);
            reciever.acceptFriend(sender);
            userDAO.save(reciever);
            userDAO.save(sender);
        }

    }

    @Override
    public Set<User> getPendingFriendRequests(String username) {
        if (userDAO.findByUsername(username) != null) {

            User user = userDAO.findByUsername(username);
            return user.getPendingFriendRequests();
        }
        return null;
    }


    @Override
    public void delete(User user) {
        userDAO.delete(user);
    }


    @Override
    public User getById(Integer id) {
        return userDAO.findOne(id);
    }

    @Override
    public void delete(Integer id) {
        userDAO.delete(getById(id));
    }


    private List<User> getTopMatches(String username, int numberOfTOpMatches) {
        if (numberOfTOpMatches >= getSortedList(username).size()) {
            numberOfTOpMatches = getSortedList(username).size() - 1;

        }

        List<userWithCountOfInterests> topMatchesWithCount = getSortedList(username).subList(
                getSortedList(username).size() - numberOfTOpMatches, getSortedList(username).size());

        Collections.sort(topMatchesWithCount);

        List<User> topMatches = new ArrayList<>();
        for (userWithCountOfInterests each : topMatchesWithCount) {
            topMatches.add(each.getUser());
        }
        return topMatches;
    }


    private List<userWithCountOfInterests> getSortedList(String username) {
        User currentUser = userDAO.findByUsername(username);


        List<User> allUsers = userDAO.findAll();
        List<userWithCountOfInterests> usersWithNumberOfCommonInterests = new ArrayList<>();

        for (User each : allUsers) {
            int commonInterests = getNumberOfCommonInterests(currentUser, each);
            usersWithNumberOfCommonInterests.add(new userWithCountOfInterests(each, commonInterests));
        }
        Collections.sort(usersWithNumberOfCommonInterests);
        return usersWithNumberOfCommonInterests;
    }

    private int getNumberOfCommonInterests(User user1, User user2) {
        int commonInterests = 0;
        Set<Interest> interests1 = user1.getInterests();
        Set<Interest> interests2 = user2.getInterests();

        for (Interest each : interests1) {
            if (interests2.contains(each)) {
                commonInterests++;
            }
        }
        return commonInterests;
    }

    @Override
    public List<User> listUsers() {
        return userDAO.findAll();
    }

    @Override
    public List<Event> listUserEvents(User user) {
        return user.getAttendingEvents();
    }

    @Override
    public List<Event> listUserFutureEvents(User user) {
        return user.getAttendingEvents().stream().filter(event -> event.getEndTime().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Event> listUserPastEvents(User user) {
        return user.getAttendingEvents().stream().filter(event -> event.getEndTime().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    private class userWithCountOfInterests implements Comparable<userWithCountOfInterests> {

        private int commonInterest;
        private User user;


        public userWithCountOfInterests(User user, int commonInterest) {
            this.user = user;
            this.commonInterest = commonInterest;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public int getCommonInterest() {
            return commonInterest;
        }

        public void setCommonInterest(int commonInterest) {
            this.commonInterest = commonInterest;
        }


        @Override
        public int compareTo(userWithCountOfInterests o) {
            int difference = o.getCommonInterest() - this.commonInterest;
            if (difference > 0) {
                return 1;
            } else if (difference == 0) {
                return 0;
            }
            return -1;
        }
    }


}
