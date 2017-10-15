package de.qaware.edu.cc.bookservice;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

/**
 * Main JAX-RS REST API component. Registers all resources and JAX-RS providers.
 */
@Component
public class BookstoreAPI extends ResourceConfig {
    public BookstoreAPI() {
        super();

        register(JacksonFeature.class);

        register(BookResource.class);
        register(BookExceptionMapper.class);

        register(ManagementEndpoints.class);
        register(CORSFilter.class);
    }
}
