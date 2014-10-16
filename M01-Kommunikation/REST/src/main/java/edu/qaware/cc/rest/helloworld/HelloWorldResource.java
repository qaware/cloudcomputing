package edu.qaware.cc.rest.helloworld;

import edu.qaware.cc.rest.helloworld.dts.ResponseMessage;

import javax.ws.rs.*;
import java.io.IOException;
import java.util.Date;

@Path("/hello/{name}")
public class HelloWorldResource {

    @GET
    @Produces("application/json")
    public ResponseMessage getMessage(
            @DefaultValue("Hallo") @QueryParam("salutation") String salutation,
            @PathParam("name") String name) throws IOException {
        ResponseMessage response = new ResponseMessage(new Date().toString(), salutation + " " + name);
        return response;
    }

}
