package com.ws.calendar.calendar;

import com.ws.commons.server.AbstractResource;
import com.ws.commons.server.pagination.PagedList;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * @author Leticia Torres {@literal leticia.bertoldo@wssim.com.br}
 * @since 1.0.0 2018-04-10
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
