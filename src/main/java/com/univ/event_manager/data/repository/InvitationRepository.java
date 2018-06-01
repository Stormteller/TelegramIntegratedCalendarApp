package com.univ.event_manager.data.repository;

import com.univ.event_manager.data.entity.Invitation;
import com.univ.event_manager.data.entity.enums.InvitationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    Optional<Invitation> findById(Long id);
    Page<Invitation> findByReceiverIdAndStatus(Long id, InvitationStatus status, Pageable pageable);
}
