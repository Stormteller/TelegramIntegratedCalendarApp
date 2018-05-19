package com.univ.event_manager.service;

import com.univ.event_manager.data.dto.output.EventParticipantResponse;
import com.univ.event_manager.data.entity.User;

import java.util.List;

public interface EventParticipantService {
    List<EventParticipantResponse> getByEvent(long eventId, long userId);

    EventParticipantResponse create(long eventId, User user);
}
