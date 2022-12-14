package com.member.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.member.security.AuthenticationManager;
import com.member.security.repository.SecurityContextRepository;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

	private AuthenticationManager authenticationManager;

	private SecurityContextRepository securityContextRepository;

	@Bean
	public SecurityWebFilterChain securitygWebFilterChain(ServerHttpSecurity http) {
		return http.exceptionHandling()
				.authenticationEntryPoint(
						(swe, e) -> Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)))
				.accessDeniedHandler(
						(swe, e) -> Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN)))
				.and().csrf().disable().formLogin().disable().httpBasic().disable()
				.authenticationManager(authenticationManager).securityContextRepository(securityContextRepository)
				.authorizeExchange().pathMatchers(HttpMethod.OPTIONS).permitAll().pathMatchers("/hanaMbr/member/isDup")
				.permitAll().pathMatchers("/hanaMbr/member/login").permitAll().pathMatchers("/hanaMbr/member")
				.permitAll().pathMatchers("/hanaMbr/authentication/key").permitAll()
				.pathMatchers("/hanaMbr/authentication/encrypt").permitAll()
				.pathMatchers("/hanaMbr/authentication/refresh").permitAll().pathMatchers("/hanaMbr/interest")
				.permitAll().pathMatchers("/hanaMbr/h2-console").permitAll().anyExchange().authenticated().and()
				.build();
	}

}