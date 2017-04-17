package com.metropolia.events4me.Controller;

import com.metropolia.events4me.DAO.RoleDAO;
import com.metropolia.events4me.Model.User;
import com.metropolia.events4me.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Dmitry on 16.04.2017.
 */
@Controller
public class IndexController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    // Redirects to index page for login/sign-up
    @RequestMapping("/")
    public String home() {
        return "redirect:/login";
    }

    // Login page
    @RequestMapping("/login")
    public String index() {
        return "login";
    }

    // Sign-up initial page
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "signup";
    }

    // Sign-up request
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signupPost(@ModelAttribute("user") User user, Model model) {

        if(userService.checkUserExists(user.getUsername(), user.getEmail()))  {
            if (userService.checkEmailExists(user.getEmail())) {
                model.addAttribute("emailExists", true);
            }
            if (userService.checkUsernameExists(user.getUsername())) {
                model.addAttribute("usernameExists", true);
            }
            return "signup";
        } else {
            userService.saveOrUpdateUser(user);
            return "redirect:/";
        }
    }

    // User home page after login
    @RequestMapping("/events4me")
    public String userFront(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
//        some logic will be there
        return "events4me";
    }
}
