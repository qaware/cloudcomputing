package edu.qaware.cc.reactive.connectors.wikipedia;

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
 * Holt Artikeltitel auf Wikipedia zu einem entsprechenden
 * Suchbegriff. Es wird die Wikipedia Web-API in der OpenSearch
 * Variante genutzt.
 *
 * @author Josef Adersberger
 */
public class WikipediaConnector {

    /**
     * Holt die Wikipedia-Artikeltitel zu einem Suchbegriff
     * Dabei wird ein Request nach folgendem Beispiel aufgebaut:
     * <code>http://en.wikipedia.org/w/api.php?action=opensearch&search={term}&limit=10&format=xml</code>
     *
     * @param term Suchbegriff
     * @return Liste der Artikeltitel entsprechend dem Suchbegriff.
     * Die Liste ist leer, wenn keine Artikel gefunden wurden.
     * Es werden maximal 25 Artikeltitel zurückgegeben.
     */
    public List<String> getArticleTitlesFor(String term) {
        try {
            URI uri = URI.create(String.format("https://en.wikipedia.org/w/api.php?action=opensearch&limit=25&format=json&search=%s", URLEncoder.encode(term, StandardCharsets.UTF_8)));
            HttpRequest request = HttpRequest.newBuilder(uri).GET().header("Accept", "application/json").build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            JsonNode jsonNode = new ObjectMapper().readTree(response.body());
            JsonNode titles = jsonNode.get(1);

            List<String> result = new ArrayList<>();
            for (JsonNode title : titles) {
                result.add(title.asText());
            }
            return result;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
