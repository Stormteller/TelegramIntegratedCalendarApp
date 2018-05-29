package com.univ.event_manager.data.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReminderResponse {
    private long id;

    private Long eventId;

    private LocalDateTime remindingTime;

    private String message;

}
