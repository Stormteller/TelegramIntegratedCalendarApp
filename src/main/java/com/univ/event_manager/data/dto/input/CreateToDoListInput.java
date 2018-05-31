package com.univ.event_manager.data.dto.input;

import com.univ.event_manager.data.entity.enums.ToDoListType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

//TODO: Create custom validator for input (complex null checks)

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateToDoListInput {
    @NotNull
    private String name;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate forDay;

    private Long eventId;

    @NotNull
    private ToDoListType type;
}
