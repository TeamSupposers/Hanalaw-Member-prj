package com.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.member.request.DuplicatedRequest;
import com.member.request.InterestRequest;
import com.member.request.JoinRequest;
import com.member.response.DuplicatedResponse;
import com.member.response.JoinResponse;
import com.member.service.InterestService;
import com.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	
	private final InterestService interestService;
	
	@PostMapping("/isDup")
	public Mono<ResponseEntity<DuplicatedResponse>> isDuplicated(@RequestBody DuplicatedRequest duplicatedRequest) {
		return memberService.isDuplicated(duplicatedRequest.getUserId())
				.flatMap(isDup -> Mono.just(ResponseEntity.ok(new DuplicatedResponse(isDup))));
	}
	
	@PostMapping("/join")
    public Mono<ResponseEntity<JoinResponse>> join(@RequestBody JoinRequest joinRequest) {
    	return memberService.join(joinRequest)
    			.flatMap(member -> Mono.just(ResponseEntity.ok(new JoinResponse(member.getMemberId()))));
    }
	
	@PostMapping("/interest")
	public Mono<ResponseEntity<DuplicatedResponse>> registInterest(@RequestBody InterestRequest interestRequest) {
		return interestService.regist(interestRequest);
	}
}
