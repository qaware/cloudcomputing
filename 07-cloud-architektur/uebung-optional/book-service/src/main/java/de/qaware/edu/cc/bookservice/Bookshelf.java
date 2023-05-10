package de.qaware.edu.cc.bookservice;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Simple bookshelf component to hold and manage the Book entities.
 */
@Component
public class Bookshelf {

    private final Set<Book> books = new HashSet<>();

    /**
     * Initialize some test data.
     */
    @PostConstruct
    public void initialize() {
        books.add(new Book("The Hitchhiker's Guide to the Galaxy", "Douglas Adams", "0345391802"));
        books.add(new Book("The Martian", "Andy Weir", "0553418025"));
        books.add(new Book("Guards! Guards!", "Terry Pratchett", "0062225758"));
        books.add(new Book("Alice in Wonderland", "Lewis Carroll", "3458317422"));
        books.add(new Book("Life, the Universe and Everything", "Douglas Adams", "0345391829"));
    }

    /**
     * Find books, all or by title.
     *
     * @param title a title to look for, may be NULL
     * @return a collection of books
     */
    public Collection<Book> findByTitle(String title) {
        if (Objects.isNull(title)) {
            return books;
        } else {
            return books
                    .stream()
                    .filter((Book b) -> b.getTitle().equalsIgnoreCase(title))
                    .collect(Collectors.toList());
        }
    }

    /**
     * Find book by ISBN.
     *
     * @param isbn
     * @return
     */
    public Book findByIsbn(String isbn) {
        return books
                .stream()
                .filter((Book b) -> b.getIsbn().equals(isbn))
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException(isbn));
    }


    /**
     * Delete book by ISBN.
     *
     * @param isbn
     */
    public void delete(String isbn) {
        books.removeIf(b -> b.getIsbn().equals(isbn));
    }

    /**
     * Create book of not already present.
     *
     * @param book the book to create
     * @return true of created, otherwise false
     */
    public boolean create(Book book) {
        return books.add(book);
    }

    /**
     * Find and update the book with given ISBN.
     *
     * @param isbn the ISBN to update
     * @param book the updated book
     */
    public void update(String isbn, Book book) {
        Book found = findByIsbn(isbn);

        found.setTitle(book.getTitle());
        found.setAuthor(book.getAuthor());
    }
}
