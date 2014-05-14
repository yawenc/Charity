package com.bertazoli.charity.server.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.bertazoli.charity.server.businesslogic.DonateBusinessLogic;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ScheduleJob implements Job {
    @Inject DonateBusinessLogic donateBL;

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        System.out.println(donateBL);
        System.out.println("here");
    }
}
