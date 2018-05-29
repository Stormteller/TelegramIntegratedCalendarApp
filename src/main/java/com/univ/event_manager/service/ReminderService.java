package com.univ.event_manager.service;

import com.univ.event_manager.data.dto.input.ReminderInput;
import com.univ.event_manager.data.dto.output.ReminderResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface ReminderService {
    List<ReminderResponse> create(long eventId, List<ReminderInput> inputs);

    void remindForAll(LocalDateTime to);
}
