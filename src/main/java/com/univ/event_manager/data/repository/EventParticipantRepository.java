package com.univ.event_manager.data.repository;

import com.univ.event_manager.data.entity.EventParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventParticipantRepository extends JpaRepository<EventParticipant, Long> {
}
