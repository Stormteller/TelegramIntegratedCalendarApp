package com.univ.event_manager.service.impl;

import com.univ.event_manager.data.dto.input.ReminderInput;
import com.univ.event_manager.data.dto.output.ReminderResponse;
import com.univ.event_manager.data.entity.Reminder;
import com.univ.event_manager.data.repository.ReminderRepository;
import com.univ.event_manager.service.ReminderService;
import com.univ.event_manager.service.senders.ReminderSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReminderServiceImpl implements ReminderService {

    private final ReminderRepository reminderRepository;

    private final Converter<Reminder, ReminderResponse> reminderConverter;

    private final List<ReminderSender> reminderSenders;

    @Autowired
    public ReminderServiceImpl(ReminderRepository reminderRepository,
                               Converter<Reminder, ReminderResponse> reminderConverter,
                               List<ReminderSender> reminderSenders) {
        this.reminderRepository = reminderRepository;
        this.reminderConverter = reminderConverter;
        this.reminderSenders = reminderSenders;
    }

    @Override
    public List<ReminderResponse> create(long eventId, List<ReminderInput> inputs) {
        List<Reminder> reminders = inputs.stream()
                .map(input ->
                        Reminder.builder()
                                .eventId(eventId)
                                .message(input.getMessage())
                                .remindingTime(input.getReminderTime())
                                .build())
                .collect(Collectors.toList());


        reminders = reminderRepository.saveAll(reminders);

        return reminders.stream().map(reminderConverter::convert).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void remindForAll(LocalDateTime to) {
        List<Reminder> remindersToSend = reminderRepository.findByRemindingTimeBeforeAndSent(to, false);

        reminderSenders.forEach(sender -> sender.send(remindersToSend));

        remindersToSend.forEach(reminder -> reminder.setSent(true));
    }
}
