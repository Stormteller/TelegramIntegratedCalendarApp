package com.univ.event_manager.data.repository;

import com.univ.event_manager.data.entity.EventParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventParticipantRepository extends JpaRepository<EventParticipant, Long> {
    //TODO: Add entity graph for select by join
    List<EventParticipant> findByEventId(long eventId);
}
