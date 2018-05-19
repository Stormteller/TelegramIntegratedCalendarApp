package com.univ.event_manager.data.repository;

import com.univ.event_manager.data.entity.Invitation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    Optional<Invitation> findById(Long id);
    Page<Invitation> findByReceiverId(Long id, Pageable pageable);
}
