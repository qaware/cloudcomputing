package edu.qaware.cc.reactiveZwitscher.connectors.openlibrary;//(c) by QAware GmbH, Josef Adersberger (josef.adersberger@qaware.de)

import com.jayway.jsonpath.JsonPath;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Holt Bücher mit einem bestimmten Titel vom OpenLibrary Dienst des Internet Archive.
 *
 * REST API: http://openlibrary.org/search.json?title=TERM
 */
public class OpenLibraryConnector {

    /**
     *
     *
     * @param term
     * @return
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
