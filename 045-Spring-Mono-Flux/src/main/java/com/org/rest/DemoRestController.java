package com.org.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class DemoRestController {

	@GetMapping("/mono")
	public Mono<String> singleResponse(){
		return Mono.just("Hello world");
	}
	
	@GetMapping("/flux")
	public Flux<Integer> multipleResponse(){
		return Flux.range(0, 10);
	}
}
