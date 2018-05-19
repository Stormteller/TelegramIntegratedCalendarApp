package com.univ.event_manager.data.converter;

import com.univ.event_manager.data.dto.output.EventParticipantResponse;
import com.univ.event_manager.data.dto.output.UserResponse;
import com.univ.event_manager.data.entity.EventParticipant;
import com.univ.event_manager.data.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EventParticipantToDtoConverter implements Converter<EventParticipant, EventParticipantResponse> {
    private final Converter<User, UserResponse> userConverter;

    @Autowired
    public EventParticipantToDtoConverter(Converter<User, UserResponse> userConverter) {
        this.userConverter = userConverter;
    }

    @Override
    public EventParticipantResponse convert(EventParticipant eventParticipant) {
        return EventParticipantResponse.builder()
                .id(eventParticipant.getId())
                .eventId(eventParticipant.getEventId())
                .user(userConverter.convert(eventParticipant.getUser()))
                .build();
    }
}
