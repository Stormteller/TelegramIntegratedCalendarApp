package com.univ.event_manager.data.converter;

import com.univ.event_manager.data.dto.output.RecurringRuleResponse;
import com.univ.event_manager.data.entity.RecurringRule;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RecurringRuleToDtoConverter implements Converter<RecurringRule, RecurringRuleResponse> {
    @Override
    public RecurringRuleResponse convert(RecurringRule recurringRule) {
        return null;
    }
}
