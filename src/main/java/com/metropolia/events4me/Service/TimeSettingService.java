package com.metropolia.events4me.Service;

import com.metropolia.events4me.Model.TimeSetting;

public interface TimeSettingService {

    TimeSetting getTimeSettingById(Integer id);

    TimeSetting saveOrUpdate(TimeSetting timeSetting);

}
