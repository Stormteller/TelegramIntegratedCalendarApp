package com.univ.event_manager.data.dto.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterEventsInput {
    @NotNull
    private LocalDateTime from;

    @NotNull
    private LocalDateTime to;

    private Long userId;
}
