package com.member.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.stereotype.Service;

import com.member.consts.Role;
import com.member.entity.Authority;
import com.member.exception.TokenValidationException;
import com.member.repository.AuthorityRepository;
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

	private final AuthorityRepository authorityRepository;

	public Mono<AuthResponse> refresh(String refreshToken) throws InvalidKeyException, InvalidKeySpecException,
			NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, TokenValidationException {
		return memberRepository.findByRefreshToken(refreshToken).flatMap(member -> {
			if (!jwtUtil.validateToken(member.getRefreshToken())) {
				throw new TokenValidationException();
			}
			return getAuthorityList(member.getMemberId()).flatMap(list -> {
				List<Role> roles = new ArrayList<>();
				for (Authority authority : list) {
					roles.add(authority.getRole());
				}
				member.setRoles(roles);
				AuthResponse response = jwtUtil.generateTokenByRefresh(member);
				member.setRefreshToken(response.getRefreshToken());
				return memberRepository.save(member)
						.doOnNext(save -> response.setRefreshToken(member.getRefreshToken())).thenReturn(response);
			});
		});
		
	}

	public Mono<List<Authority>> getAuthorityList(Long memberId) {
		return authorityRepository.findByMemberId(memberId).collectList();
	}
}