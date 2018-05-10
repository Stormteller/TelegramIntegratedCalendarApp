package com.univ.event_manager.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = EventParticipant.TABLE_NAME)
public class EventParticipant {

    public static final String TABLE_NAME = "event_participant";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_participant_seq_gen")
    @SequenceGenerator(name = "event_participant_seq_gen", sequenceName = "event_participant_id_seq")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "event_id")
    private long eventId;

    @Builder.Default
    @JoinColumn(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

}
