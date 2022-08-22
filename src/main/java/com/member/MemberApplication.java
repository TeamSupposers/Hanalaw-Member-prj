package com.member;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import com.member.consts.Role;
import com.member.entity.User;
import com.member.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@EnableR2dbcRepositories
@SpringBootApplication
@RequiredArgsConstructor
public class MemberApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemberApplication.class, args);
	}

    private final UserRepository userRepository;
    
	@PostConstruct
    public void init() {
    	userRepository.save(new User("user", "cBrlgyL2GI2GINuLUUwgojITuIufFycpLG4490dhGtY=", true, Arrays.asList(Role.ROLE_USER)));
    	userRepository.save(new User("admin", "dQNjUIMorJb8Ubj2+wVGYp6eAeYkdekqAcnYp+aRq5w=", true, Arrays.asList(Role.ROLE_ADMIN)));
    }
}
