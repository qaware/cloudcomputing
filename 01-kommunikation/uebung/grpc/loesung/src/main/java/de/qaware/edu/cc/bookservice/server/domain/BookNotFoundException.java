package de.qaware.edu.cc.bookservice.server.domain;

/**
 * Is thrown if a book with that isbn doesn't exist.
 */
public class BookNotFoundException extends Exception {
    private final String isbn;

    public BookNotFoundException(String isbn) {
        super(String.format("Book with ISBN '%s' not found", isbn));

        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }
}
