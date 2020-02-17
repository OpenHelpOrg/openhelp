package com.capstone.openhelp.repositories;

import com.capstone.openhelp.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface EventRepository extends JpaRepository<Event, Long> {
    Event findById(long id);
    Event deleteById(long id);
    List<Event>  findByTitleContainsOrSummaryContainsAllIgnoreCase(String title, String summary);
}
