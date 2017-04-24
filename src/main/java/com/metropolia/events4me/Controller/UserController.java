package com.metropolia.events4me.Controller;

import com.metropolia.events4me.Model.Event;
import com.metropolia.events4me.Model.User;
import com.metropolia.events4me.Service.UserService;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("/user")
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
