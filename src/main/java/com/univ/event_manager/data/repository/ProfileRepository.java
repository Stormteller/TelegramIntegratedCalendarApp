package com.univ.event_manager.data.repository;

import com.univ.event_manager.data.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findById(long id);
}
