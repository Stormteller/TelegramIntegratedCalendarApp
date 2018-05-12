package com.univ.event_manager.data.dto.output;

import com.univ.event_manager.data.entity.RecurringRule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventResponse {
    private long id;

    private String title;

    private LocalDateTime startAt;

    private LocalDateTime finishAt;

    private LocationResponse location;

    private long creatorId;

    private String description;

    private boolean isPublic;

    private boolean isRecurring;

    private boolean isWholeDay;

    private RecurringRuleResponse recurringRule;

    private LocalDateTime createdAt = LocalDateTime.now();
}
