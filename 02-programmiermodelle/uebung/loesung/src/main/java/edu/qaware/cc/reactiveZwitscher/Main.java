package edu.qaware.cc.reactiveZwitscher;

import edu.qaware.cc.reactiveZwitscher.connectors.nytimes.NYTimesConnector;
import edu.qaware.cc.reactiveZwitscher.connectors.wikipedia.WikipediaConnector;
import java.util.ArrayList;
import java.util.List;

/**
 * Sammelt aus Wikipedia und bestimmten Feedzilla-Kategorien Meldungen
 * sequemziell zusammen und gibt diese dann auf der Konsole aus.
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

        String term = "Java";
        long start = System.currentTimeMillis();
        List<String> zwitschers = new ArrayList<String>();

        WikipediaConnector connector = new WikipediaConnector();
        zwitschers.addAll(connector.getArticleTitlesFor(term));

        NYTimesConnector nyTimes = new NYTimesConnector();
        zwitschers.addAll(nyTimes.getArticleTitlesFor(term));

        System.out.println(zwitschers.size() + " Items found");
        for (String s : zwitschers) {
            System.out.println(s);
        }

        System.out.println("Duration to collect Zwitschers: " + (System.currentTimeMillis() - start) + " ms");

    }
}
