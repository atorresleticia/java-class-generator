package com.ws.calendar.recordpermission.schedulerservice;

import com.ws.commons.server.context.UserContext;
import com.ws.shiro.RedisManager;
import com.ws.shiro.RedisSessionDAO;
import org.apache.deltaspike.core.api.config.ConfigProperty;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;

import static org.apache.shiro.util.ThreadContext.SUBJECT_KEY;

/**
 * This Utility Bean class is used to generate a fake session at Redis to the Scheduler process.
 *
 * @author Evaristo W. Benfatti
 * @since 1.0.0 Created on 10/05/2017.
 */
@ApplicationScoped
public class SchedulerServiceSessionController {

    private static final String SCHEDULER_USER = "scheduler.user";

    @Inject
    @ConfigProperty(name = SCHEDULER_USER, defaultValue = "admin@teste.com")
    private String schedulerUser;

    @Inject
    @SchedulerServiceQualifier
    private RedisSessionDAO redisSessionDAO;

    /**
     * Method responsible to create the Session to the Scheduler process.
     *
     * After calling this method the {@link org.apache.shiro.SecurityUtils} Utility will be associated with a new
     * session/subject and tenant passed as parameter.
     *
     * @param tenant which Session to be created must be associated
     */
    public void createSession(final String tenant) {

        // Create Session with max timeout allowed
        SimpleSession session = new SimpleSession();

        session.setId(tenant);
        session.setAttribute(UserContext.TENANT_ATTRIBUTE, tenant);
        session.setExpired(false);
        session.setLastAccessTime(Date.from(Instant.now()));
        session.setTimeout(Long.MAX_VALUE);

        // Create subject faking authenticated once it process is internal
        Subject subject = new Subject.Builder(new DefaultSecurityManager())
                .session(session)
                .authenticated(true)
                .contextAttribute(UserContext.TENANT_ATTRIBUTE, tenant)
                .principals(new SimplePrincipalCollection(schedulerUser, ""))
                .buildSubject();

        // Set Shiro Context locally with credentials created
        ThreadContext.setResources(Collections.singletonMap(SUBJECT_KEY, subject));

        // Save at the Redis
        redisSessionDAO.update(session);

    }

    /**
     * Cdi Produces Method used to instantiate {@link RedisSessionDAO} to the Scheduler process.
     *
     * @return {@link RedisSessionDAO} instantiated
     */
    @Produces
    @SchedulerServiceQualifier
    public RedisSessionDAO createRedisSessionDAO() {

        RedisManager redisManager = new RedisManager();
        redisManager.init();

        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager);

        return redisSessionDAO;

    }

}
