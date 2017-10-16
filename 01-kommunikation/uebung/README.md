# Übung: Das REST Protokoll

Ziel dieser Übung ist es ein einfaches REST API für eine Bibliothek zu erstellen. Das API soll einfache Abfragen,
sowie ein CRUD Interface zum Anlegen, Aktualisieren und Löschen von Büchern ermöglichen.

## Vorbereitung

1. Zunächst muss ein Anwendungsrumpf für den Microservice und das REST API erstellt werden. Wir verwenden hierfür
den Spring Boot Initializr. Rufen sie hierfür die folgende URL auf: https://start.spring.io

2. Passen sie die Projekt Metadaten nach ihren Bedürfnissen an. Wählen sie Java als Sprache und Maven als Build-Tool.

3. Fügen sie die folgenden Dependencies hinzu:
  * Jersey (JAX-RS)
  * Actuator


4. Generieren und laden sie das Projekt und speichern sie es in ihrem Arbeitsbereich.

5. Öffnen sie eine Console, gehen sie in das Projektverzeichnis und führen sie folgendes Kommand aus: `mvnv install`


## Aufgaben

### Aufgabe 1: REST-API mit JAX-RS erstellen

Bei dieser Aufgabe geht es darum, eine einfache REST-Schnittstelle aufzubauen. Wir verwenden hierfür JAX-RS. Ein Getting Started finden sie hier: https://jersey.github.io/documentation/latest/getting-started.html

(1) Entwerfen sie zunächst eine einfache Datenklasse um Bücher zu repräsentieren. Die Klasse soll mindestens die Felder `titel`, `isbn` und `author` enthalten. Zusätzlich soll die Klasse beim Deserialisieren unbekannte
JSON Felder ignorieren.

```java
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {
    private String title;
    private String author;
    private String isbn;

    // getter and setter
}
```

(2) Fügen sie nun eine REST Resource Klasse hinzu. Diese dient als Haupteinstiegspunkt für das Book API. Das API soll unter dem Pfad `/api/books` erreichbar sein und als Media-Type `application/json` produzieren.

```java
@Component
@Path("/api/books")
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {
  // implement methods
}
```

(3) Fügen sie der REST Resource nun entsprechende Methoden zum Abruf von Büchern hinzu. Es soll die Möglichkeit geben alle Bücher per `GET /api/books` abzurufen sowie einzelne Bücher mittels ISBN per `GET /api/books/{isbn}`. Implementieren sie die Business-Logik rudimentär (statische Liste statt DB). Achten sie bei
der Implementierung auf die Verwendung der korrekten HTP Verben und Status-Codes, z.B. für den Fall das ein Buch per ISBN nicht gefunden wurde.

```java
    @GET
    public Response books(@QueryParam("title") String title) {
        Collection<Book> books = bookshelf.findByTitle(title);
        return Response.ok(books).build();
    }

    @GET
    @Path("/{isbn}")
    public Response byIsbn(@PathParam("isbn") String isbn) {
        Book book = bookshelf.findByIsbn(isbn);
        return Response.ok(book).build();
    }
```

(4) Implementieren sie eine JAX-RS `ResourceConfig` und registrieren sie die REST Resource Klasse sowie den Jackson JSON Marshaller.

```java
@Component
public class BookstoreAPI extends ResourceConfig {
    public BookstoreAPI() {
        super();

        register(JacksonFeature.class);
        register(BookResource.class);
        register(BookExceptionMapper.class);
    }
}
```

(5) Kompilieren sie den Microservice und führen sie die Applikation aus: `mvnv install spring-boot:run`. Die Anwendung und das REST API sollte nun unter der folgenden URL erreichbar sein: `http://localhost:8080/api/books`.

### Aufgabe 2: API Dokumentation

Eine gute REST API braucht Dokumentation bzw. eine Beschreibung der angeboten
Funktionalität die von Maschinen verarbeitet werden kann.

#### Aufgabe 2.1: WADL Definition hinzufügen

(1) In einem ersten Schritt fügen sie die `WadlResource` Klasse aus dem
Jersey Modul in der REST API `ResourceConfig` hinzu.

```java
  register(WadlResource.class);
```

(2) Starten sie den Microservice und prüfen sie die korrekte Funktionsweise.
Die WADL Definition sollten unter `http://localhost:8080/api/application.wadl`
aufrufbar sein (abhängig von der Jersey Servlet URL).

#### Aufgabe 2.2: Swagger Definition hinzufügen

