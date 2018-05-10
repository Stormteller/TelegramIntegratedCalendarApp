package com.univ.event_manager.data.entity;

import com.univ.event_manager.data.entity.utils.PostgreSQLEnumType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = RecurringRule.TABLE_NAME)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TypeDef(
        name = "pgsql_enum",
        typeClass = PostgreSQLEnumType.class
)
public class RecurringRule {
    public static final String TABLE_NAME = "recurring_rule";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recurring_rule_id_gen")
    @SequenceGenerator(name = "recurring_rule_id_gen", sequenceName = "recurring_rule_id_seq")
    private long id;

    @Builder.Default
    @Column(name = "week_days")
    @ElementCollection
    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    private List<WeekDay> weekDays = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    @Column(name = "recurring_type")
    private RecurringType recurringType;

    private int interval;
}
