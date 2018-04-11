package com.ws.<api_name>.<entityPkg>;

import com.ws.commons.persistence.AbstractDAO;
import com.ws.commons.server.pagination.PagedList;
import io.ebean.ExpressionList;
import io.ebean.Query;

/**
 * @author <author> {@literal <mail>}
 * @since <version> <date>
 */

public class <Entity>DAO extends AbstractDAO<<Entity>DAO> {

    @Override
    public Class<<Entity>DAO> getEntityClass() { return <Entity>.class; }

    /**
     *
     * @param search filter as {@link <Entity>Search}
     * @return PagedList
     * @author <author> {@literal <mail>}
     * @since <version> <date>
     */
    public PagedList<<Entity>> search(final <Entity>Search search) {
        Validate.notNull(search, "<Entity>Search cannot be null");

        final Query<<Entity>> query = find();
        final ExpressionList<<Entity>> where = query.where();



        return getPagedList(query, search);
    }
}