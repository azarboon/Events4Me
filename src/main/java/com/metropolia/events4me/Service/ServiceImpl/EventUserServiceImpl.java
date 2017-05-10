package com.metropolia.events4me.Service.ServiceImpl;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.Calendar.Freebusy;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.FreeBusyRequest;
import com.google.api.services.calendar.model.FreeBusyRequestItem;
import com.google.api.services.calendar.model.FreeBusyResponse;
import com.google.api.services.calendar.model.TimePeriod;
import com.metropolia.events4me.converter.Period;
import com.metropolia.events4me.converter.TimeSettingConverter;
import com.metropolia.events4me.Model.Days;
import com.metropolia.events4me.Model.Event;
import com.metropolia.events4me.Model.Interest;
import com.metropolia.events4me.Model.User;
import com.metropolia.events4me.Service.EventService;
import com.metropolia.events4me.Service.EventUserService;
import com.metropolia.events4me.Service.UserService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
        List<Period> periods = TimeSettingConverter.convertForTemplate(user.getTimeAvailability()).getPeriodsList();
        return futureEvents.stream()
            .filter(event -> interests.contains(event.getCategory()))
            .filter(e -> {
                Period weekDay = periods.get(Days.valueOf(e.getEndTime().getDayOfWeek().name()).ordinal());
                return e.getStartTime().toLocalTime().isAfter(weekDay.getStart()) && e.getEndTime().toLocalTime().isBefore(weekDay.getEnd());
            })
            .collect(Collectors.toList());
    }

    @Override
    public void joinEvent(User user, Integer eventId) {
        Event event = eventService.getEventById(eventId);
        user.enrolEvent(event);
        event.acceptAttendee(user);
        userService.saveOrUpdateUser(user);
        eventService.saveOrUpdateEvent(event);
    }

    @Override
    public Event createEvent(User user, Event event) {
        if (timeSlotIsFree(event)) {
            user.organizeNewEvent(event);
            if (!createEventOnCalendar(event)) {
                return null;
            }
            Event newEvent = eventService.saveOrUpdateEvent(event);
            userService.saveOrUpdateUser(user);
            return newEvent;
        }
        return null;
    }

    private boolean createEventOnCalendar(Event event) {
        String calendarID = event.getLocation().getCalendarID();
        com.google.api.services.calendar.model.Event googleEvent = new com.google.api.services.calendar.model.Event()
                .setSummary(event.getTitle())
                .setLocation(event.getLocation().getAddress())
                .setDescription(event.getDescription());

        DateTime startDate = new DateTime(event.getStartTime().toString() + ":00+03:00");
        EventDateTime start = new EventDateTime()
                .setDateTime(startDate)
                .setTimeZone("Europe/Helsinki");
        googleEvent.setStart(start);

        DateTime endDate = new DateTime(event.getEndTime().toString() + ":00+03:00");
        EventDateTime end = new EventDateTime()
                .setDateTime(endDate)
                .setTimeZone("Europe/Helsinki");
        googleEvent.setEnd(end);

        try {
            googleEvent = getCalendar().events().insert(calendarID, googleEvent).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        System.out.printf("Event created: %s\n", googleEvent.getHtmlLink());

        return true;
    }

    private boolean timeSlotIsFree(Event event) {
        String calendarID = event.getLocation().getCalendarID();
        FreeBusyRequest fbrq = new FreeBusyRequest();
        DateTime startDate = new DateTime(event.getStartTime().toString() + ":00+03:00");
        DateTime endTime = new DateTime(event.getEndTime().toString() + ":00+03:00");

        fbrq.setTimeMin(startDate);
        fbrq.setTimeMax(endTime);
        FreeBusyRequestItem fbrqItem = new FreeBusyRequestItem();
        fbrqItem.setId(calendarID);
        List<FreeBusyRequestItem> listOffbrqItems = new ArrayList<FreeBusyRequestItem>();
        listOffbrqItems.add(fbrqItem);

        fbrq.setItems(listOffbrqItems);

        Freebusy.Query fbq = null;
        FreeBusyResponse fbResponse = null;
        try {
            fbq = getCalendar().freebusy().query(fbrq);
            fbResponse = fbq.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(fbResponse.toString());

        StringBuilder busyPeriods = new StringBuilder("");
        for (TimePeriod period : fbResponse.getCalendars().get(calendarID).getBusy()) {
            busyPeriods.append(period.toString());
        }

        if (busyPeriods.length() < 10) {
            return true;
        } else {
            return false;
        }
    }

    private Calendar getCalendar() {
        JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
        HttpTransport HTTP_TRANSPORT = null;
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collection<String> scope = Collections.singleton(CalendarScopes.CALENDAR);
        FileInputStream auth_credentials = null;
        try {
            auth_credentials = new FileInputStream("src/main/resources/Events4me.p12");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        GoogleCredential credentials = null;
        try {
            credentials = createCredentialForServiceAccount(HTTP_TRANSPORT, JSON_FACTORY, System.getenv("SERVICE_ACCOUNT_ID"), scope, auth_credentials);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credentials)
                .setApplicationName("Events4ME").build();
        return service;
    }

    public GoogleCredential createCredentialForServiceAccount(
            HttpTransport transport,
            JsonFactory jsonFactory,
            String serviceAccountId,
            Collection<String> serviceAccountScopes, InputStream p12file) throws GeneralSecurityException, IOException {


        // need an environmental variable as P12_PASSWORD, which includes paassword
        String p12Password = System.getenv("P12_PASSWORD");
        KeyStore keystore = KeyStore.getInstance("PKCS12");

        keystore.load(p12file, p12Password.toCharArray());
        PrivateKey key = (PrivateKey) keystore.getKey("privatekey", p12Password.toCharArray());

        return new GoogleCredential.Builder().setTransport(transport)
                .setJsonFactory(jsonFactory)
                .setServiceAccountId(serviceAccountId)
                .setServiceAccountScopes(serviceAccountScopes)
                .setServiceAccountPrivateKey(key)
                .build();
    }

}
