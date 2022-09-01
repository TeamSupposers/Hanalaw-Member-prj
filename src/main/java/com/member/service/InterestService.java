package com.member.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
	
	public Map<String, Concern> getList() {
		Map<String, Concern> map = new HashMap<>();
		for(Concern c : Concern.values()) {
			map.put(c.getValue(), c);
		}
		return map;
	}
	
	public Mono<List<Concern>> retriveInterest(Long memberId) {
		return interestRepository.findByMemberId(memberId)
				.flatMap(t -> Flux.just(t.getConcern())).collect(Collectors.toList());
	}
	
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