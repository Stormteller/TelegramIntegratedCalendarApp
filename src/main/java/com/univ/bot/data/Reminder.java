package com.univ.bot.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reminder {

    private Event event;

    private long minutesBeforeEvent;
}
