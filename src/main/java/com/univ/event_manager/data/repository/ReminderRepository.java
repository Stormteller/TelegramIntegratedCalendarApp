package com.univ.event_manager.data.repository;

import com.univ.event_manager.data.entity.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    List<Reminder> findByRemindingTimeBeforeAndSent(LocalDateTime remindingTime, boolean sent);
}
