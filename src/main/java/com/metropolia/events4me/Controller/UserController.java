package com.metropolia.events4me.Controller;

import com.metropolia.events4me.Model.User;
import com.metropolia.events4me.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

/**
 * Created by Dmitry on 11.04.2017.
 */

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "profile";
    }

//    @RequestMapping(value = "/profile", method = RequestMethod.POST)
//    public String profilePost(@ModelAttribute("user") User newUser, Model model) {
//        User user = userService.findByUsername(newUser.getUsername());
//        user.setUsername(newUser.getUsername());
//        user.setFirstName(newUser.getFirstName());
//        user.setLastName(newUser.getLastName());
//        user.setEmail(newUser.getEmail());
//        user.setCountry(newUser.getCountry());
//        user.setBirthday(newUser.getBirthday());
//        user.setInterests(newUser.getInterests());
//        model.addAttribute("user", user);
//        userService.saveOrUpdateUser(user);
//        return "profile";
//    }


}
