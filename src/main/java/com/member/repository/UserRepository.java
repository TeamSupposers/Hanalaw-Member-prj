package com.member.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.member.entity.User;

import reactor.core.publisher.Mono;

public interface UserRepository extends R2dbcRepository <User, String> {

    Mono<User> findByUsername(String username);
}
