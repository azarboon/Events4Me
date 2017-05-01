package com.metropolia.events4me.Model;

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
  @Enumerated(EnumType.STRING)
  private Location location;
  private String title;
  private String description;


  @ManyToOne
  private User organizer;
  private LocalDateTime dateTime;

  @Enumerated(EnumType.STRING)
  private Interest category;
  private int maxAttenddees;
  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Set<User> confirmedAttendees;
  @ManyToMany(cascade = CascadeType.ALL)
  private Set<User> pendingAttendees;

  private BigDecimal price;


  public Event() {
    this.confirmedAttendees = new HashSet<>();
    this.pendingAttendees = new HashSet<>();
  }


  public void acceptAttendee(User attendee) {
    if(pendingAttendees.contains(attendee)){
      pendingAttendees.remove(attendee);
      this.confirmedAttendees.add(attendee);
      attendee.attendEvent(this);
    }
  }

  public void acceptEnrollment(User attendee){
    this.pendingAttendees.add(attendee);
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

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
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

  public void setEventId(int eventId) {
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
