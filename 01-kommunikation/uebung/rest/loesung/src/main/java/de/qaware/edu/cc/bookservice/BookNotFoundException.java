package de.qaware.edu.cc.bookservice;

/**
 * Custom exception class in case a book was not found.
 */
public class BookNotFoundException extends RuntimeException {
    /**
     * Construct message with given ISBN.
     *
     * @param isbn the ISBN
     */
    public BookNotFoundException(String isbn) {
        super("Book with ISBN " + isbn + " not found.");
    }
}
