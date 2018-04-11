package com.ws.calendar.recordpermission;

import com.sollar.recordpermission.accessgroup.PermissionChangesQuery;
import com.sollar.recordpermission.queryadapter.RecordPermissionConfiguration;
import com.ws.commons.server.context.UserContext;
import com.ws.commons.sync.SyncQueryModifier;
import io.ebean.EbeanServer;
import io.ebean.Expression;
import io.ebean.Query;

import javax.inject.Inject;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Modifies the Sync query to include permission changes on the results.
 * @author Gustavo P. Bilert
 * @since 2017-05-19
 */
public class RecordPermissionSyncQueryModifier implements SyncQueryModifier {

    @Inject
    PermissionChangesQuery permissionChangesQuery;

    @Inject
    UserContext userContext;

    @Inject
    RecordPermissionConfiguration recordPermissionConfiguration;

    @Override
    public <T> Query<T> modifySyncQuery(Query<T> query, Class<T> entityClass, Map<String, String[]> queryParams, EbeanServer ebeanServer) {

        // Disable permission changes for admins
        if(recordPermissionConfiguration.currentUserIsAdministrator()){
            return query;
        }

        UUID userId = UUID.fromString((String) userContext.getSession().getAttribute("user_id"));
        String[] updatedAtInterval = queryParams.get("updatedAt");
        if(updatedAtInterval==null){
            return query;
        }

        OffsetDateTime since = OffsetDateTime.parse(updatedAtInterval[0].split(",")[0]);
        if(!queryParams.containsKey("deleted")){

            Expression permissionGrants = permissionChangesQuery.grantedPermissionsSince(query, userId, since, ebeanServer);
            query.select("id");

            return ebeanServer.find(entityClass).where().or()
                    .in("id", query)
                    .add(permissionGrants)
                    .endOr().query();
        } else {

            List<UUID> permissionRevokes = permissionChangesQuery.revokedPermissionsSince(query, userId, since, ebeanServer);

            // this optimization can be enabled after the query is well tested
//            if(permissionRevokes.isEmpty()){
//                return query;
//            }

            recordPermissionConfiguration.disableVerification();

            query.select("id");

            return ebeanServer.find(entityClass)
            .setIncludeSoftDeletes()
            .select("id, updatedAt")
            .setDisableLazyLoading(true)
                    .where().or()
                    .in("id", query)
                    .in("id", permissionRevokes)
                    .endOr().query();

        }
    }
}
