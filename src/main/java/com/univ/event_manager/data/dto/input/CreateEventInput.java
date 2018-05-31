package com.univ.event_manager.data.dto.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//TODO: add custom validator for start, finish

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEventInput {
    @NotNull
    @Size(min = 3, max = 255)
    private String title;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startAt;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
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

    @Size(max = 50)
    private List<ReminderInput> reminders = new ArrayList<>();

    @Size(max = 50)
    private List<Long> inviteUsers = new ArrayList<>();
}
