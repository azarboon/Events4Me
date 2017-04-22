package com.metropolia.events4me.Service.ServiceImpl;

import com.metropolia.events4me.Model.Event;
import com.metropolia.events4me.Model.Interest;
import com.metropolia.events4me.Model.User;
import com.metropolia.events4me.Service.EventService;
import com.metropolia.events4me.Service.EventUserService;
import com.metropolia.events4me.Service.UserService;
import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by Dmitry on 16.04.2017.
 */
@Service
public class EventUserServiceImpl implements EventUserService {

    private UserService userService;
    private EventService eventService;

    @Autowired
    @Qualifier("UserServiceImpl")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    // The method returns the list of recommended events
    // for the user according to his interests
    @Override
    public List<Event> matchEventsForUser(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Set<Interest> interests = user.getInterests();
        List<Event> futureEvents = eventService.listFutureEvents();
        return futureEvents.stream()
                            .filter(event -> interests.contains(event.getCategory()))
                            .collect(Collectors.toList());
    }

}
