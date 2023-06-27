package de.qaware.cloudcomputing.satellite;

import com.github.amsacode.predict4java.SatPos;
import de.qaware.cloudcomputing.tle.TleClient;
import io.opentelemetry.instrumentation.annotations.SpanAttribute;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.smallrye.mutiny.Uni;
import lombok.extern.jbosslog.JBossLog;
import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.RotationConvention;
import org.apache.commons.math3.geometry.euclidean.threed.SphericalCoordinates;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import java.util.AbstractMap;
import java.util.Map;

import static org.apache.commons.math3.geometry.euclidean.threed.Vector3D.PLUS_J;

@JBossLog
@Consumes(MediaType.APPLICATION_JSON)
@Path("/satellite")
@ApplicationScoped
public class SatelliteResource {

    @Inject
    @RestClient
    TleClient tleClient;

    @GET
    @WithSpan
    @Path("/{satelliteId}")
    public Uni<SatPos> getPrediction(@PathParam("satelliteId") @SpanAttribute int satelliteId) {
        return tleClient.predict(satelliteId)
            .invoke(this::log)
            .onFailure().invoke(log::error);

    }

    private void log(SatPos satPos) {
        log.info(satPos);
    }

}
