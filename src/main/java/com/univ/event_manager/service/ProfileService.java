package com.univ.event_manager.service;

import com.univ.event_manager.data.dto.input.SignUpInput;
import com.univ.event_manager.data.dto.input.UpdateProfileInput;
import com.univ.event_manager.data.dto.output.ProfileResponse;
import com.univ.event_manager.data.entity.User;

public interface ProfileService {
    void createProfileForUser(User user, SignUpInput signUpInput);

    ProfileResponse updateProfile(long profileId, UpdateProfileInput updateProfileInput);

    ProfileResponse connectTelegramAccount(long profileId, String telegramId);
//    ProfileResponse updateAvatar(long profileId, );
}
