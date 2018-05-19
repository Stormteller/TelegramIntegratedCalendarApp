package com.univ.event_manager.data.repository;

import com.univ.event_manager.data.entity.ToDoList;
import com.univ.event_manager.data.entity.enums.ToDoListType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ToDoListRepository extends JpaRepository<ToDoList, Long> {
    List<ToDoList> findByTypeAndForDayAndCreatorId(ToDoListType type, LocalDate date, long creatorId);
    List<ToDoList> findByTypeAndEventIdAndCreatorId(ToDoListType type, long eventId, long creatorId);
    List<ToDoList> findByTypeAndCreatorId(ToDoListType type, long creatorId);
}
