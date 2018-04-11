package com.ws.calendar.recordpermission.schedulerservice;

import org.apache.deltaspike.core.api.config.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * Run scheduled tasks to replicate data from other services
 * @author Gustavo P. Bilert
 * @since 2017-05-31
 */
@ApplicationScoped
public class SchedulerService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private static final String SCHEDULER_TENANTS = "scheduler.tenants";

    private static final String SERVICE_INTEGRATION_ENABLE = "service.integration.enable";

    private final String schedulerTenants;

    private final String serviceIntegrationEnable;

    private final AccessGroupReplicator accessGroupReplicator;

    private final SchedulerServiceSessionController scheduleServiceSessionController;

    /**
     * Constructor with dependency injection
     * @param schedulerTenants The tenant to run the replication for, comma separated
     * @param accessGroupReplicator The class responsible for the replication for each tenant
     * @param scheduleServiceSessionController Utility to authenticate and create a session for the request
     */
    @Inject
    public SchedulerService(@ConfigProperty(name = SCHEDULER_TENANTS) final String schedulerTenants,
                            @ConfigProperty(name = SERVICE_INTEGRATION_ENABLE, defaultValue = "true")
                            final String serviceIntegrationEnable,
                            final AccessGroupReplicator accessGroupReplicator,
                            final SchedulerServiceSessionController scheduleServiceSessionController) {
        this.schedulerTenants = schedulerTenants;
        this.serviceIntegrationEnable = serviceIntegrationEnable;
        this.accessGroupReplicator = accessGroupReplicator;
        this.scheduleServiceSessionController = scheduleServiceSessionController;
    }

    /**
     * Run the replication process for all configured tenants
     */
    public void run() {
        Thread.setDefaultUncaughtExceptionHandler((thread, exception) -> {
            LOGGER.error(String.format("SchedulerService: Error in Thread %s", thread));
            LOGGER.error(exception.getMessage(), exception);
        });

        Thread.currentThread().setName("SchedulerService Pool thread");
        final Boolean isEnabled = Boolean.valueOf(serviceIntegrationEnable);
        if (isEnabled && !schedulerTenants.isEmpty()) {
            final String[] tenants = schedulerTenants.split(",");
            for (String tenant : tenants) {
                try {
                    tenant = tenant.trim();

                    scheduleServiceSessionController.createSession(tenant);

                    accessGroupReplicator.replicateForTenant(tenant);
                } catch (final Throwable e) {
                    // Catch throwable because if one tenant causes an OutOfMemoryError
                    // it would prevent the other tenants from replicating,
                    // while if we catch it, the system can garbage collect its objects
                    // and be able to handle the next tenants
                    LOGGER.error(String.format("SchedulerService: Error replicating for tenant %s", tenant));
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
    }
}
