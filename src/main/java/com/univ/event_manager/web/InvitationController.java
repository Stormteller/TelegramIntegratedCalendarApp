package com.univ.event_manager.web;

import com.univ.event_manager.data.dto.input.InviteUsersInput;
import com.univ.event_manager.data.dto.output.InvitationResponse;
import com.univ.event_manager.data.dto.security.AuthorizedUserDetails;
import com.univ.event_manager.service.InvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/invitation")
public class InvitationController implements AuthenticatedController {

    private final InvitationService invitationService;

    @Autowired
    public InvitationController(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvitationResponse> invitationById(@PathVariable("id") long invitationId,
                                                      Authentication authentication) {

        AuthorizedUserDetails authorizedUserDetails = authPrincipal(authentication);

        InvitationResponse invitation = invitationService.getById(invitationId, authorizedUserDetails.getId());

        return ResponseEntity.ok(invitation);
    }

    @GetMapping("/me")
    public ResponseEntity<Page<InvitationResponse>> invitationsForMe(Authentication authentication, Pageable pageable) {
        AuthorizedUserDetails authorizedUserDetails = authPrincipal(authentication);

        Page<InvitationResponse> invitations = invitationService.getByUser(authorizedUserDetails.getId(), pageable);

        return ResponseEntity.ok(invitations);
    }

    @PostMapping("/{id}/acceptance")
    public ResponseEntity<Boolean> acceptInvitation(@PathVariable("id") long invitationId,
                                             Authentication authentication) {
        AuthorizedUserDetails authorizedUserDetails = authPrincipal(authentication);

        invitationService.accept(invitationId, authorizedUserDetails.getId());

        return ResponseEntity.ok(true);
    }

    @PostMapping("/{id}/declination")
    public ResponseEntity<Boolean> declineInvitation(@PathVariable("id") long invitationId,
                                             Authentication authentication) {
        AuthorizedUserDetails authorizedUserDetails = authPrincipal(authentication);

        invitationService.reject(invitationId, authorizedUserDetails.getId());

        return ResponseEntity.ok(true);
    }

    @PostMapping
    public ResponseEntity<Boolean> inviteUsers(@RequestBody @Valid InviteUsersInput input, Authentication authentication) {
        AuthorizedUserDetails authorizedUserDetails = authPrincipal(authentication);

        invitationService.create(input.getUserIds(), input.getEventId(), authorizedUserDetails.getId());

        return ResponseEntity.ok(true);
    }
}
