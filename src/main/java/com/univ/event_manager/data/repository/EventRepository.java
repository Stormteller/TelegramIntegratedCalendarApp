package com.univ.event_manager.data.repository;

import com.univ.event_manager.data.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findById(long id);

    @Query("SELECT e FROM event_participants p JOIN event e ON e.id=p.event_id WHERE p.user_id=?1 " +
            "AND (e.start_at BETWEEN ?2 AND ?3 OR e.finish_at BETWEEN ?2 AND ?3)")
    List<Event> findByUserInRange(long userId, LocalDateTime from, LocalDateTime to);
}
