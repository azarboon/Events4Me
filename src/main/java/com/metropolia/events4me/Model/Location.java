package com.metropolia.events4me.Model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Created by mahdiaza on 05/05/17.
 */
@Entity
public class Location {

  private String name;
  private String address;
  //TODO: double check relationship with event
  @OneToMany(mappedBy = "location")
  private Set<Event> events;
  private String calendarID;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  public Location(String name, String address, String calendarID) {
    this.name = name;
    this.address = address;
    this.calendarID = calendarID;
    this.events = new HashSet<>();
  }

  public Location() {
    this.events = new HashSet<>();
  }

  public String getCalendarID() {
    return calendarID;
  }

  public void setCalendarID(String calendarID) {
    this.calendarID = calendarID;
  }

  public Set<Event> getEvents() {
    return events;
  }

  public void setEvents(Set<Event> events) {
    this.events = events;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }


}
