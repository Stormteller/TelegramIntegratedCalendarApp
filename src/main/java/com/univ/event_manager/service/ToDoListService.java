package com.univ.event_manager.service;

import com.univ.event_manager.data.dto.input.CreateToDoListInput;
import com.univ.event_manager.data.dto.input.ToDoListFilterInput;
import com.univ.event_manager.data.dto.input.UpdateToDoListInput;
import com.univ.event_manager.data.dto.output.ToDoListResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface ToDoListService {
    ToDoListResponse create(CreateToDoListInput input, long creatorId);

    List<ToDoListResponse> getByFilter(ToDoListFilterInput input, long userId);

    ToDoListResponse getById(long id, long userId);

    ToDoListResponse update(long id, UpdateToDoListInput input, long userId);

    @Transactional
    void delete(long id, long userId);
}
