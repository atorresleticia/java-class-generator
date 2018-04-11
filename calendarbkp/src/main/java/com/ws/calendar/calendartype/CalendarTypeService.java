package com.ws.calendar.calendartype;

import com.ws.commons.persistence.AbstractDAO;
import com.ws.commons.server.AbstractService;
import com.ws.commons.server.pagination.PagedList;

import javax.inject.Inject;

/**
 * @author Leticia Torres {@literal <leticia.bertoldo@wssim.com.br>}
 * @since 6.1.0 2018-03-28
 */

public class CalendarTypeService extends AbstractService<CalendarType> {

    private final CalendarTypeDAO calendarTypeDAO;

    @Inject
    public CalendarTypeService(final CalendarTypeDAO calendarTypeDAO) {
        super(calendarTypeDAO);
        this.calendarTypeDAO = calendarTypeDAO;
    }

    @Override
    public CalendarType insert(final CalendarType entity) throws Exception {
        return super.insert(entity);
    }

    @Override
    public void update(final CalendarType entity) throws Exception {
        super.update(entity);
    }

    public PagedList<CalendarType> search(final CalendarTypeSearch search) {
        return calendarTypeDAO.search(search);
    }
}