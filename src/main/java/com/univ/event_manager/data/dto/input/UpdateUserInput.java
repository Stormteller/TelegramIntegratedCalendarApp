package com.univ.event_manager.data.dto.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserInput {
    @NotNull
    private String firstName;

    @NotNull
    private String lastName;
}
