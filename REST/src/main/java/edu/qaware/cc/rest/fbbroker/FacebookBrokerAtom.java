package edu.qaware.cc.rest.fbbroker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import edu.qaware.cc.rest.fbbroker.dts.FacebookMessage;
import edu.qaware.cc.rest.fbbroker.dts.FacebookResponse;
import org.apache.abdera.Abdera;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;

@Path("/facebook/feed/{keyword}")
public class FacebookBrokerAtom {

    @GET
    @Produces("application/atom+xml; charset=utf-8")
    public String getStream(
            @PathParam("keyword") String keyword) throws IOException {

        Client c = Client.create();
        WebResource r = c.resource("https://graph.facebook.com/search?type=post&q=" + URLEncoder.encode(keyword));
        String responseString = r.accept(
                MediaType.APPLICATION_JSON_TYPE).
                get(String.class);
        FacebookResponse response = new ObjectMapper().readValue(responseString, FacebookResponse.class);

        Abdera abdera = new Abdera();
        Feed feed = abdera.newFeed();
        feed.setTitle("Facebook Feed");
        feed.setUpdated(new Date());

        for (FacebookMessage message : response.getData()) {
            Entry entry = feed.addEntry();
            entry.setId(message.getId());
            entry.setTitle("Facebook " + message.getType() + "  from " + message.getFrom());
            entry.setSummary(message.getMessage());
        }

        return feed.toString();
    }

}
