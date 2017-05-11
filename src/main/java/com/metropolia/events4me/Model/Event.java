package com.metropolia.events4me.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 * This model class holds information about event. This includes data about attendees, start and end
 * time and other relevant information that can be persisted in DB and also be registered on the Google Calendar.
 */
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer eventId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;
    private String title;
    private String description;

    @ManyToOne
    private User organizer;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private Interest category;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<User> confirmedAttendees;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<User> pendingAttendees;

    public Event() {
        this.confirmedAttendees = new HashSet<>();
        this.pendingAttendees = new HashSet<>();
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Set<User> getPendingAttendees() {
        return pendingAttendees;
    }

    public void setPendingAttendees(Set<User> pendingAttendees) {
        this.pendingAttendees = pendingAttendees;
    }

    public void acceptAttendee(User attendee) {
        if (pendingAttendees.contains(attendee)) {
            pendingAttendees.remove(attendee);
            this.confirmedAttendees.add(attendee);
            attendee.attendEvent(this);
        }
    }

    public void acceptEnrollment(User attendee) {
        this.pendingAttendees.add(attendee);
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public User getOrganizer() {
        return organizer;
    }

    public void setOrganizer(User organizer) {
        this.organizer = organizer;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Interest getCategory() {
        return category;
    }

    public void setCategory(Interest category) {
        this.category = category;
    }

    public Set<User> getConfirmedAttendees() {
        return confirmedAttendees;
    }

    public void setConfirmedAttendees(Set<User> confirmedAttendees) {
        this.confirmedAttendees = confirmedAttendees;
    }

}
