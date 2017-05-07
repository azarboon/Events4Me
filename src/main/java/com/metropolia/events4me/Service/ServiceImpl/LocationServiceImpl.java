package com.metropolia.events4me.Service.ServiceImpl;

import com.metropolia.events4me.DAO.LocationDAO;
import com.metropolia.events4me.Model.Location;
import com.metropolia.events4me.Service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by mahdiaza on 07/05/17.
 */
@Service
public class LocationServiceImpl implements LocationService{

  private LocationDAO locationDAO;

  @Autowired
  public void setLocationDAO(LocationDAO locationDAO){
    this.locationDAO = locationDAO;
  }

  @Override
  public Location saveOrUpdateLocation(Location location) {
    return locationDAO.save(location);
  }
}
