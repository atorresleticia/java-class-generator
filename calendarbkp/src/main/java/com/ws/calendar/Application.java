package com.ws.calendar;

import com.ws.commons.server.DefaultConfig;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * @author Sezar Caldeira <sezar.caldeira@wssim.com.br>
 * @since 1.0.0 2016-02-02
 */
public class Application extends ResourceConfig {

    /**
     * @author Sezar Caldeira <sezar.caldeira@wssim.com.br>
     * @since 1.0.0 2016-02-02
     */
    public Application() {

        DefaultConfig.jerseyConfig(this, getClass().getPackage().getName());

    }
}