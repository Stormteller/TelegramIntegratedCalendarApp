package com.univ.event_manager.data.entity;

import com.univ.event_manager.data.entity.enums.ToDoListType;
import com.univ.event_manager.data.entity.utils.PostgreSQLEnumType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = ToDoList.TABLE_NAME)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TypeDef(
        name = "pgsql_enum",
        typeClass = PostgreSQLEnumType.class
)
public class ToDoList {
    public static final String TABLE_NAME = "to_do_list";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "to_do_list_id_gen")
    @SequenceGenerator(name = "to_do_list_id_gen", sequenceName = "to_do_list_id_seq")
    private long id;

    private String name;

    @Column(name = "for_day")
    private LocalDate forDay;

    @Column(name = "event_id")
    private Long eventId;

    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    private ToDoListType type;

    @Column(name = "creator_id")
    private long creatorId;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "toDoListId")
    @Fetch(FetchMode.SUBSELECT)
    private List<ToDoItem> items = new ArrayList<>();
}
