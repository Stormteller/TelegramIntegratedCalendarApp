package com.univ.event_manager.service;

import com.univ.event_manager.data.dto.input.UpdateToDoItemInput;
import com.univ.event_manager.data.dto.output.ToDoItemResponse;


public interface ToDoItemService {
    ToDoItemResponse create(long listId, String text, long creatorId);

    void delete(long id);

    ToDoItemResponse update(long id, UpdateToDoItemInput input);

    ToDoItemResponse getById(long id);
}
