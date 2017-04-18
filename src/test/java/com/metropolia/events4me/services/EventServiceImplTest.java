package com.metropolia.events4me.services;

import com.metropolia.events4me.Model.Event;
import com.metropolia.events4me.Service.EventService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by Dmitry on 18.04.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventServiceImplTest {

    private EventService eventService;

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    @Test
    public void listAllSortTest() throws Exception {
        List<Event> list = eventService.listAllEvents();
        assert list.get(0).getEventId() == 4;
        assert list.get(list.size() - 1).getEventId() == 2;
    }

    @Test
    public void listPastEventsTest() throws Exception {
        assert eventService.listPastEvents().size() == 2;
    }

    @Test
    public void listFutureEventsTest() throws Exception {
        assert eventService.listFutureEvents().size() == 3;
    }
}
