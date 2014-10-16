package edu.qaware.cc.rest.fbbroker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import edu.qaware.cc.rest.fbbroker.dts.FacebookMessage;
import edu.qaware.cc.rest.fbbroker.dts.FacebookResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.URLEncoder;

@Path("/facebook/{keyword}")
public class FacebookBroker {

    @GET
    @Produces("text/html; charset=utf-8")
    public String getStream(
            @PathParam("keyword") String keyword) throws IOException {
        Client c = Client.create();
        WebResource r = c.resource("https://graph.facebook.com/search?type=post&q=" + URLEncoder.encode(keyword, "utf-8"));
        String responseString = r.accept(
                MediaType.APPLICATION_JSON_TYPE).
                get(String.class);
        FacebookResponse response = new ObjectMapper().readValue(responseString, FacebookResponse.class);
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body><ul>");
        for (FacebookMessage message : response.getData()) {
            sb.append("<li><b>[").append(message.getFrom()).append("]</b><i> ").append(message.getMessage()).append("</i></li>");
        }
        sb.append("</ul></body></html>");
        return sb.toString();
    }

}
