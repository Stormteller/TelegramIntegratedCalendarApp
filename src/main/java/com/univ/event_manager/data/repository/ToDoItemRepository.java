package com.univ.event_manager.data.repository;

import com.univ.event_manager.data.entity.ToDoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoItemRepository extends JpaRepository<ToDoItem, Long> {
}
