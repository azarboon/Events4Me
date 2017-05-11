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
Model class for holding information about locations. Each location has an associated calendar on Google Calendar API.
 */

@Entity
public class Location {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  private String name;
  private String address;

  @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
  private Set<Event> events;
  private String calendarID;


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
