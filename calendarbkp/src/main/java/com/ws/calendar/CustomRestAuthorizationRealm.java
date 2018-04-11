package com.ws.calendar;

import com.ws.calendar.gateway.UserGateway;
import com.ws.commons.server.accesscontrol.RestAuthorizationRealm;
import org.apache.shiro.authz.SimpleAuthorizationInfo;

import javax.enterprise.inject.spi.CDI;
import java.io.IOException;

/**
 *
 * Class created with the purpose to customize API call to user service and load permissions
 *
 * @author ivan.reffatti
 * @since 2017/04/20
 */
public class CustomRestAuthorizationRealm extends RestAuthorizationRealm {

    @Override
    protected SimpleAuthorizationInfo getSimpleAuthorizationInfo() throws IOException {
        return CDI.current().select(UserGateway.class).get().getPermissions();
    }
}
