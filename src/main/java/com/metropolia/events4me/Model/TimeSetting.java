package com.metropolia.events4me.Model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dmitry on 29.04.2017.
 */

@Entity
public class TimeSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ElementCollection
    @MapKeyColumn(name="day")
//    @Column(name="interval")
    @CollectionTable(name="day_interval", joinColumns=@JoinColumn(name="time_settings_id"))
    private Map<Days, String> timeMap;

//    @Column
//    @ElementCollection(targetClass=String.class)
//    private List<String> timeList;

    public TimeSetting() {
        this.timeMap = new HashMap<Days, String>();
//        this.timeList = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

//    public List<String> getTimeList() {
//        return timeList;
//    }
//
//    public void setTimeList(List<String> timeList) {
//        this.timeList = timeList;
//    }

    public Map<Days, String> getTimeMap() {
        return timeMap;
    }

    public void setTimeMap(Map<Days, String> timeMap) {
        this.timeMap = timeMap;
    }
}
