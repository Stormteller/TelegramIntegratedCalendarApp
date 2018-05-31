package com.univ.event_manager.data.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = Profile.TABLE_NAME)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(exclude = "user")
public class Profile implements Serializable {
    public static final String TABLE_NAME = "profile";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_id_gen")
    @SequenceGenerator(name = "profile_id_gen", sequenceName = "profile_id_seq")
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String avatar;

    @Column(name = "telegram_id")
    private String telegramId;

    @Column(name = "google_token")
    private String googleToken;

    @Column(name = "notification_token")
    private String notificationToken;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "profile", optional = false)
    @MapsId
    @JoinColumn(name = "id")
    private User user;
}
