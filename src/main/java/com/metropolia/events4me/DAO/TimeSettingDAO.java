package com.metropolia.events4me.DAO;

import com.metropolia.events4me.Model.TimeSetting;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Dmitry on 29.04.2017.
 */

public interface TimeSettingDAO extends CrudRepository<TimeSetting, Integer> {
}
