package edu.qaware.cc.reactiveZwitscher.connectors.nytimes;

import com.jayway.jsonpath.JsonPath;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import net.minidev.json.JSONArray;

/**
 *
 * @author f.lautenschlager
 */
public class NYTimesConnector {

    /**
     * Holt die NY-Times-Artikel zu einem Suchbegriff
     * (http://developer.nytimes.com/docs/events_api) Dabei wird ein Request
     * nach folgendem Beispiel aufgebaut:
     * <code>http://api.nytimes.com/svc/search/v2/articlesearch.json?q=nsa&api-key=sample-key</code>
     *
     * @param term Suchbegriff
     * @return Liste der Artikeltitel entsprechend dem Suchbegriff. Die Liste
     * ist leer, wenn keine Artikel gefunden wurden.
     */
    public List<String> getArticleTitlesFor(String term) {
        try {
            //Request absetzen
            Client client = ClientBuilder.newClient();
            String articles = client.target("http://api.nytimes.com/")
                    .path("svc/search/v2/articlesearch.json")
                    .queryParam("api-key", "sample-key")
                    .queryParam("q", term)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .get(String.class);

         

            //Das JSON-Dokument auslesen
            List jsonResult = JsonPath.read(articles, "$.*.*");

            //Unsere Ergebnisse
            List<String> result = new ArrayList<String>();
            
            //Wir wollen nur die Rohwerte und keine Metadaten
            for (Object value : (JSONArray) jsonResult.get(1)) {
                Map<Object, Object> document = (Map) value;

                result.add("-------------------");
                for (Map.Entry<Object, Object> field : document.entrySet()) {
                    result.add(field.getKey() + "::" + field.getValue());
                }

            }

            return result;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
