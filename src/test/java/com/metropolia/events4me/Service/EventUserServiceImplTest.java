//package com.metropolia.events4me.Service;
//
//import com.metropolia.events4me.Model.Event;
//import com.metropolia.events4me.Model.User;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.transaction.Transactional;
//import java.util.List;
//
///**
// * Created by Dmitry on 18.04.2017.
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
////@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
//public class EventUserServiceImplTest {
//
//    private EventUserService eventUserService;
//    private UserService userService;
//    private EventService eventService;
//
//    @Autowired
//    public void setEventUserService(EventUserService eventUserService) {
//        this.eventUserService = eventUserService;
//    }
//
//    @Autowired
//    public void setUserService(UserService userService) {
//        this.userService = userService;
//    }
//
//    @Autowired
//    public void setEventService(EventService eventService) {
//        this.eventService = eventService;
//    }
//
//    @Test
//    @Transactional
//    public void matchEventsForUserTest() throws Exception {
//        User user = userService.findByUsername("dima");
//        List<Event> list = eventUserService.matchEventsForUser(user);
//        System.out.println(list.size());
//        assert list.size() == 2;
//    }
//
//    @Test
//    @Transactional
//    public void joinEventTest() throws Exception {
//        User user = userService.findByUsername("dima");
//        eventService.listFutureEvents().forEach(event -> eventUserService.joinEvent(user, event.getEventId()));
//        User userWithEvents = userService.findByUsername("dima");
//        assert userWithEvents.getAttendingEvents().size() == 3;
//        int counter = 0;
//        for (Event event : eventService.listFutureEvents()) {
//            counter += event.getConfirmedAttendees().size();
//        }
//        assert counter == 3;
//    }
//}
