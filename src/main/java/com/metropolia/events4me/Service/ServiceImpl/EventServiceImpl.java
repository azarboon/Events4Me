package com.metropolia.events4me.Service.ServiceImpl;

import com.metropolia.events4me.DAO.EventDAO;
import com.metropolia.events4me.DAO.UserDAO;
import com.metropolia.events4me.Model.Event;
import com.metropolia.events4me.Model.User;
import com.metropolia.events4me.Service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitry on 13.04.2017.
 */

@Service
public class EventServiceImpl implements EventService {

    private EventDAO eventDAO;

    @Autowired
    public void setEventDAO(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    public List<Event> listAllEvents() {
        return eventDAO.findAll();
    }

    public List<Event> listPastEvents() {
        List<Event> allEvents = listAllEvents();
        LocalDate today = LocalDate.now();
        List<Event> pastEvents = new ArrayList<>();
        allEvents.forEach(event -> {
            if (event.getDateTime().toLocalDate().isBefore(today)) {
                pastEvents.add(event);
            }
        });
        return pastEvents;
    }

    public List<Event> listFutureEvents() {
        List<Event> allEvents = listAllEvents();
        LocalDate today = LocalDate.now();
        List<Event> futureEvents = new ArrayList<>();
        allEvents.forEach(event -> {
            if (event.getDateTime().toLocalDate().isAfter(today)) {
                futureEvents.add(event);
            }
        });
        return futureEvents;
    }

    public Event createEvent(Event newEvent) {
        return eventDAO.save(newEvent);
    }

    @Override
    public Event saveEvent(Event event) {
        return eventDAO.save(event);
    }


}
