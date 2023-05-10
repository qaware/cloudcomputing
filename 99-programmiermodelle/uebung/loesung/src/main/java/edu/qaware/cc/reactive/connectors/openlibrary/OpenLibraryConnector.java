package edu.qaware.cc.reactive.connectors.openlibrary;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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
    public List<String> getBooksWithTitleContaining(String term) {
        try {
            URI uri = URI.create(String.format("https://openlibrary.org/search.json?title=%s", URLEncoder.encode(term, StandardCharsets.UTF_8)));
            HttpRequest request = HttpRequest.newBuilder(uri).GET().header("Accept", "application/json").build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            JsonNode jsonNode = new ObjectMapper().readTree(response.body());

            List<String> result = new ArrayList<>();
            for (JsonNode doc : jsonNode.get("docs")) {
                result.add(doc.get("title").textValue());
            }

            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
