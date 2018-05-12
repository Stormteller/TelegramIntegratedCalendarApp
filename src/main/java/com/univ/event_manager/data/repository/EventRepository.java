package com.univ.event_manager.data.repository;

import com.univ.event_manager.data.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
