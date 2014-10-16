package edu.qaware.cc.rest;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import org.glassfish.grizzly.http.server.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;

public class HelloWorldMain {

    private static final Logger LOG = LoggerFactory.getLogger(HelloWorldMain.class);

    public static final String RESOURCE_ROOT_PACKAGE = "edu.qaware.cc.rest.helloworld";
    public static final String BASE_URI = "http://localhost/";
    public static final int BASE_PORT = 9999;

    private static URI getBaseURI() {
        return UriBuilder.fromUri(BASE_URI).port(BASE_PORT).build();
    }

    private static HttpServer startServer() throws IOException {
        ResourceConfig rc = new PackagesResourceConfig(RESOURCE_ROOT_PACKAGE);
        rc.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, true);
        return GrizzlyServerFactory.createHttpServer(getBaseURI(), rc);
    }

    public static void main(String[] args) throws IOException {
        HttpServer httpServer = startServer();
        LOG.info("REST app started at {} with WADL available at {}application.wadl", getBaseURI(), getBaseURI());
        System.in.read();
        httpServer.stop();
    }

}
