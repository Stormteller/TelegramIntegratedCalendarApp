package com.univ.event_manager.service.impl;

import com.univ.event_manager.data.dto.input.*;
import com.univ.event_manager.data.dto.output.EventResponse;
import com.univ.event_manager.data.entity.Event;
import com.univ.event_manager.data.entity.Location;
import com.univ.event_manager.data.entity.RecurringRule;
import com.univ.event_manager.data.entity.User;
import com.univ.event_manager.data.entity.enums.RecurringType;
import com.univ.event_manager.data.exception.NotFoundException;
import com.univ.event_manager.data.repository.EventRepository;
import com.univ.event_manager.data.repository.RecurringRuleRepository;
import com.univ.event_manager.data.repository.UserRepository;
import com.univ.event_manager.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Component
public class EventServiceImpl implements EventService {
    private static final int EVENTS_TO_CREATE_IF_RECURRING = 100;

    private final EventRepository eventRepository;
    private final RecurringRuleRepository recurringRuleRepository;
    private final UserRepository userRepository;

    private final Converter<Event, EventResponse> eventConverter;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository,
                            RecurringRuleRepository recurringRuleRepository,
                            UserRepository userRepository, Converter<Event, EventResponse> eventConverter) {
        this.eventRepository = eventRepository;
        this.recurringRuleRepository = recurringRuleRepository;
        this.userRepository = userRepository;
        this.eventConverter = eventConverter;
    }


    // TODO: Move event creation to some factory class
    @Override
    @Transactional
    public EventResponse create(CreateEventInput input, long creatorId) {
        User user = userRepository.findById(creatorId).orElseThrow(() -> new NotFoundException("User not found"));

        Event createdEvent;
        if(input.isRecurring()) {
            createdEvent = createRecurringEvent(input, user).get(0);
        } else {
            createdEvent = createSingleTimeEvent(input, user);
        }

        //TODO: Create Invites
        //TODO: Create Reminders

        return eventConverter.convert(createdEvent);
    }

    private Event createSingleTimeEvent(CreateEventInput input, User creator) {
        Event event = Event.builder()
                .title(input.getTitle())
                .location(buildLocationFromInput(input.getLocation()))
                .startAt(input.getStartAt())
                .finishAt(input.getFinishAt())
                .isWholeDay(input.isWholeDay())
                .isRecurring(input.isRecurring())
                .isPublic(input.isPublic())
                .description(input.getDescription())
                .creator(creator)
                .build();

        event = eventRepository.save(event);

        return event;
    }

    private Location buildLocationFromInput(LocationInput input) {
        return Optional.ofNullable(input)
                .map(locationInput -> Location.builder()
                        .address(locationInput.getAddress())
                        .title(locationInput.getTitle())
                        .latitude(locationInput.getLatitude())
                        .longitude(locationInput.getLongitude())
                        .build())
                .orElse(null);
    }

    private List<Event> createRecurringEvent(CreateEventInput input, User creator) {
        RecurringRuleInput inputRecurringRule = input.getRecurringRule();

        RecurringRule recurringRule = RecurringRule.builder()
                .recurringType(inputRecurringRule.getRecurringType())
                .interval(inputRecurringRule.getInterval())
                .weekDays(inputRecurringRule.getWeekDays())
                .build();

        recurringRule = recurringRuleRepository.save(recurringRule);

        List<Event> createdEvents = new ArrayList<>();
        Duration eventDuration = Duration.between(input.getStartAt(), input.getFinishAt());
        
        for(int eventNumber = 0; eventNumber < EVENTS_TO_CREATE_IF_RECURRING; eventNumber++) {
            LocalDateTime eventStartAt = calculateNextEventTime(input.getStartAt(), recurringRule, eventNumber);

            Event event = Event.builder()
                    .title(input.getTitle())
                    .location(buildLocationFromInput(input.getLocation()))
                    .startAt(eventStartAt)
                    .finishAt(eventStartAt.plus(eventDuration))
                    .isWholeDay(input.isWholeDay())
                    .isRecurring(input.isRecurring())
                    .isPublic(input.isPublic())
                    .description(input.getDescription())
                    .creator(creator)
                    .recurringRule(recurringRule)
                    .build();

            createdEvents.add(event);
        }
        return eventRepository.saveAll(createdEvents);
    }

    private LocalDateTime calculateNextEventTime(LocalDateTime firstTime, RecurringRule recurringRule, int number) {
        ChronoUnit chronoUnit = chronoUnitFromRecurringType(recurringRule.getRecurringType());
            if(recurringRule.getRecurringType() != RecurringType.WEEKLY) {
            int step = number * recurringRule.getInterval();
            return firstTime.plus(step, chronoUnit);
        } else {
            LocalDateTime actualFirstTime = getActualFirstTimeEvent(firstTime, recurringRule);

            DayOfWeek targetDayOfWeek = getTargetDayOfWeek(actualFirstTime, recurringRule, number);

            int weeksToAdd = (number / recurringRule.getWeekDays().size()) * recurringRule.getInterval();

            return actualFirstTime.plus(weeksToAdd, chronoUnit).with(targetDayOfWeek);
        }
    }

    private LocalDateTime getActualFirstTimeEvent(LocalDateTime firstTime, RecurringRule recurringRule) {
        DayOfWeek firstTimeDayOfWeek = firstTime.getDayOfWeek();
        Optional<DayOfWeek> currentWeekFirstTimeWeekDay = recurringRule.getWeekDays()
                .stream()
                .filter(weekDay -> weekDay.compareTo(firstTimeDayOfWeek) > 0)
                .findFirst();

        LocalDateTime onWeekWithFirstEvent = firstTime;
        if(!currentWeekFirstTimeWeekDay.isPresent()) {
            onWeekWithFirstEvent = firstTime.plusWeeks(recurringRule.getInterval());
        }

        DayOfWeek actualFirstTimeWeekDay = currentWeekFirstTimeWeekDay.orElse(recurringRule.getWeekDays().get(0));

        return onWeekWithFirstEvent.with(actualFirstTimeWeekDay);
    }

    private DayOfWeek getTargetDayOfWeek(LocalDateTime actualFirstTime, RecurringRule recurringRule, int number) {
        int weekDaysSize = recurringRule.getWeekDays().size();

        int firstDayOfWeekIndex = recurringRule.getWeekDays().indexOf(actualFirstTime.getDayOfWeek());

        int targetDayOfWeekIndex = (number + firstDayOfWeekIndex) % weekDaysSize;

        return recurringRule.getWeekDays().get(targetDayOfWeekIndex);
    }

    private ChronoUnit chronoUnitFromRecurringType(RecurringType recurringType) {
        switch (recurringType) {
            case DAILY:
                return ChronoUnit.DAYS;
            case WEEKLY:
                return ChronoUnit.WEEKS;
            case MONTHLY:
                return ChronoUnit.MONTHS;
            case YEARLY:
                return ChronoUnit.YEARS;
            default:
                throw new RuntimeException("Should never happen");
        }
    }

    @Override
    public List<EventResponse> getEventByFilter(FilterEventsInput input, long userId) {
        return null;
    }

    @Override
    public EventResponse getEventById(long eventId, long userId) {
        return null;
    }

    @Override
    public EventResponse update(long eventId, UpdateEventInput input) {
        return null;
    }

    @Override
    public void delete(long eventId) {

    }
}
