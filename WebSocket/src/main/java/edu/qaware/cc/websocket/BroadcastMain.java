package edu.qaware.cc.websocket;

import edu.qaware.cc.websocket.broadcast.BroadcastServer;
import edu.qaware.cc.websocket.broadcast.BroadcastService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.webbitserver.WebServer;
import org.webbitserver.WebServers;
import org.webbitserver.handler.StaticFileHandler;

import javax.xml.ws.Endpoint;

public class BroadcastMain {

    private static final Logger LOG = LoggerFactory.getLogger(BroadcastMain.class);

    public static final String STATIC_DIR
            = "C:\\2_Projekte\\QAWARE-VL-CLOUDCOMPUTING\\5-codebase\\m02-kommunikation\\WebSocket\\src\\main\\web";

    public static final String WS_URL
            = "http://localhost:2897/webservices/broadcast";

    public static void main(String[] args) {
        WebServer webServer = WebServers.createWebServer(2898)
                .add("/broadcastwebsocket", new BroadcastServer())
                .add(new StaticFileHandler(STATIC_DIR));
        webServer.start();
        LOG.info("Server running at {}", webServer.getUri());

        Endpoint.publish(WS_URL, new BroadcastService());
        LOG.info("Webservice running at {} with WSDL {}?WSDL", WS_URL, WS_URL);
    }

}
