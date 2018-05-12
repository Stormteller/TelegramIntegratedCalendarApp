package com.univ.event_manager.service;

import com.univ.event_manager.data.dto.input.CreateEventInput;
import com.univ.event_manager.data.dto.input.FilterEventsInput;
import com.univ.event_manager.data.dto.input.UpdateEventInput;
import com.univ.event_manager.data.dto.output.EventResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface EventService {
    EventResponse create(CreateEventInput input, long creatorId);
    List<EventResponse> getEventByFilter(FilterEventsInput input, long userId);
    EventResponse getEventById(long eventId, long userId);
    EventResponse update(long eventId, UpdateEventInput input);
    void delete(long eventId);
}
