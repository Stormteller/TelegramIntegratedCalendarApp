package com.univ.event_manager.service;

import com.univ.event_manager.data.dto.input.CreateToDoListInput;
import com.univ.event_manager.data.dto.input.ToDoListFilterInput;
import com.univ.event_manager.data.dto.output.ToDoListResponse;

import java.util.List;


public interface ToDoListService {
    ToDoListResponse create(CreateToDoListInput input, long creatorId);

    List<ToDoListResponse> getByFilter(ToDoListFilterInput input, long userId);

    ToDoListResponse getById(long id, long userId);
}
