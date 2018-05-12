package com.univ.event_manager.data.dto.output;

import com.univ.event_manager.data.entity.enums.InvitationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvitationResponse {
    private long id;

    private long receiverId;

    private UserResponse sender;

    private EventResponse event;

    private InvitationStatus status;

    private LocalDateTime createdAt;
}
