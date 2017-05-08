package com.metropolia.events4me;

import com.metropolia.events4me.Model.Location;
import com.metropolia.events4me.Service.EventService;
import com.metropolia.events4me.Service.LocationService;
import com.metropolia.events4me.Service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import sun.util.locale.LocaleObjectCache;

/**
 * Created by mahdiaza on 08/05/17.
 */
public class LocationServiceImplIT {

  @Autowired
  private LocationService locationService;

  @Autowired
  public void setLocationService(LocationService locationService){
    this.locationService = locationService;
  }


  public LocationServiceImplIT() {

  }

  @Test
  public void getAllLocaitonsTest(){



  }


}
