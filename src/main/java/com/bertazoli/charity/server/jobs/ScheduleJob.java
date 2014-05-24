package com.bertazoli.charity.server.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.bertazoli.charity.server.businesslogic.DrawBusinessLogic;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ScheduleJob implements Job {
    @Inject DrawBusinessLogic drawBL;

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        System.out.println(drawBL);
    }
}
