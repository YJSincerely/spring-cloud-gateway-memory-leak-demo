package com.example.demo.util;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

public class requestBodyUtils {
    public static Publisher<String> modifyRequestBody(String modified) {
        return Mono.just("{\"modified\":123}");
    }
}
