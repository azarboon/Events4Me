package com.metropolia.events4me.Service;

import com.metropolia.events4me.Model.Location;
import java.util.List;


public interface LocationService {

  Location saveOrUpdateLocation(Location location);

  List<Location> listLocations();

  Location findByName(String name);

  Location findById(int id);

  Location findOne(int id);

}
