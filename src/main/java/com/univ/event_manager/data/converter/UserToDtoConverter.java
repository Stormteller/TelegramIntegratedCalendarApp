package com.univ.event_manager.data.converter;

import com.univ.event_manager.data.dto.output.ProfileResponse;
import com.univ.event_manager.data.dto.output.UserResponse;
import com.univ.event_manager.data.entity.Profile;
import com.univ.event_manager.data.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToDtoConverter implements Converter<User, UserResponse> {

    private final Converter<Profile, ProfileResponse> profileConverter;

    @Autowired
    public UserToDtoConverter(Converter<Profile, ProfileResponse> profileConverter) {
        this.profileConverter = profileConverter;
    }

    @Override
    public UserResponse convert(User user) {
        return UserResponse.builder()
                .email(user.getEmail())
                .id(user.getId())
                .profile(profileConverter.convert(user.getProfile()))
                .build();
    }
}
