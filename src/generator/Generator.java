package generator;

import java.io.File;

public class Generator {

    private File attributes;
    private File entity;
    private File dao;
    private File search;
    private File service;
    private File resource;
    private File validation;

    public Generator(File attributes, File entity, File dao) {
        this.attributes = attributes;
        this.entity = entity;
        this.dao = dao;
    }

    public Generator(File attributes, File entity, File dao, File search, File service, File resource, File validation) {
        this.attributes = attributes;
        this.entity = entity;
        this.dao = dao;
        this.search = search;
        this.service = service;
        this.resource = resource;
        this.validation = validation;
    }

    public File getAttributes() {
        return attributes;
    }

    public void setAttributes(File attributes) {
        this.attributes = attributes;
    }

    public File getEntity() {
        return entity;
    }

    public void setEntity(File entity) {
        this.entity = entity;
    }

    public File getDao() {
        return dao;
    }

    public void setDao(File dao) {
        this.dao = dao;
    }

    public File getSearch() {
        return search;
    }

    public void setSearch(File search) {
        this.search = search;
    }

    public File getService() {
        return service;
    }

    public void setService(File service) {
        this.service = service;
    }

    public File getResource() {
        return resource;
    }

    public void setResource(File resource) {
        this.resource = resource;
    }

    public File getValidation() {
        return validation;
    }

    public void setValidation(File validation) {
        this.validation = validation;
    }
}