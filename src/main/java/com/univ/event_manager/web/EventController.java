package com.univ.event_manager.web;

import com.univ.event_manager.data.dto.input.CreateEventInput;
import com.univ.event_manager.data.dto.output.EventResponse;
import com.univ.event_manager.data.dto.security.AuthorizedUserDetails;
import com.univ.event_manager.data.exception.BadRequestException;
import com.univ.event_manager.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/event")
public class EventController implements AuthenticatedController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<EventResponse> create(Authentication authentication,
                                                @RequestBody @Valid CreateEventInput input,
                                                BindingResult params) {
        if(params.hasErrors()) {
            throw new BadRequestException("Invalid request");
        }

        AuthorizedUserDetails authorizedUserDetails = authPrincipal(authentication);
        EventResponse eventResponse = eventService.create(input, authorizedUserDetails.getId());

        return ResponseEntity.ok(eventResponse);
    }
}
