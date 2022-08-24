package com.member.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.member.request.AuthRequest;
import com.member.response.AuthResponse;
import com.member.security.utils.JWTUtil;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private JWTUtil jwtUtil;

	private final MemberService memberService;

	public Mono<ResponseEntity<AuthResponse>> getToken(AuthRequest authRequest) {
		return memberService.login(authRequest)
				.map(memberDetails -> ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken(memberDetails))))
				.switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
	}

}