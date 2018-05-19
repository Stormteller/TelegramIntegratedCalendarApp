package com.univ.event_manager.data.dto.output;

import com.univ.event_manager.data.entity.enums.ToDoListType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToDoListResponse {
    private long id;

    private String name;

    private LocalDate forDay;

    private Long eventId;

    private ToDoListType type;

    private long creatorId;

    private List<ToDoItemResponse> items;
}
