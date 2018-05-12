package com.univ.event_manager.data.dto.input;

import com.univ.event_manager.data.entity.enums.RecurringType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

//TODO: add custom validator for sorting weekdays

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecurringRuleInput {
    private List<DayOfWeek> weekDays = new ArrayList<>();

    private RecurringType recurringType;

    private int interval;
}




