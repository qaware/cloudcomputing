package edu.qaware.cc.websocket.broadcast;

import javax.jws.WebService;

@WebService
public class BroadcastService {

    public void sendMessage(String message) {
        BroadcastServer.messageToAll(message);
    }

}
