package de.qaware.edu.cc.bookservice;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

/**
 * The REST resource for the books.
 */
@Component
@Path("/books")
@Api(value = "/books", description = "Operations about books")
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {

    @Autowired
    private Bookshelf bookshelf;

    @GET
    @ApiOperation(value = "Find books", response = Book.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Found all books")
    })
    public Response books(@ApiParam(value = "title to search")
                          @QueryParam("title") String title) {
        Collection<Book> books = bookshelf.findByTitle(title);
        return Response.ok(books).build();
    }

    @GET
    @ApiOperation(value = "Find book by ISBN", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Found the book"),
            @ApiResponse(code = 404, message = "Book not found")
    })
    @Path("/{isbn}")
    public Response byIsbn(@ApiParam(value = "ISBN to search", required = true)
                           @PathParam("isbn") String isbn) {
        Book book = bookshelf.findByIsbn(isbn);
        return Response.ok(book).build();
    }
}
