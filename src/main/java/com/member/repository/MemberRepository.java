package com.member.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.member.entity.Member;

import reactor.core.publisher.Mono;

public interface MemberRepository extends ReactiveCrudRepository <Member, Long> {

    Mono<Member> findByMemberName(String memberName);
}
