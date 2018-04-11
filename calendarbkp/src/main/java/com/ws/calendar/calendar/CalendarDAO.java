package com.ws.<api_name>.calendar;

import com.ws.commons.persistence.AbstractDAO;
import com.ws.commons.server.pagination.PagedList;
import io.ebean.ExpressionList;
import io.ebean.Query;

/**
 * @author Leticia Torres {@literal leticia.bertoldo@wssim.com.br}
 * @since 1.0.0 2018-04-10
 */

public class <Entity>DAO extends AbstractDAO<<Entity>DAO> {

    @Override
    public Class<<Entity>DAO> getEntityClass() { return <Entity>.class; }

    /**
     *
     * @param search filter as {@link <Entity>Search}
     * @return PagedList
     * @author Leticia Torres {@literal leticia.bertoldo@wssim.com.br}
     * @since 1.0.0 2018-04-10
     */
    public PagedList<<Entity>> search(final <Entity>Search search) {
        Validate.notNull(search, "<Entity>Search cannot be null");

        final Query<<Entity>> query = find();
        final ExpressionList<<Entity>> where = query.where();



        return getPagedList(query, search);
    }
}
