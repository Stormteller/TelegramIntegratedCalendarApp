package com.univ.event_manager.service.impl;

import com.univ.event_manager.config.security.JwtTokenProvider;
import com.univ.event_manager.data.dto.input.SignInInput;
import com.univ.event_manager.data.dto.input.SignUpInput;
import com.univ.event_manager.data.dto.output.SignInResponse;
import com.univ.event_manager.data.dto.output.SignUpResponse;
import com.univ.event_manager.data.dto.output.UserResponse;
import com.univ.event_manager.data.dto.security.AuthorizedUserDetails;
import com.univ.event_manager.data.entity.User;
import com.univ.event_manager.data.exception.BadRequestException;
import com.univ.event_manager.data.exception.NotFoundException;
import com.univ.event_manager.data.repository.ProfileRepository;
import com.univ.event_manager.data.repository.UserRepository;
import com.univ.event_manager.service.ProfileService;
import com.univ.event_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    private final Converter<User, UserResponse> userConverter;

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    private final ProfileService profileService;


    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider,
                           Converter<User, UserResponse> userConverter,
                           ProfileRepository profileRepository,
                           ProfileService profileService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userConverter = userConverter;
        this.profileRepository = profileRepository;
        this.profileService = profileService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new BadRequestException("User not found"));

        return AuthorizedUserDetails.builder()
                .email(user.getEmail())
                .id(user.getId())
                .password(user.getPassword())
                .build();
    }

    @Override
    @Transactional
    public SignInResponse signIn(SignInInput signInInput) {
        UserDetails userDetails = loadUserByUsername(signInInput.getEmail());

        validatePassword(userDetails.getPassword(), signInInput.getPassword());

        String token = jwtTokenProvider.createToken(signInInput.getEmail(), userDetails.getAuthorities());

        return SignInResponse.builder()
                .token(token)
                .build();
    }

    private void validatePassword(String currentPassword, String inputPassword) {
        boolean passwordsMatch = passwordEncoder.matches(inputPassword, currentPassword);
        if (!passwordsMatch) throw new BadRequestException("Invalid password");
    }

    @Override
    @Transactional
    public SignUpResponse createUser(SignUpInput signUpInput) {
        checkIfUserExists(signUpInput.getEmail());

        User user = buildUser(signUpInput);

        profileService.createProfileForUser(user, signUpInput);

        profileRepository.findById(user.getId()).ifPresent(user::setProfile);

        user = userRepository.save(user);

        String token = signIn(SignInInput.builder()
                .email(signUpInput.getEmail())
                .password(signUpInput.getPassword())
                .build()).getToken();

        return SignUpResponse.builder()
                .token(token)
                .user(userConverter.convert(user))
                .build();
    }

    private void checkIfUserExists(String email) {
        userRepository
                .findByEmail(email)
                .ifPresent(user -> {
                    throw new BadRequestException("User already exists");
                });
    }

    private User buildUser(SignUpInput signUpInput) {
        User user = User.builder()
                .email(signUpInput.getEmail())
                .password(passwordEncoder.encode(signUpInput.getPassword()))
                .build();

        return userRepository.save(user);
    }

    @Override
    public UserResponse getById(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        return userConverter.convert(user);
    }

    @Override
    public UserResponse getByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return userConverter.convert(user);
    }
}
