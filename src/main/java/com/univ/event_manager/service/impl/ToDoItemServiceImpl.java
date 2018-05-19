package com.univ.event_manager.service.impl;

import com.univ.event_manager.data.dto.input.UpdateToDoItemInput;
import com.univ.event_manager.data.dto.output.ToDoItemResponse;
import com.univ.event_manager.data.entity.ToDoItem;
import com.univ.event_manager.data.exception.NotFoundException;
import com.univ.event_manager.data.repository.ToDoItemRepository;
import com.univ.event_manager.service.ToDoItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


//TODO: add authorization to method

@Service
public class ToDoItemServiceImpl implements ToDoItemService {
    private final ToDoItemRepository toDoItemRepository;

    private final Converter<ToDoItem, ToDoItemResponse> toDoItemConverter;

    @Autowired
    public ToDoItemServiceImpl(ToDoItemRepository toDoItemRepository,
                               Converter<ToDoItem, ToDoItemResponse> toDoItemConverter) {
        this.toDoItemRepository = toDoItemRepository;
        this.toDoItemConverter = toDoItemConverter;
    }

    @Override
    @Transactional
    public ToDoItemResponse create(long listId, String text, long creatorId) {
        ToDoItem toDoItem = ToDoItem.builder()
                .text(text)
                .toDoListId(listId)
                .build();

        toDoItem = toDoItemRepository.save(toDoItem);

        return toDoItemConverter.convert(toDoItem);
    }

    @Override
    @Transactional
    public void delete(long id) {
        toDoItemRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ToDoItemResponse update(long id, UpdateToDoItemInput input) {
        ToDoItem toDoItem = getItemById(id);

        toDoItem.setText(input.getText());
        toDoItem.setDone(input.isDone());

        return toDoItemConverter.convert(toDoItem);
    }

    @Override
    @Transactional
    public ToDoItemResponse getById(long id) {
        ToDoItem toDoItem = getItemById(id);
        return toDoItemConverter.convert(toDoItem);
    }

    private ToDoItem getItemById(long id) {
        return toDoItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("To-do-item not found"));
    }

}
