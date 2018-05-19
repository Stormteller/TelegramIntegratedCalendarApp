package com.univ.event_manager.data.dto.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InviteUsersInput {
    @Size(max = 20)
    @NotNull
    private List<Long> userIds = new ArrayList<>();

    @NotNull
    private Long eventId;
}
