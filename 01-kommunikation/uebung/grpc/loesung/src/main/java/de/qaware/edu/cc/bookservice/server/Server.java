package de.qaware.edu.cc.bookservice.server;

import de.qaware.edu.cc.bookservice.server.domain.Book;
import de.qaware.edu.cc.bookservice.server.domain.BookAlreadyExistsException;
import de.qaware.edu.cc.bookservice.server.domain.BookRepository;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class Server {
    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException, InterruptedException {
        BookRepository repository = new BookRepository();
        BookServiceImpl service = new BookServiceImpl(repository);

        addSampleBooks(repository);

        io.grpc.Server server = ServerBuilder.forPort(PORT).addService(service).build();
        server.start();

        System.out.println("Server running on port " + PORT);
        server.awaitTermination();
    }

    private static void addSampleBooks(BookRepository repository) {
        try {
            repository.add(new Book("0345391802", "The Hitchhiker's Guide to the Galaxy", "Douglas Adams"));
            repository.add(new Book("0553418025", "The Martian", "Andy Weir"));
            repository.add(new Book("0062225758", "Guards! Guards!", "Terry Pratchett"));
            repository.add(new Book("3458317422", "Alice in Wonderland", "Lewis Carroll"));
            repository.add(new Book("0345391829", "Life, the Universe and Everything", "Douglas Adams"));
        } catch (BookAlreadyExistsException e) {
            throw new AssertionError("Shouldn't happen", e);
        }
    }
}
