package com.univ.event_manager.service.impl;

import com.univ.event_manager.data.dto.input.CreateToDoListInput;
import com.univ.event_manager.data.dto.input.ToDoListFilterInput;
import com.univ.event_manager.data.dto.input.UpdateToDoListInput;
import com.univ.event_manager.data.dto.output.ToDoListResponse;
import com.univ.event_manager.data.entity.ToDoList;
import com.univ.event_manager.data.entity.enums.ToDoListType;
import com.univ.event_manager.data.exception.BadRequestException;
import com.univ.event_manager.data.exception.NotFoundException;
import com.univ.event_manager.data.exception.UnauthorizedException;
import com.univ.event_manager.data.repository.ToDoListRepository;
import com.univ.event_manager.service.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ToDoListServiceImpl implements ToDoListService {
    private final ToDoListRepository toDoItemRepository;

    private final Converter<ToDoList, ToDoListResponse> toDoListConverter;

    @Autowired
    public ToDoListServiceImpl(ToDoListRepository toDoItemRepository,
                               Converter<ToDoList, ToDoListResponse> toDoListConverter) {
        this.toDoItemRepository = toDoItemRepository;
        this.toDoListConverter = toDoListConverter;
    }

    @Override
    @Transactional
    public ToDoListResponse create(CreateToDoListInput input, long creatorId) {
        ToDoList toDoList = ToDoList.builder()
                .forDay(input.getForDay())
                .name(input.getName())
                .type(input.getType())
                .creatorId(creatorId)
                .eventId(input.getEventId())
                .build();

        toDoList = toDoItemRepository.save(toDoList);

        return toDoListConverter.convert(toDoList);
    }


    //TODO: Refactor to query with Specification
    @Override
    @Transactional
    public List<ToDoListResponse> getByFilter(ToDoListFilterInput input, long userId) {

        List<ToDoList> toDoLists;

        switch (input.getType()){
            case DAILY:
                toDoLists = toDoItemRepository.findByTypeAndForDayAndCreatorId(ToDoListType.DAILY, input.getDate(), userId);
                break;
            case EVENT_BASED:
                toDoLists = toDoItemRepository.findByTypeAndEventIdAndCreatorId(ToDoListType.EVENT_BASED, input.getEventId(), userId);
                break;
            case GLOBAL:
                toDoLists = toDoItemRepository.findByTypeAndCreatorId(ToDoListType.GLOBAL, userId);
                    break;
            default:
                throw new BadRequestException("Unsupported to-do-list type");
        }

        return toDoLists.stream().map(toDoListConverter::convert).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ToDoListResponse getById(long id, long userId) {
        ToDoList toDoList =
                toDoItemRepository.findById(id).orElseThrow(() -> new NotFoundException("To-do-list not found"));

        return toDoListConverter.convert(toDoList);
    }

    @Override
    @Transactional
    public ToDoListResponse update(long id, UpdateToDoListInput input, long userId) {
        ToDoList toDoList =
                toDoItemRepository.findById(id).orElseThrow(() -> new NotFoundException("To-do-list not found"));

        checkRights(toDoList, userId);

        toDoList.setForDay(input.getForDay());
        toDoList.setName(input.getName());
        toDoList.setEventId(input.getEventId());
        toDoList.setType(input.getType());

        return toDoListConverter.convert(toDoList);
    }

    @Override
    @Transactional
    public void delete(long id, long userId) {
        ToDoList toDoList =
                toDoItemRepository.findById(id).orElseThrow(() -> new NotFoundException("To-do-list not found"));

        checkRights(toDoList, userId);

        toDoItemRepository.delete(toDoList);
    }

    private void checkRights(ToDoList toDoList, long userId) {
        if(toDoList.getCreatorId() != userId) {
            throw new UnauthorizedException("Access denied");
        }
    }
}
