package com.metropolia.events4me.Service;

import com.metropolia.events4me.Model.Event;
import com.metropolia.events4me.Model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Dmitry on 18.04.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class EventUserServiceImplTest {

    private EventUserService eventUserService;
    private UserService userService;
    private EventService eventService;

    @Autowired
    public void setEventUserService(EventUserService eventUserService) {
        this.eventUserService = eventUserService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    @Test
    @Transactional
    public void matchEventsForUserTest() throws Exception {
        User user = userService.findByUsername("dima");
        List<Event> list = eventUserService.matchEventsForUser(user);
        for (Event e : list) {
            System.out.println(e.getTitle());
        }
        assert list.size() == 1;
    }

}
