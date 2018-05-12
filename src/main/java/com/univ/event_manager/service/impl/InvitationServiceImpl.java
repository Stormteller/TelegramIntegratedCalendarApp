package com.univ.event_manager.service.impl;

import com.univ.event_manager.data.dto.output.InvitationResponse;
import com.univ.event_manager.service.InvitationService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InvitationServiceImpl implements InvitationService {
    @Override
    public InvitationResponse create(List<Long> userIds, long eventId, long creatorId) {
        return null;
    }

    @Override
    public void accept(long invitationId, long userId) {

    }

    @Override
    public void reject(long invitationId, long userId) {

    }

    @Override
    public List<InvitationResponse> getByUser(long userId) {
        return null;
    }
}
