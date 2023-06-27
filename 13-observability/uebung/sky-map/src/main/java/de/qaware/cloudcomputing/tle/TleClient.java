package de.qaware.cloudcomputing.tle;

import com.github.amsacode.predict4java.SatPos;
import io.opentelemetry.instrumentation.annotations.SpanAttribute;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/prediction")
@RegisterRestClient(baseUri = "http://localhost:8080")
public interface TleClient {

    @GET
    @Path("/{satelliteId}")
    Uni<SatPos> predict(@PathParam("satelliteId") @SpanAttribute int satelliteId);
}
