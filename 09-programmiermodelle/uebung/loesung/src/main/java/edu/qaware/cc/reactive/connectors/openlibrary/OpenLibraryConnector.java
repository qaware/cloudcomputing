package edu.qaware.cc.reactive.connectors.openlibrary;

import com.jayway.jsonpath.JsonPath;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Holt Bücher mit einem bestimmten Titel vom OpenLibrary Dienst des Internet Archive.
 *
 * @author Josef Adersberger
 */
public class OpenLibraryConnector {

    /**
     * Holt Buchtitel zu dem vorgegebenen Suchbegriff.
     * Der Request wird dabei entsprechend der folgenden API aufgebaut:
     * http://openlibrary.org/search.json?title=TERM
     *
     * @param term Suchbegriff im Titel
     * @return Liste an Buchtiteln. Die Liste ist leer, wenn keine Artikel gefunden wurden.
     */
    public List<String> getBooksWithTitleContaining(String term){
        //Request absetzen ()
        Client client = ClientBuilder.newClient();
        String articles = client.target("http://openlibrary.org")
                .path("search.json")
                .queryParam("title", term)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(String.class);

        //Das JSON-Dokument auslesen
        List<String> result = JsonPath.read(articles, "$.docs..title");
        return result;
    }
}
