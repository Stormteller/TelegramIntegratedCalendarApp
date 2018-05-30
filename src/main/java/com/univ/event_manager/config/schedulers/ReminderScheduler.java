package com.univ.event_manager.config.schedulers;

import com.univ.event_manager.config.listeners.AbstractJobScheduler;
import com.univ.event_manager.service.jobs.ReminderSendingJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

import static org.quartz.CronScheduleBuilder.cronSchedule;

// TODO: Think about schedule reminders dynamically on create, but then it would be more complex to update them

@Component
public class ReminderScheduler extends AbstractJobScheduler {
    private final String REMINDER_IDENTITY = "REMINDER_RESCHEDULE";

    @Autowired
    public ReminderScheduler(Scheduler scheduler) {
        super(scheduler);
    }

    @Override
    protected JobDetail buildDetails() {
        return JobBuilder.newJob()
                .ofType(ReminderSendingJob.class)
                .storeDurably()
                .build();
    }

    @Override
    protected Trigger buildTrigger(JobDetail detail) {
        Date date = new Date();
        return TriggerBuilder.newTrigger()
                .forJob(detail)
                .startAt(date)
                .withIdentity(REMINDER_IDENTITY)
                .withSchedule(cronSchedule("0 0/1 * * * ?"))
                .build();
    }

}
