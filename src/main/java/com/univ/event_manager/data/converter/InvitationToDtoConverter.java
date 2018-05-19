package com.univ.event_manager.data.converter;

import com.univ.event_manager.data.dto.output.EventResponse;
import com.univ.event_manager.data.dto.output.InvitationResponse;
import com.univ.event_manager.data.dto.output.UserResponse;
import com.univ.event_manager.data.entity.Event;
import com.univ.event_manager.data.entity.Invitation;
import com.univ.event_manager.data.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class InvitationToDtoConverter implements Converter<Invitation, InvitationResponse> {

    private final Converter<Event, EventResponse> eventConverter;
    private final Converter<User, UserResponse> userConverter;

    @Autowired
    public InvitationToDtoConverter(Converter<Event, EventResponse> eventConverter,
                                    Converter<User, UserResponse> userConverter) {
        this.eventConverter = eventConverter;
        this.userConverter = userConverter;
    }

    @Override
    public InvitationResponse convert(Invitation invitation) {
        return InvitationResponse.builder()
                .id(invitation.getId())
                .event(eventConverter.convert(invitation.getEvent()))
                .receiverId(invitation.getReceiverId())
                .sender(userConverter.convert(invitation.getSender()))
                .status(invitation.getStatus())
                .createdAt(invitation.getCreatedAt())
                .build();
    }
}
