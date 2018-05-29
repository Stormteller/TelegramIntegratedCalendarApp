package com.univ.event_manager.web;

import com.univ.event_manager.data.dto.input.CreateEventInput;
import com.univ.event_manager.data.dto.input.FilterEventsInput;
import com.univ.event_manager.data.dto.output.EventResponse;
import com.univ.event_manager.data.dto.security.AuthorizedUserDetails;
import com.univ.event_manager.data.exception.BadRequestException;
import com.univ.event_manager.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
        if (params.hasErrors()) {
            throw new BadRequestException("Invalid request");
        }

        AuthorizedUserDetails authorizedUserDetails = authPrincipal(authentication);
        EventResponse eventResponse = eventService.create(input, authorizedUserDetails.getId());

        return ResponseEntity.ok(eventResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> eventById(Authentication authentication,
                                                   @PathVariable("id") long id) {
        AuthorizedUserDetails authorizedUserDetails = authPrincipal(authentication);
        EventResponse eventResponse = eventService.getEventById(id, authorizedUserDetails.getId());

        return ResponseEntity.ok(eventResponse);
    }

    @GetMapping
    public ResponseEntity<List<EventResponse>> eventByFilter(Authentication authentication,
                                                             FilterEventsInput input) {
        AuthorizedUserDetails authorizedUserDetails = authPrincipal(authentication);
        List<EventResponse> eventsByFilter = eventService.getEventByFilter(input, authorizedUserDetails.getId());

        return ResponseEntity.ok(eventsByFilter);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(Authentication authentication,
                                          @PathVariable("id") long id) {
        AuthorizedUserDetails authorizedUserDetails = authPrincipal(authentication);
        eventService.delete(id, authorizedUserDetails.getId());

        return ResponseEntity.ok(true);
    }
}
