package de.qaware.edu.cc.bookservice;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Collection;

/**
 * The REST resource for the books.
 */
@Component
@Path("/books")
@Api
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
            @ApiResponse(code = 200, message = "Updated the book", response = Book.class)
    })
    @Path("/{isbn}")
    public Response update(@ApiParam(value = "ISBN to search", required = true)
                           @PathParam("isbn") String isbn, Book book) {
        Book updated = bookshelf.update(isbn, book);
        return Response.ok(updated).build();
    }

    @DELETE
    @ApiOperation(value = "Delete book by ISBN")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Book deleted")
    })
    @Path("/{isbn}")
    public Response delete(@ApiParam(value = "ISBN to delete", required = true)
                           @PathParam("isbn") String isbn) {
        bookshelf.delete(isbn);
        return Response.noContent().build();
    }
}
