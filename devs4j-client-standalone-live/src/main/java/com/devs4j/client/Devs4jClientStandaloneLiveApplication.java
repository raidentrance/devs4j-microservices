package com.devs4j.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;

import com.devs4j.client.clients.DragonBallCharacterClient;

@SpringBootApplication
@EnableFeignClients
public class Devs4jClientStandaloneLiveApplication implements ApplicationRunner {

	@Autowired
	private DragonBallCharacterClient dragonBallClient;

//	@Autowired
//	private EurekaClient eurekaClient;

	private static final Logger log = LoggerFactory.getLogger(Devs4jClientStandaloneLiveApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(Devs4jClientStandaloneLiveApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		for (int i = 0; i < 10; i++) {
			ResponseEntity<String> responseEntity = dragonBallClient.getApplicationName();
			log.info("Status {}", responseEntity.getStatusCode());
			String body = responseEntity.getBody();
			log.info("Body {}", body);
		}
	}

	/**
	 * Implementación de cliente de Eureka
	 */
//	@Override
//	public void run(ApplicationArguments args) throws Exception {
//		Application application = eurekaClient.getApplication("devs4j-dragon-ball");
//		log.info("Application name {}",application.getName());
//		List<InstanceInfo> instances = application.getInstances();
//		for (InstanceInfo instanceInfo : instances) {
//			log.info("Ip address {}:{}",instanceInfo.getIPAddr(), instanceInfo.getPort());
//		}
//	}

}
