package com.univ.event_manager.data.converter;

import com.univ.event_manager.data.dto.output.ProfileResponse;
import com.univ.event_manager.data.entity.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProfileToDtoConverter implements Converter<Profile, ProfileResponse> {
    @Override
    public ProfileResponse convert(Profile profile) {
        return ProfileResponse.builder()
                .avatar(profile.getAvatar())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .telegramId(profile.getTelegramId())
                .build();
    }
}
