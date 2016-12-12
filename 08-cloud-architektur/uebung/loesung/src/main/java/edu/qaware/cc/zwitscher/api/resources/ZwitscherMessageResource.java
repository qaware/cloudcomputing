package edu.qaware.cc.zwitscher.api.resources;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import edu.qaware.cc.zwitscher.api.entities.ZwitscherMessage;

import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;

@Api(value = "/messages", description = "Zwitscher Messages")
@Path("/messages")
public class ZwitscherMessageResource {
    
    @Path("/random")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Eine beliebige Nachricht zurückgeben", 
            notes = "Diese Methode dient nur zu Demonstrationszwecken. "
                    + "Sie gibt eine beliebige Nachricht zurück.")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Es kann keine vernünftige Nachricht generiert werden"),
        @ApiResponse(code = 404, message = "Die generierte Nachricht ist unvernünftig") 
    })    
    public ZwitscherMessage getRandomMessage(){
        return new ZwitscherMessage("YO!");
    }
    
    @GET
    @Produces("application/json")  
    @ApiOperation(value = "Den aktuellen Nachrichtenstrom zurückgeben",
            response = ZwitscherMessage.class,
            responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 404, message = "Es können keine Nachrichten geholt werden")
    })    
    public List<ZwitscherMessage> getMessageStream(
            @DefaultValue("") @QueryParam("keyword") String keyword){
        List<ZwitscherMessage> messages = new ArrayList<ZwitscherMessage>();
        messages.add(new ZwitscherMessage("yo"));
        return messages;
    }
}