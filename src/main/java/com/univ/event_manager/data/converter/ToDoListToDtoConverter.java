package com.univ.event_manager.data.converter;

import com.univ.event_manager.data.dto.output.ToDoItemResponse;
import com.univ.event_manager.data.dto.output.ToDoListResponse;
import com.univ.event_manager.data.entity.ToDoItem;
import com.univ.event_manager.data.entity.ToDoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ToDoListToDtoConverter implements Converter<ToDoList, ToDoListResponse> {
    private final Converter<ToDoItem, ToDoItemResponse> toDoItemConverter;

    @Autowired
    public ToDoListToDtoConverter(Converter<ToDoItem, ToDoItemResponse> toDoItemConverter) {
        this.toDoItemConverter = toDoItemConverter;
    }

    @Override
    public ToDoListResponse convert(ToDoList toDoList) {
        return ToDoListResponse.builder()
                .id(toDoList.getId())
                .name(toDoList.getName())
                .forDay(toDoList.getForDay())
                .eventId(toDoList.getEventId())
                .items(toDoList.getItems().stream()
                        .map(toDoItemConverter::convert)
                        .collect(Collectors.toList()))
                .type(toDoList.getType())
                .creatorId(toDoList.getCreatorId())
                .build();
    }
}
