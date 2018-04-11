package com.ws.<apiName>.<entityPkg>;

import com.ws.commons.server.AbstractService;
import com.ws.commons.server.pagination.PagedList;

import javax.inject.Inject;

/**
 * @author <author> {@literal <mail>}
 * @since <version> <date>
 */

public class <Entity>Service extends AbstractService<<Entity>> {
    
    private final <Entity>DAO <entity>DAO;

    @Inject
    public <Entity>Service(final <Entity>DAO <entity>DAO) {
        super(<entity>DAO);
        this.<entity>DAO = <entity>DAO;
    }

    @Override
    public <Entity> insert(final <Entity> entity) throws Exception {
        return super.insert(entity);
    }

    @Override
    public void update(final <Entity> entity) throws Exception {
        return super.upadte(entity);
    }

    public PagedList<<Entity>> search(final <Entity>Search search) { return <entity>DAO.search(search); }
}