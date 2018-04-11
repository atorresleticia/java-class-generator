package com.ws.calendar.recordpermission.schedulerservice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ws.accounts.model.AccessGroupSearch;
import com.ws.accounts.recordpermission.AccessGroupResource;
import com.sollar.recordpermission.accessgroup.AccessGroup;
import com.sollar.recordpermission.accessgroup.AccessGroupConsumerDelegate;
import com.sollar.recordpermission.accessgroup.AccessGroupDAO;
import com.sollar.recordpermission.queryadapter.RecordPermissionConfiguration;
import com.ws.commons.event.Updated;
import com.ws.commons.integration.proxytextension.InjectProxy;
import com.ws.commons.server.context.KafkaContext;
import com.ws.commons.server.json.ObjectMapperResolver;
import com.ws.commons.server.pagination.PagedList;
import org.apache.deltaspike.cdise.api.ContextControl;
import org.apache.http.HttpStatus;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.time.OffsetDateTime;

/**
 * Replicates AccessGroup data from other services
 * @author Gustavo P. Bilert
 * @since 2017-05-31
 */
public class AccessGroupReplicator {

    @InjectProxy
    private AccessGroupResource resource;

    @Inject
    private AccessGroupDAO accessGroupDAO;

    @Inject
    private AccessGroupConsumerDelegate delegate;

    @Inject
    @Updated
    private Event<AccessGroup> updateEvent;

    @Inject
    private ContextControl contextControl;

    @Inject
    private RecordPermissionConfiguration recordPermissionConfiguration;


    /**
     * Replicates AccessGroup data for each tenant, based on the last modification time.
     * @param tenant The tenant
     * @throws Exception
     */
    public void replicateForTenant(final String tenant) throws Exception {
        try {

            KafkaContext.get().setTenantId(tenant);
            contextControl.startContext(RequestScoped.class);

            AccessGroupSearch search;
            PagedList<AccessGroup> pagedList;

            for(String groupName: recordPermissionConfiguration.accessGroupsToReplicate()) {

                OffsetDateTime latestUpdate = accessGroupDAO.findLatestUpdatedAt(groupName);

                search = new AccessGroupSearch();
                search.setGroupName(groupName);
                if (latestUpdate != null) {
                    search.setUpdatedSince(latestUpdate.toString());
                }

                final Response response = resource.search(search);
                if (response.getStatus() == HttpStatus.SC_OK && response.hasEntity()) {

                    ObjectMapper objectMapper = ObjectMapperResolver.createMapper().jacksonMapper();
                        pagedList = objectMapper.readValue((InputStream) response.getEntity(), new TypeReference<PagedList<AccessGroup>>() {
                    });

                    if(pagedList.getCount() > 0) {
                        // Update all in one transaction so changes do not get discarded
                        accessGroupDAO.beginTransaction();
                        try {
                            for (AccessGroup accessGroup :
                                    pagedList.getItems()) {
                                delegate.update(accessGroup);
                                updateEvent.fire(accessGroup);
                            }
                            accessGroupDAO.commitTransaction();
                        } finally {
                            accessGroupDAO.endTransaction();
                        }
                    }
                }
            }
        } finally {
            contextControl.stopContext(RequestScoped.class);
            contextControl.startContext(RequestScoped.class);
            KafkaContext.get().setTenantId(null);
        }

    }
}
