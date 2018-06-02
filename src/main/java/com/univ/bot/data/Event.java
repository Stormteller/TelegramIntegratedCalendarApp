package com.univ.bot.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    private String title;

    private LocalDateTime startAt;

    private LocalDateTime finishAt;

    private long userID;

    private List<Reminder> reminders;
}
