package com.ws.<apiName>.calendar;

import com.ws.commons.server.AbstractService;
import com.ws.commons.server.pagination.PagedList;

import javax.inject.Inject;

/**
 * @author Leticia Torres {@literal leticia.bertoldo@wssim.com.br}
 * @since 1.0.0 2018-04-10
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
