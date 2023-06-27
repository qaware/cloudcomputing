package de.qaware.cloudcomputing.tle;

import io.opentelemetry.instrumentation.annotations.SpanAttribute;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@RegisterRestClient(baseUri = "https://tle.ivanstanojevic.me/api")
public interface TleClient {

    @GET
    @Path("/tle/")
    Uni<TleSearchResult> search(@BeanParam TleSearchParameters tleSearchParameters);

    @GET
    @WithSpan
    @Path("/tle/{satelliteId}")
    Uni<TleMember> getRecord(@PathParam("satelliteId") int satelliteId);

    @GET
    @Path("/tle/{satelliteId}/propagate")
    Uni<TlePropagationResult> propagate(@PathParam("satelliteId") @SpanAttribute int satelliteId);
}
