package com.example.websocket.demo.server;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class Handler implements WebSocketHandler {

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        System.out.println("Session Id : " + session.getId());
        Flux<String> messages =
                session.receive()
                        .map(webSocketMessage -> "From Server: Received: [ " + webSocketMessage.getPayloadAsText()+ " ]");
                      //  .startWith("Hello");
        return session.send(messages.map(session::textMessage));
    }
}
