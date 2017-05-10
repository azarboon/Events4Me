package com.metropolia.events4me.Service.ServiceImpl;

import com.metropolia.events4me.DAO.LocationDAO;
import com.metropolia.events4me.Model.Location;
import com.metropolia.events4me.Service.LocationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

  @Override
  public List<Location> listLocations() {
    return locationDAO.findAll();
  }

  @Override
  public Location findByName(String name) {
    return locationDAO.findByName(name);
  }

  @Override
  public Location findById(int id) {
    return locationDAO.findOne(id);
  }

  @Override
  public Location findOne(int id) {
    return locationDAO.findOne(id);
  }


}
