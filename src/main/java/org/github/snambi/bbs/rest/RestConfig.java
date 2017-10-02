package org.github.snambi.bbs.rest;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

/**
 */
@Component
@ApplicationPath("/api/v1")
public class RestConfig extends ResourceConfig {

    public RestConfig() {
        register(RestController.class);
    }

}