package de.qaware.edu.cc.bookservice;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Create book")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created the book"),
            @ApiResponse(code = 409, message = "Book already exists")
    })
    public Response create(Book book) {
        boolean created = bookshelf.create(book);
        if (created) {
            return Response.created(URI.create("/api/books/" + book.getIsbn())).build();
        } else {
            return Response.status(Response.Status.CONFLICT).build();
        }
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

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Update book by ISBN")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Updated the book")
    })
    @Path("/{isbn}")
    public Response update(@ApiParam(value = "ISBN to search", required = true)
                           @PathParam("isbn") String isbn, Book book) {
        bookshelf.update(isbn, book);
        return Response.ok().build();
    }

    @DELETE
    @ApiOperation(value = "Delete book by ISBN")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Book deleted")
    })
    @Path("/{isbn}")
    public Response delete(@ApiParam(value = "ISBN to delete", required = true)
                           @PathParam("isbn") String isbn) {
        bookshelf.delete(isbn);
        return Response.ok().build();
    }
}
