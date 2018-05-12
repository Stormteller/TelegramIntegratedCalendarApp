package com.univ.event_manager.data.dto.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventInput {
    @NotNull
    @Size(min = 3, max = 255)
    private String title;

    @NotNull
    private LocalDateTime startAt;

    @NotNull
    private LocalDateTime finishAt;

    private LocationInput location;

    @NotNull
    private String description;

    @NotNull
    private boolean isPublic;

    @NotNull
    private boolean isRecurring;

    @NotNull
    private boolean isWholeDay;

    private RecurringRuleInput recurringRule;
}
