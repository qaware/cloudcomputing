package edu.qaware.cc.reactiveZwitscher.connectors.feedzilla;

import com.jayway.jsonpath.JsonPath;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

/**
 * Holt die Titel von aktuellen News-Einträgen auf Feedzilla
 * zu einem bestimmten Suchbegriff.
 * 
 * @author Josef Adersberger
 */
public class FeedzillaConnector {
    
    /**
     * Holt die News-Artikel in einer Kategorie auf Feedzilla 
     * zu einem entsprechenden Suchbegriff.
     * 
     * Es wird die REST-API von Newszilla genutzt:
     * <code>https://code.google.com/p/feedzilla-api/wiki/RestApi</code>
     * 
     * Die Request-Struktur dafür sieht wie folgt aus:
     * <code>http://api.feedzilla.com/v1/categories/{category}/articles/search.json?q={term}</code>
     * 
     * @param term Suchbegriff
     * @param category News-Kategorie in der gesucht werden soll
     * @return Liste der News-Titel entsprechend dem Suchbegriff. 
     * Die Liste ist leer, wenn keine Artikel gefunden wurden.
     */
    public List<String> getNewsFor(String term, NewsCategory category){
        try {       
            //Request absetzen
            Client client = ClientBuilder.newClient();
            String articles = client.target("http://api.feedzilla.com")
                    .path("v1/categories/{category}/articles/search.json")
                    .resolveTemplate("category", category.getCategoryId())
                    .queryParam("q", term)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .get(String.class);
            
            //Das JSON-Dokument auslesen
            List<String> result = JsonPath.read(articles, "$.articles.*.title");          
            return result;
        } catch (Exception ex) { 
            throw new RuntimeException(ex);
        }        
    }
    
}

