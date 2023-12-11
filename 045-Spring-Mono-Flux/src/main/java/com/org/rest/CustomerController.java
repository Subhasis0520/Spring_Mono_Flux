package com.org.rest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.binding.CustomerEvent;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@RestController
public class CustomerController {

	@GetMapping(value = "/event", produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<CustomerEvent> getEvent(){
		CustomerEvent customer = new CustomerEvent("Rohit", LocalDateTime.now());
		
		return Mono.justOrEmpty(customer);
	}
	
	@GetMapping(value = "/events", produces =MediaType.TEXT_EVENT_STREAM_VALUE )
	public Flux<CustomerEvent> getEvents(){
		CustomerEvent event = new CustomerEvent("Virat", LocalDateTime.now());
		
		// create stream object to send data
		Stream<CustomerEvent> stream = Stream.generate(()-> event);
		
		//create flux object with stream
		Flux<CustomerEvent> fromStream = Flux.fromStream(stream);
		
		//setting response interval
		Flux<Long> interval = Flux.interval(Duration.ofSeconds(4));
		
		//combine interval and flux
		Flux<Tuple2<Long, CustomerEvent>> zip = Flux.zip(interval, fromStream);
		
		Flux<CustomerEvent> map = zip.map(Tuple2::getT2);
		
		return map;
	}
}
