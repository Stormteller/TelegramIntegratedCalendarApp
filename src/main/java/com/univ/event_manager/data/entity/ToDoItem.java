package com.univ.event_manager.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = ToDoItem.TABLE_NAME)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToDoItem {
    public static final String TABLE_NAME = "to_do_item";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "to_do_item_id_gen")
    @SequenceGenerator(name = "to_do_item_id_gen", sequenceName = "to_do_item_id_seq")
    private long id;

    @Builder.Default
    @Column(name = "is_done")
    private boolean isDone = false;

    private String text;

    @Column(name = "to_do_list_id")
    private long toDoListId;

    @Builder.Default
    @JoinColumn(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
