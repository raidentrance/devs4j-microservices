package com.devs4j.got.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devs4j.got.services.TranslationService;

@RestController
@RequestMapping("/translations")
public class MessageController {

	@Autowired
	private TranslationService service;
	private static final Logger log = LoggerFactory.getLogger(MessageController.class);

	@GetMapping
	public ResponseEntity<String> getTranslation(@RequestParam("message") String message) {
		log.info("Message received {} ", message);
		Optional<String> translation = service.getTranslation(message);
		if(translation.isPresent()) {
			return ResponseEntity.ok(translation.get());
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping
	public void clearCache(@RequestParam("message") String message) {
		log.info("Cleaning cache for {}",message);
		service.clearCache(message);
	}
}
