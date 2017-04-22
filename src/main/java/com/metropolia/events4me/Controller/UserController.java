package com.metropolia.events4me.Controller;

import com.metropolia.events4me.Model.User;
import com.metropolia.events4me.Service.UserService;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Dmitry on 11.04.2017.
 */

@Controller
public class UserController {


    private UserService userService;

    @Autowired
    @Qualifier("UserServiceImpl")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile(Principal principal, Model model) {

        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "profile";
    }

    @RequestMapping(value = "/recomUsers", method = RequestMethod.GET)
    public String getRecommendedUsers(Principal principal, Model model){
        List<User> recommendedUsers = userService.getUsersWithCommonInterest(principal.getName());
        model.addAttribute("recommendedUsers", recommendedUsers);
        return "recommendedUsers";
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
