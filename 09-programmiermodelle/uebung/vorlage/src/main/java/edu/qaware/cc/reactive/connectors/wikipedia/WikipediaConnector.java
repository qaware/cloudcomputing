package edu.qaware.cc.reactive.connectors.wikipedia;

import com.jayway.jsonpath.JsonPath;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
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
    public List<String> getArticleTitlesFor(String term){
        try {          
            //Request absetzen
            Client client = ClientBuilder.newClient();
            String articles = client.target("https://en.wikipedia.org")
                    .path("w/api.php")
                    .queryParam("action", "opensearch")
                    .queryParam("limit", "25")
                    .queryParam("format", "json")
                    .queryParam("search", term)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .get(String.class);
            
            //Das JSON-Dokument auslesen
            return JsonPath.read(articles, "$.*.*");
        } catch (Exception ex) { 
            throw new RuntimeException(ex);
        }
    }
}