(1) Fügen sie zunächst in der `pom.xml` die folgenden Dependencies hinzu:
```xml
    <dependency>
        <groupId>io.springfox</groupId>
        <artifactId>springfox-swagger2</artifactId>
        <version>2.7.0</version>
    </dependency>
    <dependency>
        <groupId>io.springfox</groupId>
        <artifactId>springfox-swagger-ui</artifactId>
        <version>2.7.0</version>
    </dependency>
    <dependency>
        <groupId>io.swagger</groupId>
        <artifactId>swagger-jersey2-jaxrs</artifactId>
        <version>1.5.13</version>
    </dependency>
```

(2) Im nächsten Schritt müssen die Swagger REST Resource Klasse mit der JAX-RS Applikation registriert werden. Siehe https://github.com/swagger-api/swagger-core/wiki/Swagger-Core-Jersey-2.X-Project-Setup-1.5#using-a-custom-application-subclass für weitere Details.

```java
    resources.add(io.swagger.jaxrs.listing.ApiListingResource.class);
    resources.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);
```

(3) Zusätzlich muss Swagger und die Basis-Parameter für das API noch entsprechend konfiguriert werden. Siehe https://github.com/swagger-api/swagger-core/wiki/Swagger-Core-Jersey-2.X-Project-Setup-1.5#using-a-custom-application-subclass für weitere Details.

```java
    BeanConfig beanConfig = new BeanConfig();
    beanConfig.setVersion("1.0.1");
    beanConfig.setSchemes(new String[]{"http"});
    beanConfig.setHost("localhost:8080");
    beanConfig.setPrettyPrint(true);
    beanConfig.setBasePath("/api/");
    beanConfig.setResourcePackage("de.qaware.edu.cc.bookservice");
    beanConfig.setScan(true);
```

(4) Annotieren und dokumentieren sie nun die vorhandenen Klassen der REST-API über Swagger-Annotationen zusätzlich zu den bereits vorhandenen JAX-RS-Annotationen. Nutzen Sie hierfür die folgenden Swagger-Annotationen, eine Beschreibung der Annotationen ist hier zugänglich: https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X.

| Swagger-Annotation        | Code-Element           |
| ------------- | ------------- |
| `@Api`      |Ressourcen-Klasse (REST-Schnittstelle) |
| `@ApiOperation`      | Methode der Ressourcen-Klasse      |
| `@ApiResponses` und `@ApiResponse` | Methode der Ressourcen-Klasse (überlegen Sie sich hier mögliche Fehlersituationen und bilden Sie diese auf http-Status-Codes ab)      |
| `@ApiModel` | Entitäts-Klasse      |
| `@ApiModelProperty` | Setter-Methoden der Entitätsklasse      |

(5) Starten Sie die Anwendung nun neu. Die API-Beschreibung durch Swagger sollte nun unter der URL http://localhost:8080/api/swagger.json zugänglich sein.

(6) Laden sie nun die Swagger-UI von Github. Folgen sie den Anweisungen unter https://swagger.io/docs/swagger-tools/#swagger-ui-documentation-29
Öffnen sie die UI und rufen sie die Swagger JSON URL auf. **Hinweis: sie benötigen einen JAX-RS CORS Filter um die Datei lokal aufrufen zu können.**

### Kür: REST-API weiter ausbauen
Bauen Sie die REST-Schnittstelle weiter aus und fügen sie Logik zum Anlegen, Aktualisieren und Löschen von Büchern hinzu:

* `DELETE /api/books/{isbn}` löscht ein Buch und gibt bei Erfolg HTTP 202 zurück
* `POST /api/books` legt ein Buch an, akzeptiert `application/json` und gibt HTTP 201 mit der neuen URL zurück.
* `PUT /api/books/{isbn}` aktualisiert das Buch, akzeptiert `application/json` und gibt HTTP 200 zurück.

Überlegen Sie sich weitere Anwendungsfälle, die die Schnittstelle abbilden soll als einfache Liste (auf Papier). Leiten Sie aus den Anwendungsfällen ein Datenmodell ab (auf Papier). Erstellen Sie aus den Anwendungsfällen, dem Datenmodell und den vorgestellten Entwurfsregeln eine REST-Schnittstelle.

## Quellen
Diese Übung soll auch eine eigenständige Problemlösung auf Basis von Informationen aus dem Internet vermitteln. Sie können dazu für die eingesetzten Technologien z.B. die folgenden Quellen nutzen:

Maven
* http://maven.apache.org/guides/getting-started

Spring Boot
* https://start.spring.io
* https://docs.spring.io/spring-boot/docs/current/reference/html/index.html
* https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-web-applications.html

JAX-RS
* https://jersey.github.io/documentation/latest/index.html
* https://jersey.github.io/documentation/latest/getting-started.html
* https://dzone.com/articles/using-jax-rs-with-spring-boot-instead-of-mvc

Swagger
* http://swagger.io
* https://github.com/swagger-api/swagger-core
* http://springfox.github.io/springfox/docs/current/#introduction
* http://springfox.github.io/springfox/
