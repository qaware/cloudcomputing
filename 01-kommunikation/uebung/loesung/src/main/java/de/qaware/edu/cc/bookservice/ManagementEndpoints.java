package de.qaware.edu.cc.bookservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.HealthEndpoint;
import org.springframework.boot.actuate.endpoint.InfoEndpoint;
import org.springframework.boot.actuate.endpoint.MetricsEndpoint;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/admin/")
@Produces({MediaType.APPLICATION_JSON})
@Component
public class ManagementEndpoints {
    @Autowired
    private HealthEndpoint health;

    @Autowired
    private MetricsEndpoint metrics;

    @Autowired
    private InfoEndpoint info;

    @GET
    @Path("/health")
    public Object getHealth() {
        return health.invoke();
    }

    @GET
    @Path("/metrics")
    public Object getMetrics() {
        return this.metrics.invoke();
    }

    @GET
    @Path("/info")
    public Object getInfo() {
        return this.info.invoke();
    }

    @GET
    @Path("/metrics/{name:.*}")
    public Object getMetric(@PathParam("name") final String name) {
        final Object value = this.metrics.invoke().get(name);
        if (value == null) {
            throw new NotFoundException("No such metric: " + name);
        }
        return value;
    }

}
