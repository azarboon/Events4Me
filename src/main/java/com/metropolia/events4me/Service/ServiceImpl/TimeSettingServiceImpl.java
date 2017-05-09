package com.metropolia.events4me.Service.ServiceImpl;

import com.metropolia.events4me.DAO.TimeSettingDAO;
import com.metropolia.events4me.Model.Days;
import com.metropolia.events4me.Model.TimeSetting;
import com.metropolia.events4me.Model.User;
import com.metropolia.events4me.Service.TimeSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Dmitry on 29.04.2017.
 */
@Service
public class TimeSettingServiceImpl implements TimeSettingService {

    private TimeSettingDAO timeSettingDAO;

    @Autowired
    public void setTimeSettingDAO(TimeSettingDAO timeSettingDAO) {
        this.timeSettingDAO = timeSettingDAO;
    }

    @Override
    public TimeSetting getTimeSettingById(Integer id) {
        return timeSettingDAO.findOne(id);
    }

    @Override
    public TimeSetting saveOrUpdate(TimeSetting timeSetting) {
        return timeSettingDAO.save(timeSetting);
    }

}
