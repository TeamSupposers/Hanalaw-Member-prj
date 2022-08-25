package com.member.service;

import java.util.Arrays;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.member.consts.Role;
import com.member.entity.Authority;
import com.member.entity.Interest;
import com.member.entity.Member;
import com.member.repository.AuthorityRepository;
import com.member.repository.InterestRepository;
import com.member.repository.MemberRepository;
import com.member.request.AuthRequest;
import com.member.request.InterestRequest;
import com.member.request.JoinRequest;
import com.member.security.utils.PBKDF2Encoder;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class InterestService {

	private final MemberRepository memberRepository;

	private final InterestRepository interestRepository;
	
	public Mono<Member> regist(InterestRequest interestRequest) {
		return memberRepository.findById(interestRequest.getMemberId()).flatMap(member -> {
			for(com.member.consts.Interest interest : interestRequest.getList()) {
				interestRepository.save(new Interest(member.getMemberId(),interest));
			}
			
		})
	}

}