package com.member.repository;

import org.springframework.data.repository.reactive.ReactiveSortingRepository;

import com.member.entity.Specific;

import reactor.core.publisher.Mono;

public interface SpecificRepository extends ReactiveSortingRepository <Specific, Long> {

	Mono<Long> findByRefreshToken(String refreshToken);
}
