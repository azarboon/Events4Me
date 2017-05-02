package com.metropolia.events4me.bootstrap;

import com.metropolia.events4me.Converter.TimeSettingConverter;
import com.metropolia.events4me.Model.*;
import com.metropolia.events4me.Model.security.Role;
import com.metropolia.events4me.Service.EventService;
import com.metropolia.events4me.Service.RoleService;
import com.metropolia.events4me.Service.TimeSettingService;
import com.metropolia.events4me.Service.UserService;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


@Component
public class SpringDataBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private UserService userService;
    private EventService eventService;
    private RoleService roleService;
    private TimeSettingService timeSettingService;

    @Autowired
    public void setTimeSettingService(TimeSettingService timeSettingService) {
        this.timeSettingService = timeSettingService;
    }

    @Autowired
    @Qualifier("UserServiceImpl")
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
        loadTimeSettings();

    }

    private void loadTimeSettings() {
//        TimeSetting  test = new TimeSetting();
//        LocalTime s = LocalTime.of(0, 0);
//        LocalTime e = LocalTime.of(23, 59);
//        String i = s + ";" + e;
//        test.getTimeMap().put(Days.Monday, i);
//        test.getTimeMap().put(Days.Tuesday, i);
//        test.getTimeMap().put(Days.Wednesday, i);
//        test.getTimeMap().put(Days.Thursday, i);
//        test.getTimeMap().put(Days.Friday, i);
//        test.getTimeMap().put(Days.Saturday, i);
//        test.getTimeMap().put(Days.Sunday, i);
//        TimeSettingConverter.convertForTemplate(test);

        for (User u : userService.listUsers()) {
            TimeSetting  timeSetting = new TimeSetting();
            LocalTime start = LocalTime.of(10, 10);
            LocalTime end = LocalTime.of(23, 59);
            String interval = start + ";" + end;
            timeSetting.getTimeMap().put(Days.Monday, interval);
            timeSetting.getTimeMap().put(Days.Tuesday, interval);
            timeSetting.getTimeMap().put(Days.Wednesday, interval);
            timeSetting.getTimeMap().put(Days.Thursday, interval);
            timeSetting.getTimeMap().put(Days.Friday, interval);
            timeSetting.getTimeMap().put(Days.Saturday, interval);
            timeSetting.getTimeMap().put(Days.Sunday, interval);
            u.setTimeAvailability(timeSetting);
            timeSettingService.saveOrUpdate(timeSetting);
            userService.saveOrUpdateUser(u);
        }
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
        dmitry.getInterests().add(Interest.DANCE);
        userService.saveOrUpdateUser(dmitry);

        User martin = new User();
        martin.setUsername("martin");
        martin.setFirstName("martin");
        martin.setPassword("user");
        martin.getInterests().add(Interest.PARTY);
        martin.getInterests().add(Interest.ART);
        userService.saveOrUpdateUser(martin);


//        User niklas = new User();
//        niklas.setUsername("nilas");
//        niklas.setFirstName("niklas");
//        niklas.setPassword("user");
//        niklas.getInterests().add(Interest.BUSINESS);
//        niklas.getInterests().add(Interest.SPORT);
//        niklas.getInterests().add(Interest.DANCE);
//        userService.saveOrUpdate(niklas);
//
//        User user4 = new User();
//      user4.setUsername("user4");
//      user4.setFirstName("firstname4");
//      user4.setPassword("user");
//      user4.getInterests().add(Interest.BUSINESS);
//      user4.getInterests().add(Interest.NATURE);
//      userService.saveOrUpdate(user4);

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
