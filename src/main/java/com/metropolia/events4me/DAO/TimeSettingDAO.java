package com.metropolia.events4me.DAO;

import com.metropolia.events4me.Model.TimeSetting;
import org.springframework.data.repository.CrudRepository;

public interface TimeSettingDAO extends CrudRepository<TimeSetting, Integer> {
}
