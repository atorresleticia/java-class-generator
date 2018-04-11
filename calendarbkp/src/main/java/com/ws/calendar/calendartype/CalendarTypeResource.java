package com.ws.calendar.calendartype;

import com.ws.commons.server.AbstractResource;
import com.ws.commons.server.pagination.PagedList;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * @author Leticia Torres {@literal <leticia.bertoldo@wssim.com.br>}
 * @since 6.1.0 2018-03-28
 */

@Path("/calendar-types")
public class CalendarTypeResource extends AbstractResource<CalendarType, CalendarTypeSearch> {

    private final CalendarTypeService calendarTypeService;

    @Inject
    public CalendarTypeResource(CalendarTypeService calendarTypeService) {
        this.calendarTypeService = calendarTypeService;
    }

    @Override
    public Response search(CalendarTypeSearch parameters) throws Exception {
        final PagedList<CalendarType> list = calendarTypeService.search(parameters);
        return Response.status(Response.Status.OK).entity(list).build();
    }
}