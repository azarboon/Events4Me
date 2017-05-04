package com.metropolia.events4me.Model.security;

import com.metropolia.events4me.Model.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer roleId;

    private String role;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable
    private List<User> users = new ArrayList<>();

    public Role() {
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUser(User user){
        if(!this.users.contains(user)){
            this.users.add(user);
        }

        if(!user.getRoles().contains(this)){
            user.getRoles().add(this);
        }
    }

    public void removeUser(User user){
        this.users.remove(user);
        user.getRoles().remove(this);
    }
}
