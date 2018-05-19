package com.univ.event_manager.service;

import com.univ.event_manager.data.dto.output.InvitationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InvitationService {
    List<InvitationResponse> create(List<Long> userIds, long eventId, long creatorId);
    void accept(long invitationId, long userId);
    void reject(long invitationId, long userId);
    Page<InvitationResponse> getByUser(long userId, Pageable pageable);
    InvitationResponse getById(long id, long userId);
}
