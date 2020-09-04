package de.qaware.edu.cc.bookservice.server;

import de.qaware.edu.cc.bookservice.server.domain.Book;
import de.qaware.edu.cc.bookservice.server.domain.BookAlreadyExistsException;
import de.qaware.edu.cc.bookservice.server.domain.BookNotFoundException;
import de.qaware.edu.cc.bookservice.server.domain.BookRepository;
import de.qaware.edu.cc.generated.BookOuterClass;
import de.qaware.edu.cc.generated.BookServiceGrpc;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

/**
 * gRPC server implementation.
 */
class BookServiceImpl extends BookServiceGrpc.BookServiceImplBase {
    private final BookRepository bookRepository;

    BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void listBooks(BookOuterClass.ListBooksRequest request, StreamObserver<BookOuterClass.Book> responseObserver) {
        for (Book book : bookRepository.listAll()) {
            responseObserver.onNext(book.toProtobuf());
        }

        responseObserver.onCompleted();
    }

    @Override
    public void addBook(BookOuterClass.Book request, StreamObserver<BookOuterClass.Book> responseObserver) {
        Book book = Book.fromProtobuf(request);
        try {
            bookRepository.add(book);
            responseObserver.onNext(request);
        } catch (BookAlreadyExistsException e) {
            responseObserver.onError(Status.ALREADY_EXISTS.asException());
        }

        responseObserver.onCompleted();
    }

    @Override
    public void deleteBook(BookOuterClass.Isbn request, StreamObserver<BookOuterClass.Void> responseObserver) {
        try {
            bookRepository.delete(request.getValue());
            responseObserver.onNext(BookOuterClass.Void.getDefaultInstance());
        } catch (BookNotFoundException e) {
            responseObserver.onError(Status.NOT_FOUND.asException());
        }

        responseObserver.onCompleted();
    }

    @Override
    public void updateBook(BookOuterClass.UpdateBookRequest request, StreamObserver<BookOuterClass.Book> responseObserver) {
        Book newBook = Book.fromProtobuf(request.getNewBook());

        try {
            bookRepository.update(request.getIsbn(), newBook);
            responseObserver.onNext(request.getNewBook());
        } catch (BookNotFoundException e) {
            responseObserver.onError(Status.NOT_FOUND.asException());
        } catch (BookAlreadyExistsException e) {
            responseObserver.onError(Status.ALREADY_EXISTS.asException());
        }

        responseObserver.onCompleted();
    }
}
