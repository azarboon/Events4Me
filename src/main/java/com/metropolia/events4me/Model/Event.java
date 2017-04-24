package com.metropolia.events4me.Model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;



@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer eventId;

    private String name;
    private String place;
    private String description;
    private byte[] photo;
    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    private Interest category;

    @ManyToMany
    private Set<User> participants;

    public Event() {
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Interest getCategory() {
        return category;
    }

    public void setCategory(Interest category) {
        this.category = category;
    }

    public Set<User> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<User> participants) {
        this.participants = participants;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
