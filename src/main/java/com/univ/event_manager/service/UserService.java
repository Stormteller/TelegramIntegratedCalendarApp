package com.univ.event_manager.service;

import com.univ.event_manager.data.dto.input.SignInInput;
import com.univ.event_manager.data.dto.input.SignUpInput;
import com.univ.event_manager.data.dto.output.SignInResponse;
import com.univ.event_manager.data.dto.output.SignUpResponse;
import com.univ.event_manager.data.dto.output.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    SignInResponse signIn(SignInInput signInInput);
    SignUpResponse createUser(SignUpInput signUpInput);
    UserResponse getById(long id);
    UserResponse getByEmail(String email);
}
