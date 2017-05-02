package com.metropolia.events4me.Model;


import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Model class for holding users' time availability settings.
 */

@Entity
public class TimeSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ElementCollection
    @MapKeyColumn(name="day")
    @CollectionTable(name="day_interval", joinColumns=@JoinColumn(name="time_settings_id"))
    private Map<Days, String> timeMap;

    public TimeSetting() {
        this.timeMap = new HashMap<Days, String>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Map<Days, String> getTimeMap() {
        return timeMap;
    }

    public void setTimeMap(Map<Days, String> timeMap) {
        this.timeMap = timeMap;
    }
}
