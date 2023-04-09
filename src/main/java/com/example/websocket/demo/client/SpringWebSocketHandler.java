package com.example.websocket.demo.client;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;

@Component
public class SpringWebSocketHandler implements WebSocketHandler {

    @Getter
    private WebSocketSession webSocketSession;




    //  final Phaser phaser = new Phaser(1); // register self

    public SpringWebSocketHandler() {
    }

    @PostConstruct
    public void init() {
        try {
            var standardWebSocketClient = new StandardWebSocketClient();
            String url = "ws://localhost:8080/connect_ws";
            System.out.println("connect web socket connect: " +url);
            this.webSocketSession = standardWebSocketClient.doHandshake(this, new WebSocketHttpHeaders(),
                    URI.create(url)).get();
            // send message from spring client
            this.sendMessage("message from Spring-client");
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }


    public void sendMessage(String message) throws IOException {
        System.out.println("Send websocket message");
        webSocketSession.sendMessage(new TextMessage(message + " => " + webSocketSession.getId()));
      //  webSocketSession.close(CloseStatus.SERVER_ERROR);
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("Got WebSocket connection");
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
        System.out.println("Got Message payload: "+message.getPayload());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable th) {
        System.out.println("Got WebSocket error");
    }



    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("Closed WebSocket connection");
    }

    @Override
    public boolean supportsPartialMessages() {
        return true;
    }
}
