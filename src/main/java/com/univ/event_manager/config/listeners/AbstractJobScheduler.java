package com.univ.event_manager.config.listeners;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public abstract class AbstractJobScheduler implements ApplicationListener<ContextRefreshedEvent> {
    private AtomicBoolean instantiated = new AtomicBoolean(false);

    private final Scheduler scheduler;

    protected AbstractJobScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (instantiated.get()) return;
        JobDetail jobDetail = buildDetails();
        trySchedule(jobDetail, buildTrigger(jobDetail));
    }

    protected abstract JobDetail buildDetails();

    protected abstract Trigger buildTrigger(JobDetail detail);


    private void trySchedule(JobDetail detail, Trigger trigger) {
        try {
            boolean isScheduled = scheduler.checkExists(trigger.getKey());
            if (isScheduled) return;
            scheduler.scheduleJob(detail, trigger);
            log.info("Scheduled job:" + trigger.getKey());
            instantiated.set(true);
        } catch (SchedulerException e) {
            log.error(Arrays.toString(e.getStackTrace()));
            log.error(e.getMessage());
        }

    }
}
