package edu.qaware.cc.marathon;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;

/**
 * Fernsteuerung für ein Marathon Cluster
 */
public class MarathonController {

    public static final String MARATHON_ENDPOINT = "http://<MASTER-IP>/service/marathon/v2/apps/";
    public static final String JWT_FILE = "dcos-jwt.txt";
    private static final Logger LOG = LoggerFactory.getLogger(MarathonController.class);

    public static void main(String[] args) throws UnirestException, IOException {
        //Die Default HTTP-Header zur Kommunikation mit dem Marathon-Endpunkt setzen
        setDefaultHeader();
        //Anwendung an Marathon übermitteln
        submitApp("nginx-cluster.json");
        //Alle laufenden Anwendungen anzeigen
        printRunningApps();
    }

    /**
     * Die Default HTTP-Header setzen. Muss vor dem ersten Aufruf der REST-API erfolgen.
     */
    public static void setDefaultHeader() throws IOException {
        Unirest.setDefaultHeader("Content-Type","application/json");
        Unirest.setDefaultHeader("Accept","application/json");
        String jwt = Resources.toString(Resources.getResource(JWT_FILE), Charsets.UTF_8);
        Unirest.setDefaultHeader("Authorization", "token=" + jwt);
    }

    /**
     * Startet eine App in einem Marathon Cluster
     *
     * @param jsonFile die JSON-Datei mit der Job-Definition. Pfad im Classpath.
     * @throws UnirestException wenn REST Anfrage an Marathon fehlerhaft war.
     * @throws IOException wenn JSON-Datei mit Job-Definition nicht gelesen werden kann.
     */
    public static void submitApp(String jsonFile) throws UnirestException, IOException {
        String req = Resources.toString(Resources.getResource(jsonFile), Charsets.UTF_8);
        String response = Unirest.post(MARATHON_ENDPOINT).body(req).asJson().getStatusText();
        LOG.info(response);
    }

    /**
     * Gibt alle in Marathon laufenden Apps aus
     *
     * @throws UnirestException wenn REST Anfrage an Marathon fehlerhaft war.
     */
    public static void printRunningApps() throws UnirestException {
        LOG.info(Unirest.get(MARATHON_ENDPOINT)
                        .asString().getBody().toString());
    }
}
