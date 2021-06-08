package edu.qaware.cc.reactive.message;

/**
 * Message which is sent from MainWithActors to MessageCollectorActor
 * and from MessageCollectorActor to OpenLibraryActor and WikipediaActor.
 */
public class SearchMessage {
    private final String term;

    public SearchMessage(String term) {
        this.term = term;
    }

    public String getTerm() {
        return term;
    }
}
