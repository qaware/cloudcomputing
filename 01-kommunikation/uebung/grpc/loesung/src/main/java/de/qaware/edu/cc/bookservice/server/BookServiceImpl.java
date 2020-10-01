package de.qaware.edu.cc.bookservice.server;

import de.qaware.edu.cc.bookservice.server.domain.Book;
import de.qaware.edu.cc.bookservice.server.domain.BookAlreadyExistsException;
import de.qaware.edu.cc.bookservice.server.domain.BookNotFoundException;
import de.qaware.edu.cc.bookservice.server.domain.BookRepository;
import de.qaware.edu.cc.generated.BookProto;
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
    public void listBooks(BookProto.ListBooksRequest request, StreamObserver<BookProto.Book> responseObserver) {
        for (Book book : bookRepository.listAll()) {
            responseObserver.onNext(book.toProtobuf());
        }

        responseObserver.onCompleted();
    }

    @Override
    public void addBook(BookProto.Book request, StreamObserver<BookProto.Book> responseObserver) {
        Book book = Book.fromProtobuf(request);
        try {
            bookRepository.add(book);
            responseObserver.onNext(request);
            responseObserver.onCompleted();
        } catch (BookAlreadyExistsException e) {
            responseObserver.onError(Status.ALREADY_EXISTS.asException());
        }
    }

    @Override
    public void deleteBook(BookProto.Isbn request, StreamObserver<BookProto.Void> responseObserver) {
        try {
            bookRepository.delete(request.getValue());
            responseObserver.onNext(BookProto.Void.getDefaultInstance());
            responseObserver.onCompleted();
        } catch (BookNotFoundException e) {
            responseObserver.onError(Status.NOT_FOUND.asException());
        }
    }

    @Override
    public void updateBook(BookProto.UpdateBookRequest request, StreamObserver<BookProto.Book> responseObserver) {
        Book newBook = Book.fromProtobuf(request.getNewBook());

        try {
            bookRepository.update(request.getIsbn(), newBook);
            responseObserver.onNext(request.getNewBook());
            responseObserver.onCompleted();
        } catch (BookNotFoundException e) {
            responseObserver.onError(Status.NOT_FOUND.asException());
        } catch (BookAlreadyExistsException e) {
            responseObserver.onError(Status.ALREADY_EXISTS.asException());
        }
    }
}
