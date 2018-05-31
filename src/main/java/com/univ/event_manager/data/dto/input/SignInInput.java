package com.univ.event_manager.data.dto.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignInInput {
    @NotNull
    private String email;
    @NotNull
    private String password;
}
