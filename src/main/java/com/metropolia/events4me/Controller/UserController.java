package com.metropolia.events4me.Controller;

import com.metropolia.events4me.Model.Event;
import com.metropolia.events4me.Model.User;
import com.metropolia.events4me.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * Created by Dmitry on 11.04.2017.
 */

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping({"/list", "/"})
        public String listUsers(Model model){
        model.addAttribute("users", userService.listUsers());
        return "user/list";
    }

    @RequestMapping("/show/{id}")
    public String getUser(@PathVariable Integer id, Model model){
        model.addAttribute("user", userService.getById(id));
        return "user/show";
    }

    @RequestMapping("/edit")
    public String edit(Principal principal, Model model){
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user/profile";
    }

    @RequestMapping("/new")
    public String newUser(Model model){
        model.addAttribute("user", new User());
        return "user/profile";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String saveOrUpdate(User user){
        User savedUser = userService.saveOrUpdateUser(user);
        return "redirect:/user/show/" + savedUser.getUserId();
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        userService.delete(id);
        return "redirect:/user/list";
    }

    @RequestMapping("/userevents")
    public @ResponseBody
    List<Event> listUserEvents(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return userService.listUserEvents(user);
    }

}
