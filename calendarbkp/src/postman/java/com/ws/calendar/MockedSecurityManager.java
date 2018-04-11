package com.ws.calendar;

import com.ws.commons.server.context.UserContext;
import org.apache.deltaspike.core.api.config.ConfigResolver;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

/**
 *
 * <p>Helper to mock Shiro Security manager on tests and inject in the right way the SecurityManager</p>
 *
 * @author Evaristo W. Benfatti
 * @since v0.1.0 2017-11-22
 *
 */
public class MockedSecurityManager extends DefaultWebSecurityManager {

    /**
     * Create a dummy session only to postman tests
     *
     * @param subjectContext context
     * @return {@link Subject}
     */
    @Override
    public Subject createSubject(final SubjectContext subjectContext) {
        // Create Session with max timeout allowed
        SimpleSession session = new SimpleSession();

        session.setId(UUID.randomUUID());
        session.setAttribute(UserContext.TENANT_ATTRIBUTE, ConfigResolver.getProjectStageAwarePropertyValue("db.schema.IntegrationTest"));
        session.setAttribute("user_id", "7636f630-2358-4cf4-a290-2ec0b13ea47c");
        session.setAttribute("user_name", "admin");
        session.setAttribute("user_administrator", true);
        session.setExpired(false);
        session.setLastAccessTime(Date.from(Instant.now()));
        session.setTimeout(Long.MAX_VALUE);
        session.setAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY, new SimplePrincipalCollection("", ""));

        subjectContext.setSession(session);
        subjectContext.setAuthenticated(true);

        return super.createSubject(subjectContext);
    }

    /**
     * Do nothing, just disable the default implementation
     */
    @Override
    protected void save(Subject subject) {
    }

    /**
     * Do nothing, just disable the default implementation
     */
    @Override
    public void checkPermission(PrincipalCollection principals, String permission) {
    }

}
