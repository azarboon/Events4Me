package com.metropolia.events4me.DAO;

import com.metropolia.events4me.Model.Event;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventDAO extends CrudRepository<Event, Integer> {

    List<Event> findAll(Sort sort);
}
