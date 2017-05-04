package com.metropolia.events4me.Controller;

import com.metropolia.events4me.Model.Event;
import com.metropolia.events4me.Model.User;
import com.metropolia.events4me.Service.EventUserService;
import com.metropolia.events4me.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;


@Controller
public class EventUserController {

    private EventUserService eventUserService;
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setEventUserService(EventUserService eventUserService) {
        this.eventUserService = eventUserService;
    }

    @RequestMapping("preferedevents")
    public @ResponseBody
    List<Event> getMatchedEventsForUser(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return eventUserService.matchEventsForUser(user);
    }

    @RequestMapping("join/event/{id}")
    public @ResponseBody
    void joinEvent(Principal principal, @PathVariable Integer id) {
        User user = userService.findByUsername(principal.getName());
        eventUserService.joinEvent(user, id);
    }
}
