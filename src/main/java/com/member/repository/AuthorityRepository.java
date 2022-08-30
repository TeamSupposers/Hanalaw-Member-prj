package com.member.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.member.entity.Authority;

import reactor.core.publisher.Flux;

public interface AuthorityRepository extends ReactiveCrudRepository <Authority, Long> {

	Flux<Authority> findByMemberId (Long memberId);
}
