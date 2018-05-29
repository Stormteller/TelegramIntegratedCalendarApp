package com.univ.event_manager.service.jobs;

import com.univ.event_manager.service.ReminderService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Slf4j
public class ReminderSendingJob implements Job {
    @Autowired
    private ReminderService reminderService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        log.info("Scheduling reminders jobs");

        LocalDateTime to = LocalDateTime.now().plus(1, ChronoUnit.MINUTES);

        reminderService.remindForAll(to);
    }
}
