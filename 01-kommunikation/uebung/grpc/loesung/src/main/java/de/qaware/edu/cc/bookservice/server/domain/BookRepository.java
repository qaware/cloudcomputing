package de.qaware.edu.cc.bookservice.server.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Stores books.
 */
public class BookRepository {
    private final Map<String, Book> books = new ConcurrentHashMap<>();

    /**
     * Lists all books.
     *
     * @return books
     */
    public Collection<Book> listAll() {
        return Collections.unmodifiableCollection(books.values());
    }

    /**
     * Adds a new book.
     *
     * @param book book to add
     * @throws BookAlreadyExistsException if a book with that isbn already exists
     */
    public void add(Book book) throws BookAlreadyExistsException {
        if (books.containsKey(book.getIsbn())) {
            throw new BookAlreadyExistsException(book.getIsbn());
        }

        books.put(book.getIsbn(), book);
    }

    /**
     * Deletes the book with the given isbn.
     *
     * @param isbn isbn
     * @throws BookNotFoundException if a book with the isbn doesn't exist
     */
    public void delete(String isbn) throws BookNotFoundException {
        if (books.remove(isbn) == null) {
            throw new BookNotFoundException(isbn);
        }
    }

    /**
     * Updates the book with the given isbn to teh given values
     *
     * @param isbn    isbn
     * @param newBook new values
     * @throws BookAlreadyExistsException if a book with that isbn already exists
     * @throws BookNotFoundException      if a book with the isbn doesn't exist
     */
    public void update(String isbn, Book newBook) throws BookNotFoundException, BookAlreadyExistsException {
        delete(isbn);
        add(newBook);
    }
}
