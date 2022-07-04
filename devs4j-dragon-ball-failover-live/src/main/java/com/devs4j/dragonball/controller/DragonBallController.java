package com.devs4j.dragonball.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.javafaker.Faker;

@RestController
@RequestMapping("/api/v1/db-failover/dragonball/characters")
public class DragonBallController {

	private Faker faker = new Faker();

	private List<String> characters = new ArrayList<>();
	
	private static final Logger log = LoggerFactory.getLogger(DragonBallController.class);


	@PostConstruct
	public void init() {
		for (int i = 0; i < 20; i++) {
			characters.add(String.format("Failover - %s", faker.dragonBall().character()));
		}
	}

	@GetMapping
	public ResponseEntity<List<String>> get() {
		log.info("Getting characters db-fo");
		return ResponseEntity.ok(characters);
	}

}
