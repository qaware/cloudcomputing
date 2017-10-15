package de.qaware.edu.cc.bookservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Component
@Path("/api/books")
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {

    @Autowired
    private Bookshelf bookshelf;

    @GET
    public Response books(@QueryParam("title") String title) {
        Collection<Book> books = bookshelf.findByTitle(title);
        return Response.ok(books).build();
    }

    @GET
    @Path("/{isbn}")
    public Response byIsbn(@PathParam("isbn") String isbn) {
        Book book = bookshelf.findByIsbn(isbn);
        return Response.ok(book).build();
    }
}
