package com.daou.jiracollector.jiradatamaker.scheduler;

import java.text.ParseException;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import com.daou.jiracollector.jiradatamaker.PropertiesController;

public class CronTriggerDataMaker {

	/**
	 * 스케쥴러를 사용하여 jiraData를 수집 하는 scheduler
	 *
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		PropertiesController procon = new PropertiesController();

		String timeVersion = procon.getProperty("scheduler.version.sec") + " "
				+ procon.getProperty("scheduler.version.min") + " "
				+ procon.getProperty("scheduler.version.hour") + " "
				+ procon.getProperty("scheduler.version.dayOfMonth") + " "
				+ procon.getProperty("scheduler.version.month") + " "
				+ procon.getProperty("scheduler.version.dayOfWeek");

		schedulerMakeVersions(timeVersion);
		schedulerMakeIssuetype(timeVersion);

		String timeIssuetypeTransition = procon.getProperty("scheduler.issuetypeTransition.sec") + " "
				+ procon.getProperty("scheduler.issuetypeTransition.min") + " "
				+ procon.getProperty("scheduler.issuetypeTransition.hour") + " "
				+ procon.getProperty("scheduler.issuetypeTransition.dayOfMonth") + " "
				+ procon.getProperty("scheduler.issuetypeTransition.month") + " "
				+ procon.getProperty("scheduler.issuetypeTransition.dayOfWeek");

		schedulerIssueTransition(timeIssuetypeTransition);

		String timeIssueMeasure = procon.getProperty("scheduler.issueMeasure.sec") + " "
				+ procon.getProperty("scheduler.issueMeasure.min") + " "
				+ procon.getProperty("scheduler.issueMeasure.hour") + " "
				+ procon.getProperty("scheduler.issueMeasure.dayOfMonth") + " "
				+ procon.getProperty("scheduler.issueMeasure.month") + " "
				+ procon.getProperty("scheduler.issueMeasure.dayOfWeek");

		schedulerIssueMeasure(timeIssueMeasure);
	}

	private static void schedulerIssueTransition(String schedulerDate) throws SchedulerException, ParseException {

		JobDetail job = new JobDetail();
		job.setName("issueTypeTransition");
		job.setJobClass(JobMakeIssueTypeTransition.class);

		CronTrigger trigger = new CronTrigger();
		trigger.setName("issueTypeTransitionTrigger");

		trigger.setCronExpression(schedulerDate);

		// schedule it
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.start();
		scheduler.scheduleJob(job, trigger);
	}

	private static void schedulerIssueMeasure(String schedulerDate) throws SchedulerException, ParseException {

		JobDetail job = new JobDetail();
		job.setName("IssueMeasureStatus");
		job.setJobClass(JobMakeIssueMeasureStatus.class);

		CronTrigger trigger = new CronTrigger();
		trigger.setName("IssueMeasureStatusTrigger");

		trigger.setCronExpression(schedulerDate);

		// schedule it
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.start();
		scheduler.scheduleJob(job, trigger);
	}

	private static void schedulerMakeVersions(String schedulerDate) throws SchedulerException, ParseException {

		JobDetail job = new JobDetail();
		job.setName("makeVersions");
		job.setJobClass(JobMakeVersions.class);

		CronTrigger trigger = new CronTrigger();
		trigger.setName("makeVersionsTrigger");

		trigger.setCronExpression(schedulerDate);

		// schedule it
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.start();
		scheduler.scheduleJob(job, trigger);
	}

	private static void schedulerMakeIssuetype(String schedulerDate) throws SchedulerException, ParseException {

		JobDetail job = new JobDetail();
		job.setName("makeIssuetype");
		job.setJobClass(JobMakeIssuetype.class);

		CronTrigger trigger = new CronTrigger();
		trigger.setName("makeIssuetypeTrigger");

		trigger.setCronExpression(schedulerDate);

		// schedule it
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.start();
		scheduler.scheduleJob(job, trigger);
	}
}
