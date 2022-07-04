package com.devs4j.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class Devs4jGatewayConfig {
	@Autowired
	private Devs4jAuthFilter authFilter;
	
	@Bean
	@Profile("localhostRouter-noEureka")
	public RouteLocator configLocalNoEureka(RouteLocatorBuilder builder) {
		return builder.routes().route(r -> r.path("/api/v1/dragonball/*").uri("http://localhost:8082"))
				.route(r -> r.path("/api/v1/gameofthrones/*").uri("http://localhost:8083")).build();
	}

	@Bean
	@Profile("localhost-eureka")
	public RouteLocator configLocalEureka(RouteLocatorBuilder builder) {
		return builder.routes().route(r -> r.path("/api/v1/dragonball/*").uri("lb://devs4j-dragon-ball"))
				.route(r -> r.path("/api/v1/gameofthrones/*").uri("lb://devs4j-game-of-thrones")).build();
	}

	@Bean
	@Profile("localhost-eureka-cb")
	public RouteLocator configLocalEurekaCb(RouteLocatorBuilder builder) {
		return builder.routes()
					.route(r -> r.path("/api/v1/dragonball/*")
							.filters(f -> {
								f.circuitBreaker(
										c -> c.setName("failoverCB")
										.setFallbackUri("forward:/api/v1/db-failover/dragonball/characters")
										.setRouteId("dbFailover"));
								f.filter(authFilter);
								return f;
							})
							.uri("lb://devs4j-dragon-ball"))
					.route(r -> r.path("/api/v1/gameofthrones/*")
							.filters(f->f.filter(authFilter))
							.uri("lb://devs4j-game-of-thrones"))
					.route(r -> r.path("/api/v1/db-failover/dragonball/characters")
							.filters(f->f.filter(authFilter))
							.uri("lb://devs4j-dragon-ball-failover"))
					.route(r->r.path("/auth/**")
							.uri("lb://devs4j-auth"))
				.build();
	}

}
