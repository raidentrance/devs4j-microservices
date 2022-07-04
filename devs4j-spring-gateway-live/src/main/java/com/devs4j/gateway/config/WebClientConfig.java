package com.devs4j.gateway.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

	@LoadBalanced
	@Bean
	public WebClient.Builder webclient(){
		return WebClient.builder();
	}
	
}
