package edu.qaware.cc.zwitscher.core;

import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.ConsulException;
import edu.qaware.cc.zwitscher.api.resources.ZwitscherMessageResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.lifecycle.ServerLifecycleListener;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.mikael.urlbuilder.UrlBuilder;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

/**
 * Die Zwitscher-Applikation. Diese exponiert einen REST-Webservice inkl. Swagger Dokumentation.
 *
 * ***********************
 * Q&A
 * ***********************
 * Q: Wie schafft man dynamische Port-Zuordnung und damit einen flexiblen Start neuer Zwitscher-Instanzen?
 * A: Indem man in der Dropwizard-Konfiguration die Ports auf "0" setzt und den Port programmatisch ausliest.
 *
 * Q: Was ist der Unterschied zwischen serviceName und serviceId?
 * A: <code>serviceName</code> = Name des Services
 *    <code>serviceId</code> = spezifische Instanz des Services.
 *    Mehre <code>serviceIds</code> pro <code>serviceName</code> möglich.
 *
 * Q: Warum funktioniert das hier, wenn kein Host angegeben ist?
 * A: Es handelt sich hierbei um den Quell-Host, der von Außen aufgerufen wird.
 *    Die Routing-Tabelle ist sortiert nach Host-Namen. Zunächst die Einträge mit
 *    explizitem Host-Namen, dann die Einträge ohne Host-Namen. Bei einer Anfrage
 *    wird diese Tabelle von Oben nach Unten durchgegangen und bei einem Treffer
 *    ge-routet. (siehe Abschnitt über Routing im fabio-Doku).
 *
 * Q: Wo positioniert man die Consul-Registrierung am besten?
 * A: Man registriert in der <code>run()</code>-Methode der Applikation einen ServerLifecycleListener.
 *    Dort kann man dann die Consul-Registrierung anstoßen, sobald der Server gestartet ist
 *    (und auch der Port vergeben wurde).
 *
 * Q: Hier wird ein Consul HTTP Check verwendet. Wann nutzt man diesen, wann einen TTL Check?
 * A: In Produktion würde eher ein TTL-Check (Deadman's Switch) eingesetzt werden,
 *    was Consul Arbeit erspart und der Service hierbei seinen Hostnamen nicht kennen muss.
 *    Hierfür kann der <code>ScheduledExecutorService</code> von Java genutzt werden.
 * ***********************
 */
public class ZwitscherApplication extends Application<ZwitscherConfiguration> {

    public static final Logger LOG = LoggerFactory.getLogger(ZwitscherApplication.class);
    public static final long HEALTHCHECK_INTERVAL = 2L;

    /**
     * Der Einstiegspunkt in die Zwitscher-Applikation
     *
     * @param args Kommandozeilen-Parameter.
     * @throws Exception eine beliebige Ausnahme, die durch die Anwendung nicht mehr behandelt werden kann.
     */
    public static void main(String[] args) throws Exception {
        ZwitscherApplication app = new ZwitscherApplication();
        app.run(new String[]{"server", "./src/main/resources/zwitscher-config.yml"});
    }

    @Override
    /**
     * Prüft die Konfiguration.
     *
     * Semantik <code>initialize()</code>:
     * <i>An initialize method is used to configure aspects of the application required before
     * the application is run, like bundles, configuration source providers, etc.</i>
     */
    public void initialize(Bootstrap<ZwitscherConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/assets/swagger-ui", "/api-browser", "index.html"));
    }

    @Override
    /**
     * Komponenten registrieren und konfigurieren. In unserem Fall: REST-Ressourcen registrieren.
     *
     * Semantik <code>run()</code>:
     * <i>When the application runs, this is called after the Bundles are run.</i>
     */
    public void run(ZwitscherConfiguration t, Environment e) throws Exception {
        e.jersey().register(ZwitscherMessageResource.class);

        e.lifecycle().addServerLifecycleListener(new ServerLifecycleListener() {
            @Override
            public void serverStarted(Server server) {

                //1) ermittelt den Host und den Port der Applikation
                int applicationPort = 0;
                String applicationHost = server.getURI().getHost();
                LOG.info("Application launched on host {}", applicationHost);
                for (Connector connector : server.getConnectors()) {
                    if (connector instanceof ServerConnector) {
                        ServerConnector serverConnector = (ServerConnector) connector;
                        if (serverConnector.getName().equals("application")) {
                            applicationPort = serverConnector.getLocalPort();
                            LOG.info("Application launched on port {}", applicationPort);
                        }
                    }
                }

                //2) registriert einen Service bei Consul
                try {
                    String serviceName = "zwitscher";
                    String serviceId = "zwitscher-" + applicationHost + ":" + applicationPort; //Eindeutige Instanz
                    String fabioServiceTag = "urlprefix-/messages"; //Quell-URL, die auf den Service routen soll
                    URL serviceUrl = UrlBuilder.empty()
                            .withScheme("http")
                            .withHost(applicationHost)
                            .withPort(applicationPort)
                            .withPath("/messages").toUrl();

                    // Service bei Consul registrieren inklusive einem Health-Check auf die URL des REST-Endpunkts.
                    LOG.info("Registering service with ID {} on NAME {} with healthcheck URL {} and inbound ROUTE {}",
                              serviceId, serviceName, serviceUrl, fabioServiceTag);

                    Consul consul = Consul.builder().build(); // connect to Consul on localhost
                    AgentClient agentClient = consul.agentClient();
                    agentClient.register(applicationPort,
                                         serviceUrl,
                                         HEALTHCHECK_INTERVAL,
                                         serviceName,
                                         serviceId,
                                         fabioServiceTag);

                } catch (ConsulException e) {
                    LOG.error("No service registered @ Consul!", e);
                }
            }
        });

    }

}