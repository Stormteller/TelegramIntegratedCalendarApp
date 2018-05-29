package com.univ.event_manager.data.converter;

import com.univ.event_manager.data.dto.output.ReminderResponse;
import com.univ.event_manager.data.entity.Reminder;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ReminderToDtoConverter implements Converter<Reminder, ReminderResponse> {
    @Override
    public ReminderResponse convert(Reminder reminder) {
        return ReminderResponse.builder()
                .id(reminder.getId())
                .eventId(reminder.getEventId())
                .remindingTime(reminder.getRemindingTime())
                .message(reminder.getMessage())
                .build();
    }
}
