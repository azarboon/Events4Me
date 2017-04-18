package com.metropolia.events4me.bootstrap;

import com.metropolia.events4me.Model.Event;
import com.metropolia.events4me.Model.Interest;
import com.metropolia.events4me.Model.User;
import com.metropolia.events4me.Model.security.Role;
import com.metropolia.events4me.Service.EventService;
import com.metropolia.events4me.Service.RoleService;
import com.metropolia.events4me.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Dmitry on 17.04.2017.
 */
@Component
public class SpringDataBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private UserService userService;
    private EventService eventService;
    private RoleService roleService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        loadEvents();
        loadRoles();
        loadUsers();
//        assignUserRole();
        assignAdminRole();

    }

    private void assignAdminRole() {
        List<Role> roles = roleService.listRoles();
        List<User> users = userService.listUsers();

        roles.forEach(role -> {
            if (role.getRole().equalsIgnoreCase("ADMIN")) {
                users.forEach(user -> {
                    if (user.getUsername().equals("dima")) {
                        user.addRole(role);
                        userService.saveOrUpdateUser(user);
                    }
                });
            }
        });

    }

    private void assignUserRole() {
        List<Role> roles = roleService.listRoles();
        List<User> users = userService.listUsers();

        roles.forEach(role -> {
            if (role.getRole().equalsIgnoreCase("USER")) {
                users.forEach(user -> {
                        user.addRole(role);
                        userService.saveOrUpdateUser(user);
                });
            }
        });
    }

    private void loadRoles() {
        Role role = new Role();
        role.setRole("USER");
        roleService.saveOrUpdateRole(role);

        Role adminRole = new Role();
        adminRole.setRole("ADMIN");
        roleService.saveOrUpdateRole(adminRole);
    }

    private void loadUsers() {
        User dmitry = new User();
        dmitry.setUsername("dima");
        dmitry.setFirstName("dmitry");
        dmitry.setPassword("admin");
        dmitry.getInterests().add(Interest.BUSINESS);
        dmitry.getInterests().add(Interest.SPORT);

        User martin = new User();
        martin.setUsername("looser");
        martin.setFirstName("martin");
        martin.setPassword("user");
        martin.getInterests().add(Interest.PARTY);
        martin.getInterests().add(Interest.ART);

        martin.setFriend(dmitry);
        dmitry.setFriend(martin);
        dmitry.getFriends().add(martin);
        martin.getFriends().add(dmitry);
        userService.saveOrUpdateUser(martin);
        userService.saveOrUpdateUser(dmitry);
    }

    private void loadEvents() {
        Event sportEvent = new Event();
        sportEvent.setName("Sport event");
        sportEvent.setDateTime(LocalDateTime.of(2017, 6, 2, 13, 0));
        sportEvent.setCategory(Interest.SPORT);
        eventService.saveOrUpdateEvent(sportEvent);

        Event partyEvent = new Event();
        partyEvent.setName("Party event");
        partyEvent.setDateTime(LocalDateTime.of(2017, 6, 10, 13, 0));
        partyEvent.setCategory(Interest.PARTY);
        eventService.saveOrUpdateEvent(partyEvent);

        Event businessEvent = new Event();
        businessEvent.setName("Business event");
        businessEvent.setDateTime(LocalDateTime.of(2017, 6, 2, 15, 0));
        businessEvent.setCategory(Interest.BUSINESS);
        eventService.saveOrUpdateEvent(businessEvent);

        Event businessEventPast = new Event();
        businessEventPast.setName("Business event past");
        businessEventPast.setDateTime(LocalDateTime.of(2017, 2, 2, 13, 0));
        businessEventPast.setCategory(Interest.BUSINESS);
        eventService.saveOrUpdateEvent(businessEventPast);

        Event sportEventPast = new Event();
        sportEventPast.setName("Sport event past");
        sportEventPast.setDateTime(LocalDateTime.of(2017, 2, 3, 13, 0));
        sportEventPast.setCategory(Interest.SPORT);
        eventService.saveOrUpdateEvent(sportEventPast);
    }
}
