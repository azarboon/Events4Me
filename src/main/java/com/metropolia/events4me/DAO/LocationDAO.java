package com.metropolia.events4me.DAO;

import com.metropolia.events4me.Model.Location;
import java.util.List;
import org.springframework.data.repository.CrudRepository;


public interface LocationDAO extends CrudRepository<Location, Integer> {

  List<Location> findAll();
  Location findByName(String name);


}
