package com.univ.event_manager.service.senders;

import com.univ.event_manager.data.entity.Reminder;

import java.util.List;

public interface ReminderSender {
    default void send(List<Reminder> reminders) {
        reminders.forEach(this::send);
    }

    void send(Reminder reminder);
}
