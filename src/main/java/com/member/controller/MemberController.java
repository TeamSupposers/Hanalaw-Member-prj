package com.member.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.member.request.JoinRequest;
import com.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

	private static final Logger logger = LogManager.getLogger(MemberController.class);

	private final MemberService memberService;
	
	@GetMapping("/test")
	public Mono<String> getMember(ServerHttpRequest request, ServerHttpResponse response) {
		HttpHeaders headers = request.getHeaders();
		headers.forEach((k, v) -> {
			logger.info(k + " : " + v);
		});
		
		return Mono.just("Member info desu");
	}
	
	@PostMapping("/join")
    public Mono<String> join(@RequestBody JoinRequest joinRequest) {
    	return memberService.save(joinRequest).thenReturn("Complete");
    }
}
