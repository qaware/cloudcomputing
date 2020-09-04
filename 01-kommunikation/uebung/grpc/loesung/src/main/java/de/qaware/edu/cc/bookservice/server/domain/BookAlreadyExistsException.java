package de.qaware.edu.cc.bookservice.server.domain;

/**
 * Is thrown if a book with that isbn already exists.
 */
public class BookAlreadyExistsException extends Exception {
    private final String isbn;

    public BookAlreadyExistsException(String isbn) {
        super(String.format("Book with ISBN '%s' already exists", isbn));

        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }
}
