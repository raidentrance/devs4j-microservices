package com.devs4j.dragonball.controller;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.devs4j.dragonball.config.DragonBallConfig;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;

@RestController
@RequestMapping("/application-name")
public class ApplicationNameController {

	@Autowired
	private DragonBallConfig configuration;

	@Autowired
	private MeterRegistry registry;

	private static final Logger log = LoggerFactory.getLogger(ApplicationNameController.class);

	@GetMapping
	@Timed("devs4j.dragonballl.name.get")
	public ResponseEntity<String> getAppName() {
		log.info("Getting application name");
		int value = new Random().nextInt(5);
		registry.counter("devs4j.dragonball.name", "level", (value < 3) ? "jr" : "sr").increment(value);
		if (value < 3) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok(configuration.getApplicationName());
	}
	
	

}
