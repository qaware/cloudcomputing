package de.qaware.edu.cc.bookservice;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Custom exception mapper to handle NOT_FOUND exceptions.
 */
@Provider
public class BookExceptionMapper implements ExceptionMapper<BookNotFoundException> {
    @Override
    public Response toResponse(BookNotFoundException e) {
        return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
    }
}
