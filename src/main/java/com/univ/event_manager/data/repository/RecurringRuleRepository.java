package com.univ.event_manager.data.repository;

import com.univ.event_manager.data.entity.RecurringRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecurringRuleRepository extends JpaRepository<RecurringRule, Long> {
}
