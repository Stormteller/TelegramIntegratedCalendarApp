package com.univ.event_manager.data.dto.input;

import com.univ.event_manager.data.entity.enums.ToDoListType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToDoListFilterInput {
    private Long eventId;
    private LocalDate date;
    private ToDoListType type;
}
