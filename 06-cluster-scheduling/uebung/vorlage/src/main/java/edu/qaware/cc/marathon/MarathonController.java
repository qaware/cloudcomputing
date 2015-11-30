package edu.qaware.cc.marathon;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;

/**
 * Fernsteuerung für ein Marathon-Cluster
 */
public class MarathonController {


    public static final String MARATHON_ENDPOINT = "http://cc-vorles-elasticl-yo07v3s5a0vp-259392268.eu-central-1.elb.amazonaws.com/service/marathon/";
    private static final Logger LOG = LoggerFactory.getLogger(MarathonController.class);

    static {
        Unirest.setDefaultHeader("Content-Type","application/json");
        Unirest.setDefaultHeader("Accept","application/json");
    }


    public static void main(String[] args) throws UnirestException, IOException {
        submitApp("nginx-cluster.json");
        printRunningApps();
    }

    /**
     * Gibt alle in Marathon laufenden Apps aus
     *
     * @throws UnirestException wenn REST Anfrage an Marathon fehlerhaft war.
     */
    public static void printRunningApps() throws UnirestException {
        //TODO: Implementieren mit Hilfe der Marathon-REST-API und Unirest
        //Dokumentation Marathon-REST-API: https://mesosphere.github.io/marathon/docs/rest-api.html
        //Dokumentation Unirest: http://unirest.io/java.html
    }

    /**
     * Startet eine App in einem Marathon Cluster
     *
     * @param jsonFile die JSON-Datei mit der Job-Definition. Pfad im Classpath.
     * @return JSON Antwort von Marathon
     * @throws UnirestException wenn REST Anfrage an Marathon fehlerhaft war.
     * @throws IOException wenn JSON-Datei mit Job-Definition nicht gelesen werden kann.
     */
    public static JsonNode submitApp(String jsonFile) throws UnirestException, IOException {
        URL url = Resources.getResource(jsonFile);
        String req = Resources.toString(url, Charsets.UTF_8);

        //TODO: Implementieren mit Hilfe der Marathon-REST-API und Unirest
        //Dokumentation Marathon-REST-API: https://mesosphere.github.io/marathon/docs/rest-api.html
        //Dokumentation Unirest: http://unirest.io/java.html

        return null; //TODO: JSON-Rückgabe von Marathon als Ergebnis rückgeben
    }

}
