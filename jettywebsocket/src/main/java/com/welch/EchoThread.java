package com.welch;

import javax.websocket.Session;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by pdclwc on 18/06/2018.
 */
public class EchoThread extends Thread {

    private ConcurrentHashMap<String, Session> clientSessions;
    private static int i = 0;

    public EchoThread(ConcurrentHashMap<String, Session> clientSessions) {
        this.clientSessions = clientSessions;
    }

    public void run(){
        while(true){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clientSessions.values().forEach(session -> {
                try {
                    if(session.isOpen()){
                        session.getBasicRemote().sendText("hello from welch " + i++);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}

