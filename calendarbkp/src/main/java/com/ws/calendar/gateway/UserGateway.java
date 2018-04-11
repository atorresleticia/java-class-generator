package com.ws.calendar.gateway;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sollar.user.user.UserResource;
import com.ws.commons.integration.proxytextension.InjectProxy;
import org.apache.http.HttpStatus;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author ivan.reffatti
 * @since 2017/04/20
 */
public class UserGateway {

    @InjectProxy
    private UserResource userResource;

    private final ObjectMapper mapper = new ObjectMapper();

    public SimpleAuthorizationInfo getPermissions() throws IOException {

        final Response response = userResource.permission();

        if( response.getStatus() == HttpStatus.SC_OK){
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue((InputStream) response.getEntity(), SimpleAuthorizationInfo.class);
        }

        LoggerFactory.getLogger(getClass()).error("could not load permissions from rest endpoint.");
        return null;

    }



}
