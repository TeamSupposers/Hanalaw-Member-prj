package com.member.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/member")
public class MemberController {

	private static final Logger logger = LogManager.getLogger(MemberController.class);

	@GetMapping
	public Mono<String> getMember(ServerHttpRequest request, ServerHttpResponse response) {
		HttpHeaders headers = request.getHeaders();
		headers.forEach((k, v) -> {
			logger.info(k + " : " + v);
		});
		
		return Mono.just("Member info desu");
	}
}
