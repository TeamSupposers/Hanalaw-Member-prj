package com.member.service;

import org.springframework.stereotype.Service;

import com.member.consts.Role;
import com.member.entity.Authority;
import com.member.entity.Member;
import com.member.repository.AuthorityRepository;
import com.member.repository.MemberRepository;
import com.member.request.AuthRequest;
import com.member.request.JoinRequest;
import com.member.security.utils.PBKDF2Encoder;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final PBKDF2Encoder passwordEncoder;

	private final MemberRepository memberRepository;

	private final AuthorityRepository authorityRepository;

	public Mono<Member> save(JoinRequest joinRequest) {
		return memberRepository
				.save(new Member(joinRequest.getMemberName(), passwordEncoder.encode(joinRequest.getPassword())))
				.doOnSuccess(s -> {
					authorityRepository.save(new Authority(s.getId(), Role.ROLE_USER));
				});
	}

	public Mono<Member> login(AuthRequest authRequest) {
		return memberRepository.findByMemberName(authRequest.getMemberName()).filter(m -> passwordEncoder.encode(authRequest.getPassword()).equals(m.getPassword()));
	}
}