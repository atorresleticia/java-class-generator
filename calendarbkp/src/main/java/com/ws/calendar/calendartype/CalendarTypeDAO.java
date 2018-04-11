package com.ws.calendar.calendartype;

import com.ws.commons.persistence.AbstractDAO;
import com.ws.commons.server.pagination.PagedList;
import io.ebean.ExpressionList;
import io.ebean.Query;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/**
 * @author Leticia Torres {@literal <leticia.bertoldo@wssim.com.br>}
 * @since 6.1.0 2018-03-28
 */

public class CalendarTypeDAO extends AbstractDAO<CalendarType> {

    @Override
    public Class<CalendarType> getEntityClass() { return CalendarType.class; }

    /**
     *
     * @param search filters as {@link CalendarTypeSearch}
     * @return PagedList
     * @author Leticia Torres {@literal <leticia.bertoldo@wssim.com>}
     * @since 6.1.0 2018-03-28
     */
    public PagedList<CalendarType> search(final CalendarTypeSearch search) {
        Validate.notNull(search, "CalendarTypeSearch cannot be null");

        final Query<CalendarType> query = find();
        final ExpressionList<CalendarType> where = query.where();

        if (StringUtils.isNotBlank(search.getGeneralSearch())) {
            where.icontains("description", search.getGeneralSearch());
        }

        final Boolean[] active = search.getActive();
        if (active != null && active.length == 1) {
            where.eq("active", active[0]);
        }

        if (StringUtils.isNotBlank(search.getColor())) {
            where.eq("color", search.getColor());
        }

        if (StringUtils.isNotBlank(search.getStandard().toString()))
        {
            where.eq("standard", search.getStandard());
        }

        return getPagedList(query, search);
    }
}