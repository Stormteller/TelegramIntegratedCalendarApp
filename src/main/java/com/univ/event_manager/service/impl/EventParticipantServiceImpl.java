package com.univ.event_manager.service.impl;

import com.univ.event_manager.data.dto.output.EventParticipantResponse;
import com.univ.event_manager.data.entity.Event;
import com.univ.event_manager.data.entity.EventParticipant;
import com.univ.event_manager.data.entity.User;
import com.univ.event_manager.data.exception.NotFoundException;
import com.univ.event_manager.data.exception.UnauthorizedException;
import com.univ.event_manager.data.repository.EventParticipantRepository;
import com.univ.event_manager.data.repository.EventRepository;
import com.univ.event_manager.service.EventParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventParticipantServiceImpl implements EventParticipantService {
    private final EventParticipantRepository eventParticipantRepository;
    private final EventRepository eventRepository;

    private final Converter<EventParticipant, EventParticipantResponse> eventParticipantConverter;

    @Autowired
    public EventParticipantServiceImpl(EventParticipantRepository eventParticipantRepository,
                                       EventRepository eventRepository,
                                       Converter<EventParticipant, EventParticipantResponse> eventParticipantConverter) {
        this.eventParticipantRepository = eventParticipantRepository;
        this.eventRepository = eventRepository;
        this.eventParticipantConverter = eventParticipantConverter;
    }

    @Override
    public List<EventParticipantResponse> getByEvent(long eventId, long userId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found"));

        List<EventParticipant> eventParticipants = eventParticipantRepository.findByEventId(eventId);

        boolean isUserInParticipants = eventParticipants.stream()
                .anyMatch(participant -> participant.getUser().getId() == userId);

        if(!event.isPublic() && !isUserInParticipants) {
            throw new UnauthorizedException("You cannot see participant of not public event");
        }

        return eventParticipants.stream()
                .map(eventParticipantConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public EventParticipantResponse create(long eventId, User user) {
        EventParticipant eventParticipant = EventParticipant.builder()
                .eventId(eventId)
                .user(user)
                .build();

        eventParticipant = eventParticipantRepository.save(eventParticipant);

        return eventParticipantConverter.convert(eventParticipant);
    }


}
