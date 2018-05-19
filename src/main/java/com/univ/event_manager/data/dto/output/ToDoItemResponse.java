package com.univ.event_manager.data.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToDoItemResponse {
    private long id;

    @Builder.Default
    private boolean isDone = false;

    private String text;

    private long toDoListId;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
