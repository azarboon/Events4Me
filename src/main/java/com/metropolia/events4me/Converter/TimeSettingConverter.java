package com.metropolia.events4me.converter;

import com.metropolia.events4me.Model.Days;
import com.metropolia.events4me.Model.TimeSetting;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Dmitry on 30.04.2017.
 */
@Component
public class TimeSettingConverter {

    private ArrayList<Period> periodsList;

    public TimeSettingConverter() {
        this.periodsList = new ArrayList<>();
    }

    public ArrayList<Period> getPeriodsList() {
        return periodsList;
    }

    public void setPeriodsList(ArrayList<Period> periodsList) {
        this.periodsList = periodsList;
    }

    public static TimeSettingConverter convertForTemplate(TimeSetting timeSetting) {

        TimeSettingConverter periodsWrapper = new TimeSettingConverter();
        Map<Days, String> timeMap = timeSetting.getTimeMap();

        for (Days day : Days.values()) {
            String startEnd = timeMap.get(day);
            String[] startEndArray = startEnd.split(";");
            LocalTime start = LocalTime.parse(startEndArray[0]);
            LocalTime end = LocalTime.parse(startEndArray[1]);
            periodsWrapper.getPeriodsList().add(new Period(start, end));
        }

        return periodsWrapper;
    }
}
