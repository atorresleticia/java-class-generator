package com.ws.calendar.recordpermission;

import com.sollar.recordpermission.queryadapter.RecordPermissionConfiguration;
import com.ws.commons.server.context.UserContext;

import javax.inject.Inject;
import java.util.*;

/**
 * @author Gustavo P. Bilert <gustavo.bilert@wssim.com.br>
 * @since 4.1.3 2017-04-26
 */
public class CalendarRecordPermissionConfiguration extends RecordPermissionConfiguration{

    private final UserContext userContext;

    static final Set<String> IGNOREDENTITIES;
    static {
        IGNOREDENTITIES = new TreeSet<>();
//        IGNOREDENTITIES.add(LocationType.class.getSimpleName());
    }

    @Inject
    public CalendarRecordPermissionConfiguration(UserContext userContext) {

        this.userContext = userContext;
    }

    @Override
    public UUID currentUserId() {
        if (userContext.getSession().getAttribute("user_id") == null){
            return null;
        }

        return UUID.fromString((String) userContext.getSession().getAttribute("user_id"));
    }

    @Override
    public Boolean currentUserIsAdministrator() {
        return (Boolean) userContext.getSession().getAttribute("user_administrator");
    }

    @Override
    public Set<String> ignoredEntities() {
        return IGNOREDENTITIES;
    }

    @Override
    public List<String> accessGroupsToReplicate() {
        return Arrays.asList("user", "company", "customerByRegion");
    }
}
