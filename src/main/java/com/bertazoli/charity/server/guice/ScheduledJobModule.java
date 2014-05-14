package com.bertazoli.charity.server.guice;

import org.quartz.Scheduler;

import com.google.inject.PrivateModule;

public class ScheduledJobModule extends PrivateModule {
    private Scheduler scheduler = null;

    @Override
    protected void configure() {
//        JobDetail jd = JobBuilder.newJob(ScheduleJob.class).build();
//        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(this.getClass().getName()).withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * * * ?")).build();
//        try {
//            scheduler = StdSchedulerFactory.getDefaultScheduler();
//            scheduler.scheduleJob(jd, trigger);
//            scheduler.start();
//        } catch (SchedulerException e) {
//            e.printStackTrace();
//        }
    }
}
