package com.univ.event_manager.data.dto.input;

import com.univ.event_manager.data.entity.enums.ToDoListType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateToDoListInput {
    @NotNull
    private String name;

    private LocalDate forDay;

    private Long eventId;

    private ToDoListType type;
}
