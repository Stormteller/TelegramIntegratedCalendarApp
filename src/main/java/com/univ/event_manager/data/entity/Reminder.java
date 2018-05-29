package com.univ.event_manager.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = Reminder.TABLE_NAME)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reminder {
    public static final String TABLE_NAME = "reminder";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reminder_id_gen")
    @SequenceGenerator(name = "reminder_id_gen", sequenceName = "reminder_id_seq")
    private long id;

    @Column(name = "event_id")
    private Long eventId;

    @Builder.Default
    @Column(name = "reminding_time")
    private LocalDateTime remindingTime = LocalDateTime.now();

    @Builder.Default
    @Column(name = "is_sent")
    private boolean sent = false;

    private String message;
}
