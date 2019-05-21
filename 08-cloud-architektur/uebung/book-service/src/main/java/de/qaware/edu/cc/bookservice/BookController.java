package de.qaware.edu.cc.bookservice;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping(value = "/api/books", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController {
    private final Bookshelf bookshelf;

    @Autowired
    public BookController(Bookshelf bookshelf) {
        this.bookshelf = bookshelf;
    }

    @GetMapping
    @ApiOperation(value = "Find books", response = Book.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Found all books")
    })
    public Collection<Book> books(@ApiParam(value = "title to search") @RequestParam(value = "title", required = false) String title) {
        return bookshelf.findByTitle(title);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create book")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created the book"),
            @ApiResponse(code = 409, message = "Book already exists")
    })
    public ResponseEntity<Void> create(@RequestBody Book book) {
        boolean created = bookshelf.create(book);
        if (created) {
            return ResponseEntity.created(URI.create("/api/books/" + book.getIsbn())).build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping(value = "/{isbn}")
    @ApiOperation(value = "Find book by ISBN", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Found the book"),
            @ApiResponse(code = 404, message = "Book not found")
    })
    public Book byIsbn(@ApiParam(value = "ISBN to search") @PathVariable("isbn") String isbn) {
        return bookshelf.findByIsbn(isbn);
    }

    @PutMapping(value = "/{isbn}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update book by ISBN")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Updated the book")
    })
    @ResponseStatus(HttpStatus.OK)
    public void update(
            @ApiParam(value = "ISBN to search") @PathVariable("isbn") String isbn,
            @RequestBody Book book
    ) {
        bookshelf.update(isbn, book);
    }

    @DeleteMapping("/{isbn}")
    @ApiOperation(value = "Delete book by ISBN")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Book deleted")
    })
    @ResponseStatus(HttpStatus.OK)
    public void delete(@ApiParam(value = "ISBN to delete") @PathVariable("isbn") String isbn) {
        bookshelf.delete(isbn);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity handleBookNotFoundException(BookNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }
}
