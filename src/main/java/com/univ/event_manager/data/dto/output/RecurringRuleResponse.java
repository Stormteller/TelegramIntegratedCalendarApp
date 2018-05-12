package com.univ.event_manager.data.dto.output;

import com.univ.event_manager.data.entity.enums.RecurringType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecurringRuleResponse {
    private long id;

    @Builder.Default
    private List<DayOfWeek> weekDays = new ArrayList<>();

    private RecurringType recurringType;

    private int interval;
}
