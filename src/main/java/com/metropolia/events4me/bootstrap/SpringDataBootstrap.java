package com.metropolia.events4me.bootstrap;

import com.metropolia.events4me.Model.*;
import com.metropolia.events4me.Model.security.Role;
import com.metropolia.events4me.Service.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * THis class starts methods which populates database
 * with data for testing
 */

@Component
public class SpringDataBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private UserService userService;
    private EventService eventService;
    private RoleService roleService;
    private TimeSettingService timeSettingService;
    private LocationService locationService;

    @Autowired
    public void setTimeSettingService(TimeSettingService timeSettingService) {
        this.timeSettingService = timeSettingService;
    }

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

    @Autowired
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        loadLocations();
        loadEvents();
        loadRoles();
        loadUsers();
//        assignUserRole();
        assignAdminRole();
        loadTimeSettings();
        setLocationForEvents();
        makeUsersConnect();
    }

    private void loadTimeSettings() {

        for (User u : userService.listUsers()) {
            TimeSetting timeSetting = new TimeSetting();
            LocalTime start = LocalTime.of(17, 0);
            LocalTime end = LocalTime.of(21, 0);
            String interval = start + ";" + end;
            timeSetting.getTimeMap().put(Days.MONDAY, interval);
            timeSetting.getTimeMap().put(Days.TUESDAY, interval);
            timeSetting.getTimeMap().put(Days.WEDNESDAY, interval);
            timeSetting.getTimeMap().put(Days.THURSDAY, interval);
            timeSetting.getTimeMap().put(Days.FRIDAY, interval);
            timeSetting.getTimeMap().put(Days.SATURDAY, interval);
            timeSetting.getTimeMap().put(Days.SUNDAY, interval);
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
        dmitry.setFirstName("Dmitry");
        dmitry.setLastName("Khramov");
        dmitry.setEmail("dk@metropolia.fi");
        dmitry.setPassword("admin");
        dmitry.setCountry("Finland");
        dmitry.setBirthday("02.05.1992");
        dmitry.getInterests().add(Interest.BUSINESS);
        dmitry.getInterests().add(Interest.SPORT);
        dmitry.getInterests().add(Interest.DANCE);
        userService.saveOrUpdateUser(dmitry);

        User martin = new User();
        martin.setUsername("martin");
        martin.setFirstName("Martin");
        martin.setLastName("Anderson");
        martin.setEmail("ma@metropolia.fi");
        martin.setPassword("martin");
        martin.setCountry("Finland");
        martin.setBirthday("15.12.1985");
        martin.getInterests().add(Interest.PARTY);
        martin.getInterests().add(Interest.ART);
        martin.getInterests().add(Interest.MOVIES);
        userService.saveOrUpdateUser(martin);

        User niklas = new User();
        niklas.setUsername("niklas");
        niklas.setFirstName("Niklas");
        niklas.setLastName("Kuusisto");
        niklas.setEmail("nk@metropolia.fi");
        niklas.setPassword("niklas");
        niklas.setCountry("Finland");
        niklas.setBirthday("20.08.1990");
        niklas.getInterests().add(Interest.PARTY);
        niklas.getInterests().add(Interest.SPORT);
        niklas.getInterests().add(Interest.ART);
        userService.saveOrUpdateUser(niklas);

        User henri = new User();
        henri.setUsername("henri");
        henri.setFirstName("Henri");
        henri.setLastName("Järvinen");
        henri.setEmail("hj@metropolia.fi");
        henri.setPassword("henri");
        henri.setCountry("Finland");
        henri.setBirthday("01.02.1990");
        henri.getInterests().add(Interest.NATURE);
        henri.getInterests().add(Interest.SPORT);
        henri.getInterests().add(Interest.PARTY);
        userService.saveOrUpdateUser(henri);

    }

    private void loadEvents() {

        Event sportEvent = new Event();
        sportEvent.setTitle("Sport event");
        sportEvent.setStartTime(LocalDateTime.of(2017, 5, 22, 13, 0));
        sportEvent.setEndTime(LocalDateTime.of(2017, 5, 22, 15, 0));
        sportEvent.setCategory(Interest.SPORT);
        eventService.saveOrUpdateEvent(sportEvent);

        Event partyEvent = new Event();
        partyEvent.setTitle("Party event");
        partyEvent.setStartTime(LocalDateTime.of(2017, 6, 10, 18, 0));
        partyEvent.setEndTime(LocalDateTime.of(2017, 6, 10, 20, 0));
        partyEvent.setCategory(Interest.PARTY);
        eventService.saveOrUpdateEvent(partyEvent);

        Event businessEvent = new Event();
        businessEvent.setTitle("Business event");
        businessEvent.setStartTime(LocalDateTime.of(2017, 5, 22, 18, 0));
        businessEvent.setEndTime(LocalDateTime.of(2017, 5, 22, 22, 0));
        businessEvent.setCategory(Interest.BUSINESS);
        eventService.saveOrUpdateEvent(businessEvent);

        Event businessEventPast = new Event();
        businessEventPast.setTitle("Business event past");
        businessEventPast.setEndTime(LocalDateTime.of(2017, 2, 2, 13, 0));
        businessEventPast.setEndTime(LocalDateTime.of(2017, 2, 2, 14, 0));
        businessEventPast.setCategory(Interest.BUSINESS);
        eventService.saveOrUpdateEvent(businessEventPast);

        Event sportEventPast = new Event();
        sportEventPast.setTitle("Sport event past");
        sportEventPast.setEndTime(LocalDateTime.of(2017, 2, 3, 13, 0));
        sportEventPast.setEndTime(LocalDateTime.of(2017, 2, 3, 14, 0));
        sportEventPast.setCategory(Interest.SPORT);
        eventService.saveOrUpdateEvent(sportEventPast);


    }

    private void loadLocations() {

        Location cafeMascot = new Location();
        cafeMascot.setAddress("Neljäs linja 2, 00530 Helsinki");
        cafeMascot.setCalendarID("qo8nro2mtp67dn78qk36b60vqg@group.calendar.google.com");
        cafeMascot.setName("Cafe Mascot");

        Location maxine = new Location();
        maxine.setAddress("Urho Kekkosen katu 1A, 00100 Helsinki");
        maxine.setCalendarID("3n4jiu1vp1hma8459b71jbmh8g@group.calendar.google.com");
        maxine.setName("Maxine");

        Location sportHall = new Location();
        sportHall.setName("Töölö Sports Hall");
        sportHall.setCalendarID("t5v4rltbcsqkb3fdusfp4k2jqk@group.calendar.google.com");
        sportHall.setAddress("Paavo Nurmen kuja 1, 00250 Helsinki");

        Location businessHall = new Location();
        businessHall.setAddress("Mannerheimintie 13e, 00100 Helsinki");
        businessHall.setName("Finlandia Hall");
        businessHall.setCalendarID("b6tatnpuvhi29gkq7aafn6sm8g@group.calendar.google.com");

        locationService.saveOrUpdateLocation(cafeMascot);
        locationService.saveOrUpdateLocation(maxine);
        locationService.saveOrUpdateLocation(sportHall);
        locationService.saveOrUpdateLocation(businessHall);
    }

    private void setLocationForEvents() {
        eventService.setLocationForEvent(1,3);
        eventService.setLocationForEvent(2, 2);
        eventService.setLocationForEvent(3, 4);
        eventService.setLocationForEvent(4, 2);
        eventService.setLocationForEvent(5, 3);
    }

    private void makeUsersConnect() {
        userService.sendFriendRequestTo("henri", "niklas");
        userService.sendFriendRequestTo("dima", "niklas");
        userService.sendFriendRequestTo("martin", "niklas");
        userService.acceptFriend("henri", "niklas");
        userService.acceptFriend("dima", "niklas");
    }
}
