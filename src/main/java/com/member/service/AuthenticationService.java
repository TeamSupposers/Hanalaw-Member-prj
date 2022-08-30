package com.member.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.stereotype.Service;

import com.member.repository.MemberRepository;
import com.member.response.AuthResponse;
import com.member.security.utils.JWTUtil;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final JWTUtil jwtUtil;
	
	private final MemberRepository memberRepository;
	
	public Mono<AuthResponse> refresh(String refreshToken) throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		return memberRepository.findByRefreshToken(refreshToken).flatMap(member -> {

			if(!jwtUtil.validateToken(member.getRefreshToken())) {
				throw new IllegalStateException();
			}
			AuthResponse response = jwtUtil.generateTokenByRefresh(member);
			member.setRefreshToken(response.getRefreshToken());
			return memberRepository.save(member).doOnNext(save -> response.setRefreshToken(member.getRefreshToken())).thenReturn(response);
		});
	}

}