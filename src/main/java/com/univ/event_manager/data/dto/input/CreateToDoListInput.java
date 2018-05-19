package com.univ.event_manager.data.dto.input;

import com.univ.event_manager.data.entity.enums.ToDoListType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

//TODO: Create custom validator for input (complex null checks)

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateToDoListInput {
    private String name;

    private LocalDate forDay;

    private Long eventId;

    private ToDoListType type;
}
