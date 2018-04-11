package com.ws.<api>.<entityPkg>;

import com.ws.commons.server.AbstractResource;
import com.ws.commons.server.pagination.PagedList;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * @author <author> {@literal <mail>}
 * @since <version> <date>
 */

@Path(<resourcePath>)
public class <Entity>Resource extends AbstractResource<<Entity>, <Entity>Search> {

    private final <Entity>Service <entity>Service;

    @Inject
    public <Entity>Resource (<Entity>Service <entity>Service) {
        this.<entity>Service = <entity>Service;
    }

    @Override
    public Response search(<Entity>Search parameters) throws Exception {
        final PagedList<Entity> list = <entity>Service.search(parameters);
        return Response.status(Response.Status.OK).entity(list).build();
    }
}