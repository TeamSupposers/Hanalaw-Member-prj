package com.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.member.request.DuplicatedRequest;
import com.member.request.JoinRequest;
import com.member.request.UpdateRequest;
import com.member.response.DuplicatedResponse;
import com.member.response.JoinResponse;
import com.member.response.MemberResponse;
import com.member.response.UpdateResponse;
import com.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/isDup")
	public Mono<ResponseEntity<DuplicatedResponse>> isDuplicated(@RequestBody DuplicatedRequest duplicatedRequest) {
		return memberService.isDuplicated(duplicatedRequest.getUserId())
				.flatMap(isDup -> Mono.just(ResponseEntity.ok(new DuplicatedResponse(isDup))));
	}

	@PostMapping
	public Mono<ResponseEntity<JoinResponse>> join(@RequestBody JoinRequest joinRequest) {
		return memberService.join(joinRequest)
				.flatMap(member -> Mono.just(ResponseEntity.ok(new JoinResponse(member.getMemberId()))));
	}

	@GetMapping("{memberId}")
	public Mono<ResponseEntity<MemberResponse>> retriveMember(@PathVariable Long memberId) {
		return memberService.retrive(memberId).flatMap(member -> Mono.just(ResponseEntity.ok(new MemberResponse(member))));
	}
	
	@PutMapping
	public Mono<ResponseEntity<UpdateResponse>> updateMember(@RequestBody UpdateRequest updateRequest) {
		return memberService.update(updateRequest).flatMap(isUpdated -> Mono.just(ResponseEntity.ok(new UpdateResponse(isUpdated))));
	}
}
