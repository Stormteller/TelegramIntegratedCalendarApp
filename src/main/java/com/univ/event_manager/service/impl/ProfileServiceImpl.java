package com.univ.event_manager.service.impl;

import com.univ.event_manager.data.dto.input.SignUpInput;
import com.univ.event_manager.data.dto.input.UpdateProfileInput;
import com.univ.event_manager.data.dto.output.ProfileResponse;
import com.univ.event_manager.data.entity.Profile;
import com.univ.event_manager.data.entity.User;
import com.univ.event_manager.data.exception.NotFoundException;
import com.univ.event_manager.data.repository.ProfileRepository;
import com.univ.event_manager.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final Converter<Profile, ProfileResponse> profileConverter;

    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository, Converter<Profile, ProfileResponse> profileConverter) {
        this.profileRepository = profileRepository;
        this.profileConverter = profileConverter;
    }

    @Override
    @Transactional
    public void createProfileForUser(User user, SignUpInput signUpInput) {
        Profile profile = Profile.builder()
                .user(user)
                .lastName(signUpInput.getLastName())
                .firstName(signUpInput.getFirstName())
                .build();

        profileRepository.save(profile);
    }

    @Override
    @Transactional
    public ProfileResponse updateProfile(long profileId, UpdateProfileInput updateProfileInput) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new NotFoundException("Profile not found"));

        profile.setFirstName(updateProfileInput.getFirstName());
        profile.setLastName(updateProfileInput.getLastName());

        profile = profileRepository.save(profile);

        return profileConverter.convert(profile);
    }

    @Override
    public ProfileResponse connectTelegramAccount(long profileId, String telegramId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new NotFoundException("Profile not found"));

        profile.setTelegramId(telegramId);

        profile = profileRepository.save(profile);

        return profileConverter.convert(profile);
    }
}
