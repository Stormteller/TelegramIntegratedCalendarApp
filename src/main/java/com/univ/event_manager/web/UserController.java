package com.univ.event_manager.web;

import com.univ.event_manager.data.dto.input.SignInInput;
import com.univ.event_manager.data.dto.input.SignUpInput;
import com.univ.event_manager.data.dto.output.SignInResponse;
import com.univ.event_manager.data.dto.output.SignUpResponse;
import com.univ.event_manager.data.dto.output.UserResponse;
import com.univ.event_manager.data.dto.security.AuthorizedUserDetails;
import com.univ.event_manager.data.exception.BadRequestException;
import com.univ.event_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController implements AuthenticatedController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signin")
    public ResponseEntity<SignInResponse> signIn(@RequestBody @Valid SignInInput input, BindingResult params) {
        if (params.hasErrors()) {
            throw new BadRequestException(params.getFieldErrors().toString());
        }

        SignInResponse signInResponse = userService.signIn(input);
        return ResponseEntity.ok(signInResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signUp(@RequestBody @Valid SignUpInput input, BindingResult params) {
        if (params.hasErrors()) {
            throw new BadRequestException(params.getFieldErrors().toString());
        }

        SignUpResponse signUpResponse = userService.createUser(input);
        return ResponseEntity.ok(signUpResponse);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(Authentication auth) {
        AuthorizedUserDetails authorizedUserDetails = this.authPrincipal(auth);
        UserResponse userResponse = userService.getById(authorizedUserDetails.getId());

        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> userById(@PathVariable("id") int userId) {
        UserResponse userResponse = userService.getById(userId);

        return ResponseEntity.ok(userResponse);
    }

}
