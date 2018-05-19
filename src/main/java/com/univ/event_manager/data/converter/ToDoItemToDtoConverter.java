package com.univ.event_manager.data.converter;

import com.univ.event_manager.data.dto.output.ToDoItemResponse;
import com.univ.event_manager.data.entity.ToDoItem;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ToDoItemToDtoConverter implements Converter<ToDoItem, ToDoItemResponse> {
    @Override
    public ToDoItemResponse convert(ToDoItem toDoItem) {
        return ToDoItemResponse.builder()
                .id(toDoItem.getId())
                .text(toDoItem.getText())
                .isDone(toDoItem.isDone())
                .toDoListId(toDoItem.getToDoListId())
                .createdAt(toDoItem.getCreatedAt())
                .build();
    }
}
