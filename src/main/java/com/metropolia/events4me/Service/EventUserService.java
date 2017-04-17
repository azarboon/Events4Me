package com.metropolia.events4me.Service;

import com.metropolia.events4me.Model.Event;
import com.metropolia.events4me.Model.User;

import java.security.Principal;
import java.util.List;

/**
 * Created by Dmitry on 17.04.2017.
 */
public interface EventUserService {

    List<Event> matchEventsForUser(Principal principal);
}
