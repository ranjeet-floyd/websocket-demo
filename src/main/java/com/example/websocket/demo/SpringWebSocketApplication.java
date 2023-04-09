package com.example.websocket.demo;

import com.example.websocket.demo.client.SpringWebSocketHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.io.IOException;
import java.util.HashMap;

@SpringBootApplication(
        scanBasePackages = {"com.example.websocket.demo.client" }
)
public class SpringWebSocketApplication {
    public static void main(String[] args) throws IOException {
        HashMap<String, Object> props = new HashMap<>();
        props.put("server.port", 9999);

        new SpringApplicationBuilder()
                .sources(SpringWebSocketApplication.class)
                .properties(props)
                .run(args);

    }
}
