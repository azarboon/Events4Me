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

        User user4 = new User();
        user4.setUsername("user4");
        user4.setFirstName("firstname4");
        user4.setPassword("user");
        user4.getInterests().add(Interest.BUSINESS);
        user4.getInterests().add(Interest.NATURE);
        userService.saveOrUpdateUser(user4);


        User test5 = new User();
        test5.setUsername("test5");
        test5.setFirstName("firstname5");
        test5.setLastName("lastname5");
        test5.setEmail("test5@email.com");
        test5.setPassword("admin");
        test5.getInterests().add(Interest.BUSINESS);
        test5.getInterests().add(Interest.SPORT);
        test5.getInterests().add(Interest.DANCE);



    /*
    User retrieved = userService.findByUsername("test5");
    retrieved.setFirstName("another name");
    userService.saveOrUpdateUser(retrieved);
    */

        User test6 = new User();
        test6.setUsername("test6");
        test6.setFirstName("firstname6");
        test6.setLastName("lastname6");
        test6.setPassword("user");
        test6.setEmail("test6@email.com");
        test6.getInterests().add(Interest.PARTY);
        test6.getInterests().add(Interest.ART);


        User test7 = new User();
        test7.setUsername("test7");
        test7.setFirstName("firstname7");
        test7.setLastName("lastname7");
        test7.setPassword("user");
        test7.setEmail("test7@email.com");
        test7.getInterests().add(Interest.BUSINESS);
        test7.getInterests().add(Interest.SPORT);
        test7.getInterests().add(Interest.DANCE);

        User test8 = new User();
        test8.setUsername("test8");
        test8.setFirstName("firstname8");
        test8.setLastName("lastname8");
        test8.setPassword("user");
        test8.setEmail("test8@email.com");
        test8.getInterests().add(Interest.BUSINESS);
        test8.getInterests().add(Interest.NATURE);

        Event sportEvent = new Event();
        sportEvent.setTitle("Sport event");
        sportEvent.setEndTime(LocalDateTime.of(2017, 6, 2, 13, 0));
        sportEvent.setCategory(Interest.SPORT);


        test5.sendFriendRequestTo(test6);
        test6.acceptFriend(test5);

        //TODO: make the event have the ablity for automatic acceptance
        test5.organizeNewEvent(sportEvent);
        test6.enrolEvent(sportEvent);
        sportEvent.acceptAttendee(test6);
        test7.enrolEvent(sportEvent);
        test8.enrolEvent(sportEvent);
        sportEvent.acceptAttendee(test8);


        userService.saveOrUpdateUser(test5);
        userService.saveOrUpdateUser(test6);
        userService.saveOrUpdateUser(test7);
        userService.saveOrUpdateUser(test8);
        eventService.saveOrUpdateEvent(sportEvent);
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
