package com.member.repository;

import org.springframework.data.repository.reactive.ReactiveSortingRepository;

import com.member.entity.Member;

import reactor.core.publisher.Mono;

public interface MemberRepository extends ReactiveSortingRepository <Member, Long> {

	Mono<Member> findByUserId(String userId);
}
