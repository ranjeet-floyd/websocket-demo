package com.example.websocket.demo.client;

import com.example.websocket.demo.ClientDemoApplication;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.io.IOException;

@ClientEndpoint
public class DemoClient {
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected to endpoint: " + session.getId());

        try {
            String msg = "Msg from Client ! ";
            System.out.println("Sending message to endpoint: " + msg);
            session.getBasicRemote().sendText(msg);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    @OnMessage
    public void processMessage(String message) {
        System.out.println("Received message in client: " + message);
        ClientDemoApplication.messageLatch.countDown();
    }

    @OnError
    public void processError(Throwable t) {
        t.printStackTrace();
    }
}
