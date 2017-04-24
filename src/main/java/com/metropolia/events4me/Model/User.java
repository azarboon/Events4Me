package com.metropolia.events4me.Model;

import com.metropolia.events4me.Model.security.Role;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Dmitry on 11.04.2017.
 */

@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer userId;


  @Size(min=3, message = "Firstname should have minimum 3 characters.")
  @NotNull
  private String firstName;

  @Size(min=3, message = "Last name should have minimum 3 characters.")
  @NotNull
  private String lastName;

  @Size(min=3, message = "Username should have minimum 3 characters.")
  @Column(unique = true)
  @NotNull
  private String username;


  @Column(unique = true)
  @NotNull
  private String email;

  @Transient
  @NotNull
  private String password;
  private String encryptedPassword;


  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable
  // ~ defaults to @JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "user_id"),
  //     inverseJoinColumns = @joinColumn(name = "role_id"))
  private List<Role> roles = new ArrayList<>();
  private String birthday;
  private String country;
  private Boolean enabled = true;

  @Enumerated(EnumType.STRING)
  @ElementCollection(targetClass = Interest.class, fetch = FetchType.EAGER)
  private Set<Interest> interests = new HashSet<>();

  @ManyToMany
  private List<Event> events;
  private byte[] photo;


  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Set<User> friends = new HashSet<User>();


  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Set<User> pendingFriendRequests = new HashSet<User>();

  public void acceptFriend(User sender){
    if((sender != null) && pendingFriendRequests.contains(sender)){
      pendingFriendRequests.remove(sender);
      this.friends.add(sender);
    }
  }


  public User() {

    }



  public void recieveFriendRequestFrom(User user){
    this.pendingFriendRequests.add(user);
  }

  public Set<User> getFriends() {
    return friends;
  }

  public void setFriends(Set<User> friends) {
    this.friends = friends; }



  public Set<User> getPendingFriendRequests() {
    return pendingFriendRequests;
  }
  public void sendFriendRequestTo(User user){
    user.recieveFriendRequestFrom(this);
  }



  public Integer getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEncryptedPassword() {
    return encryptedPassword;
  }

  public void setEncryptedPassword(String encryptedPassword) {
    this.encryptedPassword = encryptedPassword;
  }

  public List<Role> getRoles() {
    return roles;
  }

  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }

  public void addRole(Role role) {
    if (!this.roles.contains(role)) {
      this.roles.add(role);
    }

    if (!role.getUsers().contains(this)) {
      role.getUsers().add(this);
    }
  }

  public void removeRole(Role role) {
    this.roles.remove(role);
    role.getUsers().remove(this);
  }

  public String getBirthday() {
    return birthday;
  }

  public void setBirthday(String birthday) {
    this.birthday = birthday;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public Set<Interest> getInterests() {
    return interests;
  }

  public void setInterests(Set<Interest> interests) {
    this.interests = interests;
  }

  public byte[] getPhoto() {
    return photo;
  }

  public void setPhoto(byte[] photo) {
    this.photo = photo;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public List<Event> getEvents() {
    return events;
  }

  public void setEvents(List<Event> events) {
    this.events = events;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }


}
