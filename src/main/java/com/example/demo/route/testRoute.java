package com.example.demo.route;

import com.example.demo.filters.CachingRequestBodyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.stereotype.Component;
import com.example.demo.util.requestBodyUtils;

import java.util.HashSet;
import java.util.function.Function;

@Component
public class testRoute implements Function<PredicateSpec, Buildable<Route>> {
    private final String path = "/**";
    private final String domain = "test";
    private final String URL = "http://localhost:3001";
    protected final HashSet<String> statusCodes = new HashSet<>();

    @Autowired
    private CachingRequestBodyFilter cachingRequestBodyFilter;

    public testRoute() {
        statusCodes.add("500");
        statusCodes.add("502");
        statusCodes.add("503");
        statusCodes.add("504");
    }

    @Override
    public Buildable<Route> apply (PredicateSpec predicateSpec) {
        String finalPath = "/" + domain + path;
        return predicateSpec
                .path(finalPath)
                .filters(f ->
                    f
                    .filter(cachingRequestBodyFilter)
                    .modifyRequestBody(String.class,String.class, ((exchange, modified) -> {
                        System.out.println("In modifyRequestBodyFilter");
                        return requestBodyUtils.modifyRequestBody(modified);
                    }))
                    .circuitBreaker(config ->
                        config
                            .setName("timeout")
                            .setFallbackUri("forward:/timeout")
                            .setStatusCodes(statusCodes)
                    )
                )
                .uri(URL);
    }
}
