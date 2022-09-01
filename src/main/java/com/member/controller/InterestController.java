package com.member.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.member.consts.Concern;
import com.member.request.InterestRequest;
import com.member.response.InterestResponse;
import com.member.service.InterestService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/interest")
public class InterestController {

	private final InterestService interestService;
	
	@GetMapping
	public Mono<ResponseEntity<Map<String, Concern>>> retriveInterest() {
		return Mono.just(ResponseEntity.ok(interestService.getList()));
	}
	
	@PostMapping
	public Mono<ResponseEntity<InterestResponse>> registInterest(@RequestBody InterestRequest interestRequest) {
		return interestService.regist(interestRequest)
				.flatMap(isSaved -> Mono.just(ResponseEntity.ok(new InterestResponse(isSaved))));
	}
	
}
