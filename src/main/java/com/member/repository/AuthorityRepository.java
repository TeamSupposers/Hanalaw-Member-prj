package com.member.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.member.entity.Authority;

public interface AuthorityRepository extends ReactiveCrudRepository <Authority, Long> {

}
