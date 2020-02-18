package com.capstone.openhelp.repositories;

import com.capstone.openhelp.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface EventRepository extends JpaRepository<Event, Long> {
    Event findById(long id);
    Event deleteById(long id);

    List<Event> findByTitleContainsOrSummaryContainsAllIgnoreCase(String title, String summary);

    @Query(value = "select * from events where date_time < CURRENT_DATE() order by date_time desc", nativeQuery = true)
    List<Event> findPastEvents();

    @Query(value = "select * from events where date_time >= CURRENT_DATE() order by date_time asc", nativeQuery = true)
    List<Event> findFutureEvents();
}