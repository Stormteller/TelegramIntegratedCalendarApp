package com.univ.event_manager.service.impl;

import com.univ.event_manager.data.dto.output.InvitationResponse;
import com.univ.event_manager.data.entity.Event;
import com.univ.event_manager.data.entity.Invitation;
import com.univ.event_manager.data.entity.User;
import com.univ.event_manager.data.entity.enums.InvitationStatus;
import com.univ.event_manager.data.exception.NotFoundException;
import com.univ.event_manager.data.exception.UnauthorizedException;
import com.univ.event_manager.data.repository.EventRepository;
import com.univ.event_manager.data.repository.InvitationRepository;
import com.univ.event_manager.data.repository.UserRepository;
import com.univ.event_manager.service.EventParticipantService;
import com.univ.event_manager.service.InvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    private final EventParticipantService eventParticipantService;

    private final Converter<Invitation, InvitationResponse> invitationConverter;

    @Autowired
    public InvitationServiceImpl(InvitationRepository invitationRepository,
                                 UserRepository userRepository,
                                 EventRepository eventRepository,
                                 EventParticipantService eventParticipantService,
                                 Converter<Invitation, InvitationResponse> invitationConverter) {
        this.invitationRepository = invitationRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.eventParticipantService = eventParticipantService;
        this.invitationConverter = invitationConverter;
    }

    @Override
    @Transactional
    public List<InvitationResponse> create(List<Long> userIds, long eventId, long creatorId) {

        User sender = userRepository.findById(creatorId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found"));

        List<Invitation> invitations = userIds.stream()
                .map(userId ->
                        Invitation.builder()
                                .event(event)
                                .receiverId(userId)
                                .sender(sender)
                                .build()
                ).collect(Collectors.toList());

        invitations = invitationRepository.saveAll(invitations);

        return invitations.stream().map(invitationConverter::convert).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void accept(long invitationId, long userId) {

        Invitation invitation = authenticatedGetInvitationById(invitationId, userId);

        User invitationReceiver = userRepository.findById(invitation.getReceiverId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        invitation.setStatus(InvitationStatus.ACCEPTED);

        eventParticipantService.create(invitation.getEvent().getId(), invitationReceiver);
    }

    @Override
    @Transactional
    public void reject(long invitationId, long userId) {
        Invitation invitation = authenticatedGetInvitationById(invitationId, userId);

        invitation.setStatus(InvitationStatus.DECLINED);
    }

    private Invitation authenticatedGetInvitationById(long invitationId, long userId) {
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new NotFoundException("Invitation not found"));

        if (invitation.getReceiverId() != userId) {
            throw new UnauthorizedException("Action is not permitted");
        }

        return invitation;
    }

    @Override
    @Transactional
    public Page<InvitationResponse> getByUser(long userId, Pageable pageable) {
        return invitationRepository.findByReceiverIdAndStatus(userId, InvitationStatus.NEW, pageable).map(invitationConverter::convert);
    }

    @Override
    @Transactional
    public InvitationResponse getById(long id, long userId) {
        Invitation invitation = authenticatedGetInvitationById(id, userId);

        return invitationConverter.convert(invitation);
    }
}
