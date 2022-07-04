package com.devs4j.dragonball.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Service;

@Service
public class FooService {

	@Autowired
	private Tracer tracer;

	private static final Logger log = LoggerFactory.getLogger(FooService.class);

	public void printLog() {
		Span newSpan = tracer.nextSpan().name("newSpan");
		try (Tracer.SpanInScope ws = this.tracer.withSpan(newSpan.start())) {
			// You can log an event on a span log.info("Log 3");
			try {
				Thread.sleep(20l);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			log.info("Test log");
		} finally {
			newSpan.end();
		}
		Span newSpan1 = tracer.nextSpan().name("newSpan2");
		try (Tracer.SpanInScope ws = this.tracer.withSpan(newSpan1.start())) {
			// You can log an event on a span log.info("Log 3");
			log.info("Test log new span 2");
		} finally {
			newSpan1.end();
		}
	}


}
