package com.member.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.member.consts.Concern;
import com.member.entity.Interest;
import com.member.entity.Member;
import com.member.repository.InterestRepository;
import com.member.repository.MemberRepository;
import com.member.request.InterestRequest;
import com.member.request.UpdateRequest;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class InterestService {

	private final MemberRepository memberRepository;

	private final InterestRepository interestRepository;

	public Mono<Boolean> regist(InterestRequest interestRequest) {
		return memberRepository.findById(interestRequest.getMemberId())
				.flatMap(m -> interestToFlux(m, interestRequest.getInterest()).then(Mono.just(true)))
				.switchIfEmpty(Mono.just(false));
	}
	
	public Mono<Boolean> updateInterest(Member member, UpdateRequest updateRequest) {
		return deleteConcern(member)
				.flatMap(isDeleted -> interestToFlux(member, updateRequest.getInterest()).then(Mono.just(isDeleted))).switchIfEmpty(Mono.just(false));
	}
	
	public Mono<Boolean> deleteConcern(Member member) {
		return interestRepository.deleteInterestByMemberId(member.getMemberId());
	}
	
	public Flux<Interest> interestToFlux(Member member, List<Concern> list) {
		return Flux.fromIterable(list)
					.flatMap(concern -> insertInterest(member, concern));
	}
	
	public Mono<Interest> insertInterest(Member member, Concern concern) {
		return interestRepository.save(new Interest(member.getMemberId(), concern));
	}
}