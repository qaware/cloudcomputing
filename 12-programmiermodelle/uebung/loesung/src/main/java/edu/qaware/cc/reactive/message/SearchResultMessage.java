package edu.qaware.cc.reactive.message;

import java.util.List;

/**
 * Message which is sent from OpenLibraryActor and WikipediaActor to MessageCollectorActor and
 * from MessageCollectorActor to MainWithActors.
 */
public class SearchResultMessage {
    private final List<String> results;

    public SearchResultMessage(List<String> results) {
        this.results = List.copyOf(results);
    }

    public List<String> getResults() {
        return results;
    }
}
