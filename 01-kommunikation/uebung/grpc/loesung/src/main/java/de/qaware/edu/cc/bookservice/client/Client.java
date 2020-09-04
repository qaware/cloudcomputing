package de.qaware.edu.cc.bookservice.client;

import de.qaware.edu.cc.generated.BookProto;
import de.qaware.edu.cc.generated.BookServiceGrpc;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;

import java.util.Iterator;
import java.util.Scanner;

public class Client {
    private static final String SERVER = "localhost:12345";
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        Channel channel = ManagedChannelBuilder.forTarget(SERVER).usePlaintext().build();

        BookServiceGrpc.BookServiceBlockingStub bookService = BookServiceGrpc.newBlockingStub(channel);
        System.out.println("Welcome to the book client. What do you want to do?");

        while (true) {
            System.out.println("list");
            System.out.println("add");
            System.out.println("delete");

            System.out.print("> ");
            String line = SCANNER.nextLine();
            if (line == null || line.isEmpty()) {
                return;
            }

            switch (line) {
                case "list":
                    listBooks(bookService);
                    break;
                case "add":
                    addNewBook(bookService);
                    break;
                case "delete":
                    deleteBook(bookService);
                    break;
                default:
                    System.out.println("Come again? (Press enter to quit)");
                    break;
            }
        }
    }

    private static void deleteBook(BookServiceGrpc.BookServiceBlockingStub bookService) {
        System.out.println("Going to delete a book ...");
        System.out.print("Enter ISBN: ");
        String isbn = SCANNER.nextLine();

        bookService.deleteBook(BookProto.Isbn.newBuilder().setValue(isbn).build());
        System.out.println("Deleted book!");
        System.out.println();
    }

    private static void addNewBook(BookServiceGrpc.BookServiceBlockingStub bookService) {
        System.out.print("Enter ISBN: ");
        String isbn = SCANNER.nextLine();

        System.out.print("Enter title: ");
        String title = SCANNER.nextLine();

        System.out.print("Enter author: ");
        String author = SCANNER.nextLine();

        BookProto.Book addedBook = bookService.addBook(
            BookProto.Book.newBuilder().setIsbn(isbn).setTitle(title).setAuthor(author).build()
        );

        System.out.printf("Added book: %s - %s from %s%n", addedBook.getIsbn(), addedBook.getTitle(), addedBook.getAuthor());
        System.out.println();
    }

    private static void listBooks(BookServiceGrpc.BookServiceBlockingStub bookService) {
        Iterator<BookProto.Book> books = bookService.listBooks(BookProto.ListBooksRequest.getDefaultInstance());
        System.out.println("Available books:");
        while (books.hasNext()) {
            BookProto.Book book = books.next();

            System.out.printf("  %s - %s from %s%n", book.getIsbn(), book.getTitle(), book.getAuthor());
        }
        System.out.println();
    }
}
