package com.bertazoli.charity.server.guice;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.bertazoli.charity.server.jobs.ScheduleJob;
import com.google.inject.PrivateModule;
import com.google.inject.multibindings.Multibinder;

public class ScheduledJobModule extends PrivateModule {
    private Scheduler scheduler = null;

    @Override
    protected void configure() {
        Multibinder<ScheduleJob> scheduleJobBinder = Multibinder.newSetBinder(binder(), ScheduleJob.class);
        scheduleJobBinder.addBinding().to(ScheduleJob.class);
        
        JobDetail jd = JobBuilder.newJob(ScheduleJob.class).build();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(this.getClass().getName()).withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * * * ?")).build();
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.scheduleJob(jd, trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
