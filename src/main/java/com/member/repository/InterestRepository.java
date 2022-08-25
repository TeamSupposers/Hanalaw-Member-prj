package com.member.repository;

import org.springframework.data.repository.reactive.ReactiveSortingRepository;

import com.member.entity.Interest;
import com.member.entity.Member;

import reactor.core.publisher.Mono;

public interface InterestRepository extends ReactiveSortingRepository <Interest, Long> {

}
