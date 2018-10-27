package com.welch.secure;

import com.welch.EventSocketClient;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.net.URI;

public class EventClient {

    public static void main(String[] args) {
        String jssecacerts = EventClient.class.getClassLoader().getResource("jssecacerts").getPath();
        System.setProperty("javax.net.ssl.trustStore", jssecacerts);
        URI uri = URI.create("wss://localhost:8098/events/");
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            Session session = container.connectToServer(EventSocketClient.class,uri);
            for (int i = 0; i < 10; i++) {
                Thread.sleep(1000);
                session.getBasicRemote().sendText("Hello");
            }
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }
}

