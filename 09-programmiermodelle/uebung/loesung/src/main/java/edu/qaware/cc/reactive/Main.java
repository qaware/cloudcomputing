package edu.qaware.cc.reactive;

import edu.qaware.cc.reactive.connectors.openlibrary.OpenLibraryConnector;
import edu.qaware.cc.reactive.connectors.wikipedia.WikipediaConnector;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Sammelt aus Wikipedia und bestimmten Feedzilla-Kategorien Meldungen
 * sequenziell zusammen und gibt diese dann auf der Konsole aus.
 *
 * @author Josef Adersberger
 */
public class Main {
    /**
     * Die Einstiegsmethode
     *
     * @param args es werden keine Kommandozeilen-Argumente ausgewertet
     */
    public static void main(String[] args) {
        String term = "Reactive";
        long start = System.nanoTime();

        WikipediaConnector connector = new WikipediaConnector();
        List<String> results = new ArrayList<>(connector.getArticleTitlesFor(term));

        OpenLibraryConnector openLibrary = new OpenLibraryConnector();
        results.addAll(openLibrary.getBooksWithTitleContaining(term));

        System.out.println(results.size() + " items found");
        for (String s : results) {
            System.out.println(s);
        }

        System.out.println("Duration to collect results: " + Duration.ofNanos(System.nanoTime() - start).toMillis() + " ms");
    }
}
