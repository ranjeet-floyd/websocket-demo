package com.example.websocket.demo.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.WebSocketService;
import org.springframework.web.reactive.socket.server.support.HandshakeWebSocketService;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import org.springframework.web.reactive.socket.server.upgrade.ReactorNettyRequestUpgradeStrategy;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class WebSocketConfiguration {
    @Autowired
    Handler packetHandler;

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketConfiguration.class);
    public static final String INGESTION_WEBSOCKET_ENDPOINT = "/connect_ws";

    @Value("${webSocket.maxFramePayloadLengthInKB:500}")
    private Integer webSocketMaxFramePayloadLengthInKB;

    @Bean // This function maps the url to the server
    public HandlerMapping handlerMapping() {
        LOGGER.info("URL handler mapping for server.");
        Map<String, WebSocketHandler> map = new HashMap<>();
        map.put(INGESTION_WEBSOCKET_ENDPOINT, packetHandler);
        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setUrlMap(map);
        return mapping;
    }

    @Bean // Creating handler for the server
    public WebSocketHandlerAdapter handlerAdapter() {
        LOGGER.info("Registering server service.");
        return new WebSocketHandlerAdapter(webSocketService());
    }

    @Bean
    public WebSocketService webSocketService() {
        ReactorNettyRequestUpgradeStrategy reactorNettyRequestUpgradeStrategy =
                new ReactorNettyRequestUpgradeStrategy();
        reactorNettyRequestUpgradeStrategy.setMaxFramePayloadLength(
                webSocketMaxFramePayloadLengthInKB * 1024);
        return new HandshakeWebSocketService(reactorNettyRequestUpgradeStrategy);
    }
}
