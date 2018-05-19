package com.univ.event_manager.web;

import com.univ.event_manager.data.dto.output.EventParticipantResponse;
import com.univ.event_manager.data.dto.security.AuthorizedUserDetails;
import com.univ.event_manager.service.EventParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/event_participant")
public class EventParticipantController implements AuthenticatedController {
    private final EventParticipantService eventParticipantService;

    @Autowired
    public EventParticipantController(EventParticipantService eventParticipantService) {
        this.eventParticipantService = eventParticipantService;
    }

    @GetMapping("/event/{event_id}")
    public ResponseEntity<List<EventParticipantResponse>> eventParticipantsByEvent(
            Authentication authentication,
            @PathVariable("event_id") long eventId
    ) {
        AuthorizedUserDetails authorizedUserDetails = authPrincipal(authentication);

        List<EventParticipantResponse> participants =
                eventParticipantService.getByEvent(eventId, authorizedUserDetails.getId());

        return ResponseEntity.ok(participants);
    }
}
