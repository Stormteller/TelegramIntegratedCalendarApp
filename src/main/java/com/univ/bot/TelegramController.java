package com.univ.bot;

import com.univ.event_manager.data.dto.input.CreateEventInput;
import com.univ.event_manager.data.dto.input.FilterEventsInput;
import com.univ.event_manager.data.dto.output.EventResponse;
import com.univ.event_manager.data.dto.security.AuthorizedUserDetails;
import com.univ.event_manager.data.exception.BadRequestException;
import com.univ.event_manager.service.EventService;
import com.univ.event_manager.web.AuthenticatedController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.List;

/**
 * Currently only initializes Telegram-bot
 */

@Component
public class TelegramController implements AuthenticatedController {

    private final EventService eventService;

    @Autowired
    public TelegramController(EventService eventService) {
        this.eventService = eventService;
        TelegramBot.init(this);
    }

    public EventResponse create(Authentication authentication,
                                CreateEventInput input, BindingResult params) {
        if (params.hasErrors()) {
            throw new BadRequestException("Invalid request");
        }

        AuthorizedUserDetails authorizedUserDetails = authPrincipal(authentication);
        return eventService.create(input, authorizedUserDetails.getId());
    }

    public EventResponse eventById(Authentication authentication, long id) {
        AuthorizedUserDetails authorizedUserDetails = authPrincipal(authentication);
        return eventService.getEventById(id, authorizedUserDetails.getId());
    }

    public List<EventResponse> eventByFilter(Authentication authentication,
                                             FilterEventsInput input) {
        AuthorizedUserDetails authorizedUserDetails = authPrincipal(authentication);
        return eventService.getEventByFilter(input, authorizedUserDetails.getId());
    }

    public Boolean delete(Authentication authentication, long id) {
        AuthorizedUserDetails authorizedUserDetails = authPrincipal(authentication);
        eventService.delete(id, authorizedUserDetails.getId());
        return true;
    }


}
