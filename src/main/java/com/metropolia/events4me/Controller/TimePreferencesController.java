package com.metropolia.events4me.Controller;

import com.metropolia.events4me.converter.TimeSettingConverter;
import com.metropolia.events4me.Model.Days;
import com.metropolia.events4me.Model.TimeSetting;
import com.metropolia.events4me.Model.User;
import com.metropolia.events4me.Service.TimeSettingService;
import com.metropolia.events4me.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class TimePreferencesController {

    @Autowired
    private TimeSettingService timeSettingService;

    @Autowired
    private UserService userService;

    // Method shows the form for setting up time preferences
    @RequestMapping(value = "/settimepref", method = RequestMethod.GET)
    public String setTime(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        TimeSetting timeSetting = user.getTimeAvailability();
        TimeSettingConverter periodsWrapper = TimeSettingConverter.convertForTemplate(timeSetting);
        model.addAttribute("periodsWrapper", periodsWrapper);
        model.addAttribute("settingsId", timeSetting.getId());
        model.addAttribute("days", Days.values());
        return "timepref";
    }

    // Updates user's time preferences in database
    @RequestMapping(value = "/setsettings", method = RequestMethod.POST)
    public ResponseEntity<?> updateTime(@ModelAttribute("periodsWrapper") TimeSettingConverter periodsWrapper, @ModelAttribute("settingsId") int id) {
        TimeSetting timeSetting = timeSettingService.getTimeSettingById(id);
        TimeSetting converted = TimeSettingConverter.convertForDatabase(periodsWrapper);
        timeSetting.setTimeMap(converted.getTimeMap());
        timeSettingService.saveOrUpdate(timeSetting);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Returns JSON object with time preferences
    @RequestMapping(value = "/show/timesettings", method = RequestMethod.GET)
    public ResponseEntity<?> lisTimeSettings(Principal principal) {
        TimeSetting timeSetting = userService.findByUsername(principal.getName()).getTimeAvailability();
        TimeSettingConverter periodsWrapper = TimeSettingConverter.convertForTemplate(timeSetting);
        return new ResponseEntity<>(periodsWrapper.getPeriodsList(), HttpStatus.OK);
    }
}
