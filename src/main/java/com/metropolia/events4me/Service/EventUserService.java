package com.metropolia.events4me.Service;

import com.metropolia.events4me.Model.Event;
import com.metropolia.events4me.Model.User;
import java.util.List;


public interface EventUserService {

    List<Event> matchEventsForUser(User user);

    void joinEvent(User user, Integer eventId);
}
