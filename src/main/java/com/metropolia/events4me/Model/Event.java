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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;


@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer eventId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (location != event.location) return false;
        if (title != null ? !title.equals(event.title) : event.title != null) return false;
        if (description != null ? !description.equals(event.description) : event.description != null) return false;
        return organizer != null ? organizer.equals(event.organizer) : event.organizer == null;
    }

    @Override
    public int hashCode() {
        int result = location != null ? location.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (organizer != null ? organizer.hashCode() : 0);
        return result;
    }

    @Enumerated(EnumType.STRING)
    private Location location;
    private String title;
    private String description;


    @ManyToOne
    private User organizer;
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private Interest category;
    private int maxAttenddees;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<User> confirmedAttendees;
    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<User> pendingAttendees;

    private BigDecimal price;


    public Event() {
        this.confirmedAttendees = new HashSet<>();
        this.pendingAttendees = new HashSet<>();
    }


    public void acceptAttendee(User attendee) {
        if (pendingAttendees.contains(attendee)) {
            pendingAttendees.remove(attendee);
            if(!this.confirmedAttendees.contains(attendee)){
                this.confirmedAttendees.add(attendee);
            }
            attendee.attendEvent(this);
        }
    }

    public void acceptEnrollment(User attendee) {
        if(!this.pendingAttendees.contains(attendee)){
            this.pendingAttendees.add(attendee);
        }
    }


    public int getMaxAttenddees() {
        return maxAttenddees;
    }

    public void setMaxAttenddees(int maxAttenddees) {
        this.maxAttenddees = maxAttenddees;
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

    //NiklasTEST
    public String getEndTimeString() {
        return endTime.toString();
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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
