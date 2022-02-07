package com.example.demo;

import com.example.demo.route.testRoute;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {


	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	RouteLocator myRoute(RouteLocatorBuilder builder, testRoute testRoute){
		return builder.routes()
				.route(testRoute)
				.build();
	}

}
