package com.univ.event_manager.data.entity;

import com.univ.event_manager.data.entity.utils.PostgreSQLEnumType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = Invitation.TABLE_NAME)
@TypeDef(
        name = "pgsql_enum",
        typeClass = PostgreSQLEnumType.class
)
public class Invitation {
    public static final String TABLE_NAME = "invitation";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invitation_seq_gen")
    @SequenceGenerator(name = "invitation_seq_gen", sequenceName = "invitation_id_seq")
    private long id;

    @Column(name = "receiver_id")
    private long receiverId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    private InvitationStatus status = InvitationStatus.NEW;

    @Builder.Default
    @JoinColumn(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
