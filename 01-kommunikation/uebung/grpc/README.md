# Übung: gRPC

Ziel dieser Übung ist es, einen einfachen gRPC-Service für eine Bibliothek zu erstellen. Das API soll einfache Abfragen,
sowie ein CRUD Interface zum Anlegen, Aktualisieren und Löschen von Büchern ermöglichen.

Es sollen folgende Operationen möglich sein (hier beispielhaft in Java-Syntax angegeben):

* `Collection<Book> listAll()`
* `void add(Book book)`
* `void delete(String isbn)`
* `void update(String isbn, Book newBook)` 

Ein Buch besteht aus `String isbn`, `String title` und `String author`. `isbn` ist die ID des Buchs.

Sollten Sie an einer Stelle steckenbleiben, werfen Sie einen Blick in die [Lösung](loesung) oder fragen Sie Ihren Betreuer!

## Vorbereitung

Machen Sie sich mit gRPC vertraut. Lesen Sie dazu [das gRPC Tutorial](https://grpc.io/docs/languages/java/basics/).
Dann sollten Sie ein neues Projekt anlegen, das sowohl den Server als auch den Client beinhaltet. Ob Sie Maven oder
Gradle verwenden, bleibt Ihnen überlassen. Die [Lösung](loesung) ist mit Maven implementiert.

gRPC-Services werden aus `.proto` Dateien generiert. Es gibt für Gradle und Maven Plugins, die dies erledigen. Auf der
[gRPC GitHub Seite](https://github.com/grpc/grpc-java) werden diese gezeigt.

## gRPC Service definieren

Definieren Sie nun Ihren gRPC `BookService` in einer `.proto`-Datei. Dabei müssen Sie die API des Services designen (siehe oben).
Diese API beinhaltet auch Eingabe- und Rückgabetypen, die im [Protocol Buffers Format](https://developers.google.com/protocol-buffers/docs/proto3)
definiert werden. 

## Server erstellen

Implementieren Sie nun den gRPC Server. In der `main`-Methode des Servers sollten Sie diesen Server auf einem Port Ihrer
Wahl starten.

## Client erstellen

Damit Sie den Server testen können, bietet sich ein gRPC Client an. Erstellen Sie eine neue `main`-Methode für den Client
und verwenden Sie die von gRPC erzeugten Client-Klassen, um mit ihrem Server zu kommunizieren.

# Quellen

* https://github.com/grpc/grpc-java
* https://grpc.io/docs/languages/java/basics/
* https://www.baeldung.com/grpc-introduction
* https://developers.google.com/protocol-buffers/docs/proto3
