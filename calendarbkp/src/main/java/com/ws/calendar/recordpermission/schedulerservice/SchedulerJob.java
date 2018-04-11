package com.ws.calendar.recordpermission.schedulerservice;

import org.apache.deltaspike.scheduler.api.Scheduled;
import org.apache.deltaspike.scheduler.spi.Scheduler;
import org.jboss.weld.environment.se.events.ContainerInitialized;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

/**
 * Class responsible to schedule the {@link SchedulerService} bean.
 *
 * @author Evaristo W. Benfatti
 * @since 1.0.0 Created on 10/05/2017.
 */
@ApplicationScoped
public class SchedulerJob {

    private final Scheduler<Job> jobScheduler;
    private final Instance<AccessGroupReplicator> accessGroupReplicatorInstance;

    @Inject
    public SchedulerJob(final Scheduler<Job> jobScheduler, final Instance<AccessGroupReplicator> accessGroupReplicatorInstance) {
        this.jobScheduler = jobScheduler;
        this.accessGroupReplicatorInstance = accessGroupReplicatorInstance;
    }

    /**
     * Method responsible to register the job right after the initializing time
     *
     * @param event Cdi event thrown right after container initializing
     */
    public void registerJobs(@Observes final ContainerInitialized event) {
        if (!accessGroupReplicatorInstance.isUnsatisfied()) {
            // Start job manually avoiding to wait the first schedule timeout
            this.jobScheduler.registerNewJob(InternalScheduledJob.class);
            this.jobScheduler.startJobManually(InternalScheduledJob.class);
        }
    }

    /**
     * Internal class used to allow managing {@link SchedulerService} like a scheduler.
     * Configure through deltaspike property "service.integration.schedule"
     */
    @Scheduled(cronExpression = "{service.integration.schedule}", onStartup = false)
    public static class InternalScheduledJob implements Job {

        private final SchedulerService schedulerService;

        @Inject
        public InternalScheduledJob(SchedulerService schedulerService) {
            this.schedulerService = schedulerService;
        }

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            schedulerService.run();
        }

    }

}
