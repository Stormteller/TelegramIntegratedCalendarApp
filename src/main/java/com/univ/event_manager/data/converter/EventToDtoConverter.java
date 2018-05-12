package com.univ.event_manager.data.converter;

import com.univ.event_manager.data.dto.output.EventResponse;
import com.univ.event_manager.data.dto.output.LocationResponse;
import com.univ.event_manager.data.dto.output.RecurringRuleResponse;
import com.univ.event_manager.data.entity.Event;
import com.univ.event_manager.data.entity.Location;
import com.univ.event_manager.data.entity.RecurringRule;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EventToDtoConverter implements Converter<Event, EventResponse> {

    private final Converter<Location, LocationResponse> locationConverter;
    private final Converter<RecurringRule, RecurringRuleResponse> recurringRuleConverter;

    public EventToDtoConverter(Converter<Location, LocationResponse> locationConverter,
                               Converter<RecurringRule, RecurringRuleResponse> recurringRuleConverter) {
        this.locationConverter = locationConverter;
        this.recurringRuleConverter = recurringRuleConverter;
    }

    @Override
    public EventResponse convert(Event event) {
        return EventResponse.builder()
                .id(event.getId())
                .creatorId(event.getCreator().getId())
                .isPublic(event.isPublic())
                .isWholeDay(event.isWholeDay())
                .isRecurring(event.isRecurring())
                .description(event.getDescription())
                .startAt(event.getStartAt())
                .finishAt(event.getFinishAt())
                .location(locationConverter.convert(event.getLocation()))
                .recurringRule(recurringRuleConverter.convert(event.getRecurringRule()))
                .title(event.getTitle())
                .createdAt(event.getCreatedAt())
                .build();
    }
}
