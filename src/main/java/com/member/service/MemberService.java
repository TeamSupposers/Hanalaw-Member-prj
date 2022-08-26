package com.member.service;

import java.util.Arrays;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.member.consts.Role;
import com.member.entity.Authority;
import com.member.entity.Member;
import com.member.repository.AuthorityRepository;
import com.member.repository.MemberRepository;
import com.member.request.AuthRequest;
import com.member.request.JoinRequest;
import com.member.request.UpdateRequest;
import com.member.security.utils.PBKDF2Encoder;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final PBKDF2Encoder passwordEncoder;

	private final MemberRepository memberRepository;

	private final AuthorityRepository authorityRepository;

	private final InterestService interestService;

	public Mono<Member> join(JoinRequest joinRequest) {
		return isDuplicated(joinRequest.getUserId()).flatMap(isDup -> {
			if (isDup) {
				throw new DuplicateKeyException("중복된 키 입니다.");
			}
			return memberRepository
					.save(Member.builder().userId(joinRequest.getUserId()).userName(joinRequest.getUserName())
							.userPassword(passwordEncoder.encode(joinRequest.getUserPassword()))
							.phoneNumber(joinRequest.getPhoneNumber()).nickName(joinRequest.getNickName())
							.ageRange(joinRequest.getAgeRange()).enabled(true).build())
					.flatMap(member -> authorityRepository.save(new Authority(member.getMemberId(), Role.ROLE_USER))
							.thenReturn(member));
		});
	}

	public Mono<Member> login(AuthRequest authRequest) {
		return memberRepository.findByUserId(authRequest.getUserId())
				.flatMap(member -> authorityRepository.findById(member.getMemberId())
						.doOnNext(authority -> member.setRoles(Arrays.asList(authority.getRole()))).thenReturn(member))
				.filter(member -> member.getPassword().equals(passwordEncoder.encode(authRequest.getUserPassword())));
	}

	public Mono<Boolean> isDuplicated(String userId) {
		return memberRepository.findByUserId(userId).hasElement();
	}

	public Mono<Member> retrive(Long memberId) {
		return memberRepository.findById(memberId);

	}

	public Mono<Boolean> update(UpdateRequest updateRequest) {
		return retrive(updateRequest.getMemberId())
				.flatMap(findMember -> updateValidation(findMember, updateRequest)
						.flatMap(updatedMember -> interestService.updateInterest(updatedMember, updateRequest)));
	}

	public Mono<Member> updateValidation(Member findMember, UpdateRequest updateRequest) {
		findMember.setPassword(passwordEncoder.encode(updateRequest.getUserPassword()));
		findMember.setPhoneNumber(updateRequest.getPhoneNumber());
		findMember.setNickName(updateRequest.getNickName());
		findMember.setAgeRange(updateRequest.getAgeRange());
		return memberRepository.save(findMember);
	}

}