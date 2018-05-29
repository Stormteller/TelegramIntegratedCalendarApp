package com.univ.event_manager.service.senders.impl;

import com.univ.event_manager.data.entity.Reminder;
import com.univ.event_manager.service.senders.ReminderSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailSender implements ReminderSender {
    @Override
    public void send(Reminder reminder) {
        log.info("Шлем ремаиндер на почту");
    }
}
