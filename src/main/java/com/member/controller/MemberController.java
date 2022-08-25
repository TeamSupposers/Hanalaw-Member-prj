package com.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.member.request.DuplicatedRequest;
import com.member.request.JoinRequest;
import com.member.response.DuplicatedResponse;
import com.member.response.JoinResponse;
import com.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	
	@PostMapping("/join")
    public Mono<ResponseEntity<JoinResponse>> join(@RequestBody JoinRequest joinRequest) {
    	return memberService.save(joinRequest)
    			.flatMap(member -> Mono.just(ResponseEntity.ok(new JoinResponse(member.getMemberId()))));
    }
	
	@PostMapping("/isDup")
	public Mono<ResponseEntity<DuplicatedResponse>> isDuplicated(@RequestBody DuplicatedRequest duplicatedRequest) {
		return memberService.isDuplicated(duplicatedRequest.getUserId())
				.flatMap(isDup -> Mono.just(ResponseEntity.ok(new DuplicatedResponse(isDup))));
	}
}
