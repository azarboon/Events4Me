package com.metropolia.events4me.Service.ServiceImpl;

import com.metropolia.events4me.Model.Event;
import com.metropolia.events4me.Model.Interest;
import com.metropolia.events4me.Model.User;
import com.metropolia.events4me.Service.EventService;
import com.metropolia.events4me.Service.EventUserService;
import com.metropolia.events4me.Service.UserService;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


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
    public List<Event> matchEventsForUser(User user) {
        Set<Interest> interests = user.getInterests();
        List<Event> futureEvents = eventService.listFutureEvents();
        return futureEvents.stream()
                            .filter(event -> interests.contains(event.getCategory()))
                            .collect(Collectors.toList());
    }

    /* This was done by Dima. I refactored it compeltely.

    // Joining event by the user and
    // adding participant to the event
    @Override
    public void joinEvent(User user, Integer eventId) {
        Event event = eventService.getEventById(eventId);
        user.getAttendingEvents().add(event);
        event.getConfirmedAttendees().add(user);
        userService.saveOrUpdateUser(user);
        eventService.saveOrUpdateEvent(event);
    }
*/
    @Override
    public void joinEvent(User user, Integer eventId) {
        Event event = eventService.getEventById(eventId);
        user.enrolEvent(event);
        event.acceptAttendee(user);
        userService.saveOrUpdateUser(user);
        eventService.saveOrUpdateEvent(event);
    }

}
