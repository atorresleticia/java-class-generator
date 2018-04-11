package com.ws.calendar.recordpermission.schedulerservice;

import org.apache.deltaspike.scheduler.spi.Scheduler;
import org.quartz.Job;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

/**
 * Producer for Scheduler&lt;Job&gt;
 *
 * @author Diego Peliser <diego.peliser@wssim.com.br>
 * @version 6.0.0
 * @since 17-12-04
 */
public class QuartzSchedulerProducer {

    /**
     * Produces a Scheduler&lt;Job&gt; by simply casting the received Scheduler.
     * @param scheduler Default scheduler instance to be returned as Scheduler&lt;Job&gt;
     * @return The Scheduler&lt;Job&gt;
     */
    @Produces
    @ApplicationScoped
    protected Scheduler<Job> produceScheduler(final Scheduler scheduler) {
        return scheduler;
    }

}
