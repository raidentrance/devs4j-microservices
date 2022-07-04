package com.devs4j.client.clients;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "devs4j-dragon-ball")
@LoadBalancerClient(name="devs4j-dragon-ball",configuration = Devs4jLoadBalancerConfiguration.class)
public interface DragonBallCharacterClient {

	@RequestMapping(method = RequestMethod.GET, value = "/application-name")
	public ResponseEntity<String> getApplicationName();
}
