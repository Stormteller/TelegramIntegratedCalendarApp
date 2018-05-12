package com.univ.event_manager.service;

import com.univ.event_manager.data.dto.output.InvitationResponse;

import java.util.List;

public interface InvitationService {
    InvitationResponse create(List<Long> userIds, long eventId, long creatorId);
    void accept(long invitationId, long userId);
    void reject(long invitationId, long userId);
    List<InvitationResponse> getByUser(long userId);
}
