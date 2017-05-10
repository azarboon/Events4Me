package com.metropolia.events4me.Service;

import com.google.api.services.calendar.Calendar;
import com.metropolia.events4me.Model.Event;
import com.metropolia.events4me.Model.Location;
import com.metropolia.events4me.Model.User;
import java.util.List;


public interface EventUserService {

    List<Event> matchEventsForUser(User user);

    Event createEvent(User user, Event event);

    void joinEvent(User user, Integer eventId);



}
