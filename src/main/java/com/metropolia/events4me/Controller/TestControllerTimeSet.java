package com.metropolia.events4me.Controller;

import com.metropolia.events4me.Converter.Period;
import com.metropolia.events4me.Converter.TimeSettingConverter;
import com.metropolia.events4me.Model.Days;
import com.metropolia.events4me.Model.TimeSetting;
import com.metropolia.events4me.Model.User;
import com.metropolia.events4me.Service.TimeSettingService;
import com.metropolia.events4me.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Dmitry on 30.04.2017.
 */

@Controller
public class TestControllerTimeSet {

    @Autowired
    private TimeSettingService timeSettingService;

    @Autowired
    private UserService userService;

//    @RequestMapping(value = "/settimepref", method = RequestMethod.GET)
//    public String setTime(Principal principal, Model model) {
//        User user = userService.findByUsername(principal.getName());
//        TimeSetting timeSetting = user.getTimeAvailability();
//        model.addAttribute("user", user);
//        return "timepref";
//    }

    @RequestMapping(value = "/settimepref", method = RequestMethod.GET)
    public String setTime(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        TimeSetting timeSetting = user.getTimeAvailability();
        TimeSettingConverter periodsWrapper = TimeSettingConverter.convertForTemplate(timeSetting);
        model.addAttribute("periodsWrapper", periodsWrapper);
        model.addAttribute("settingsId", timeSetting.getId());
        return "timepref";
    }

//    @RequestMapping(value = "/setsettings", method = RequestMethod.POST)
//    public String updateTime(@RequestParam Map<String, Object> dayTimeSetting) {
//
//
//        return "myprofile";
//    }

    @RequestMapping(value = "/setsettings", method = RequestMethod.POST)
    @ResponseBody
    public String updateTime(@ModelAttribute("periodsWrapper") TimeSettingConverter periodsWrapper) {
        ArrayList<Period> periods = periodsWrapper.getPeriodsList();
        for (Period p : periods) {
            System.out.println(p.getStart());
            System.out.println(p.getEnd());
        }
        return "hello world";
    }
}
