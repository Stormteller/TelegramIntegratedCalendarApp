package com.univ.event_manager.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = Event.TABLE_NAME)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Event {
    public static final String TABLE_NAME = "event";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_id_gen")
    @SequenceGenerator(name = "event_id_gen", sequenceName = "event_id_seq")
    private long id;

    private String title;

    @Column(name = "start_at")
    private LocalDateTime startAt;

    @Column(name = "finish_at")
    private LocalDateTime finishAt;

    @Embedded
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private User creator;

    private String description;

    @Builder.Default
    @Column(name = "is_public")
    private boolean isPublic = true;

    @Builder.Default
    @Column(name = "is_recurring")
    private boolean isRecurring = false;

    @Builder.Default
    @Column(name = "is_whole_day")
    private boolean isWholeDay = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recurring_rule_id")
    private RecurringRule recurringRule;

    @Builder.Default
    @JoinColumn(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
