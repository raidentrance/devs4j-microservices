package com.devs4j.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class Devs4jConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(Devs4jConfigServerApplication.class, args);
	}

}
