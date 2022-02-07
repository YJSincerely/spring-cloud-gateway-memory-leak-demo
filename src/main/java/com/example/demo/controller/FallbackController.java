package com.example.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

@RestController
public class FallbackController {

    private WebClient webClient = WebClient.create("http://localhost:3001");

    @PostMapping("/timeout")
    public String timeout(ServerWebExchange exchange) {
        System.out.println("In timeout fallback");
        return "timeout";

    }
}
