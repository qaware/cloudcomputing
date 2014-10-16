package edu.qaware.cc.websocket.broadcast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.webbitserver.BaseWebSocketHandler;
import org.webbitserver.WebSocketConnection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BroadcastServer extends BaseWebSocketHandler {

    private static final Logger LOG = LoggerFactory.getLogger(BroadcastServer.class);

    public static List<WebSocketConnection> connections =
            Collections.synchronizedList(new ArrayList<WebSocketConnection>());

    @Override
    public void onOpen(WebSocketConnection connection) {
        connections.add(connection);
        LOG.info("Connection opened with {}", connection.httpRequest().remoteAddress().toString());
    }

    @Override
    public void onClose(WebSocketConnection connection) {
        connections.remove(connection);
        LOG.info("Connection closed with {}", connection.httpRequest().remoteAddress().toString());
    }

    @Override
    public void onMessage(WebSocketConnection connection, String msg) throws Throwable {
        LOG.info("Message from {}: {}", connection.httpRequest().remoteAddress().toString(), msg);
    }

    public static void messageToAll(String message) {
        for (WebSocketConnection connection : connections) {
            connection.send(message);
            LOG.info("Sending message {}", message);
        }
    }

}
