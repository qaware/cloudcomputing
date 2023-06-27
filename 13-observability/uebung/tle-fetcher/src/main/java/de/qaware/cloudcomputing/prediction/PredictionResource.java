package de.qaware.cloudcomputing.prediction;

import com.github.amsacode.predict4java.GroundStationPosition;
import com.github.amsacode.predict4java.PassPredictor;
import com.github.amsacode.predict4java.SatPos;
import com.github.amsacode.predict4java.TLE;
import de.qaware.cloudcomputing.parse.TleParser;
import de.qaware.cloudcomputing.tle.TleClient;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.instrumentation.annotations.SpanAttribute;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import lombok.extern.jbosslog.JBossLog;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.sql.Date;
import java.time.LocalDate;

@JBossLog
@ApplicationScoped
@Path("/prediction")
public class PredictionResource {

    @Inject
    @RestClient
    TleClient tleClient;

    @Inject
    TleParser tleParser;

    @Inject
    Tracer tracer;

    @GET
    @WithSpan
    @Path("/{satelliteId}")
    public Uni<SatPos> predict(@PathParam("satelliteId") @SpanAttribute int satelliteId) {
        GroundStationPosition groundStationPosition = new GroundStationPosition(47, 12, 400, "Rosenheim");

        return tleClient.getRecord(satelliteId)
            .onItem()
            .transform(tleMember -> tleParser.parseTLE(tleMember))
            .onItem()
            .transform(Unchecked.function(tle -> new PassPredictor(tle, groundStationPosition)))
            .onItem()
            .transform(Unchecked.function(passPredictor -> passPredictor.getSatPos(Date.valueOf(LocalDate.now()))));
    }

}
