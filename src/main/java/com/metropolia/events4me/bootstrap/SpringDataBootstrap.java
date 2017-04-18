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
        List<User> users = userService.findUserList();

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
        List<User> users = userService.findUserList();

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
        dmitry.getInterests().add(Interest.DANCE);
        userService.saveOrUpdateUser(dmitry);

        User martin = new User();
        martin.setUsername("looser");
        martin.setFirstName("martin");
        martin.setPassword("user");
        martin.getInterests().add(Interest.PARTY);
        martin.getInterests().add(Interest.ART);
        userService.saveOrUpdateUser(martin);

        User niklas = new User();
      niklas.setUsername("nilas");
      niklas.setFirstName("niklas");
      niklas.setPassword("user");
      niklas.getInterests().add(Interest.BUSINESS);
      niklas.getInterests().add(Interest.SPORT);
      niklas.getInterests().add(Interest.DANCE);
        userService.saveOrUpdateUser(niklas);

        User user4 = new User();
      user4.setUsername("user4");
      user4.setFirstName("user4firstname");
      user4.setPassword("user");
      user4.getInterests().add(Interest.BUSINESS);
      user4.getInterests().add(Interest.NATURE);
        userService.saveOrUpdateUser(user4);
    }

    private void loadEvents() {
        Event sportEvent = new Event();
        sportEvent.setName("Sport event");
        sportEvent.setDateTime(LocalDateTime.now());
        sportEvent.setCategory(Interest.SPORT);
        eventService.saveEvent(sportEvent);

        Event partyEvent = new Event();
        partyEvent.setName("Party event");
        partyEvent.setDateTime(LocalDateTime.now());
        partyEvent.setCategory(Interest.PARTY);
        eventService.saveEvent(partyEvent);

        Event businessEvent = new Event();
        businessEvent.setName("Business event");
        businessEvent.setDateTime(LocalDateTime.now());
        businessEvent.setCategory(Interest.BUSINESS);
        eventService.saveEvent(businessEvent);
    }
}
