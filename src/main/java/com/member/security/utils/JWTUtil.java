package com.member.security.utils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.member.entity.Member;
import com.member.response.AuthResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JWTUtil {

	@Value("${springbootwebfluxjjwt.jjwt.secret}")
	private String secret;

	@Value("${springbootwebfluxjjwt.jjwt.expiration}")
	private String accessTokenExpirationTime;

	@Value("${springbootwebfluxjjwt.jjwt.re-expiration}")
	private String refreshTokenExpirationTime;

	private Key key;

	@PostConstruct
	public void init() {
		this.key = Keys.hmacShaKeyFor(secret.getBytes());
	}

	public Claims getAllClaimsFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}

	public String getUsernameFromToken(String token) {
		return getAllClaimsFromToken(token).getSubject();
	}

	public Date getExpirationDateFromToken(String token) {
		return getAllClaimsFromToken(token).getExpiration();
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public AuthResponse generateToken(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", member.getRoles());
        AuthResponse response = new AuthResponse();
        response.setAccessToken(doGenerateToken(claims, member.getUsername()));
        response.setRefreshToken(doGenerateRefreshToken(member.getMemberId()));
        return response;
    }

	public AuthResponse generateTokenByRefresh(Member member) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", member.getRoles());
		AuthResponse response = new AuthResponse();
		response.setAccessToken(doGenerateToken(claims, member.getUsername()));
		response.setRefreshToken(member.getRefreshToken());
		return response;
	}

	private String doGenerateToken(Map<String, Object> claims, String username) {
		Long expirationTimeLong = Long.parseLong(accessTokenExpirationTime); // in second
		final Date createdDate = new Date();
		final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong * 1000);

		return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(createdDate)
				.setExpiration(expirationDate).signWith(key).compact();
	}

	// jwt refresh 토큰 생성
	public String doGenerateRefreshToken(Long memberId) {
		Long expirationTimeLong = Long.parseLong(accessTokenExpirationTime); // in second
		final Date createdDate = new Date();
		final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong * 1000);

		String refreshToken = Jwts.builder().setIssuedAt(createdDate).setExpiration(expirationDate).signWith(key)
				.compact();
		
		return refreshToken;
	}

	public Boolean validateToken(String token) {
		return !isTokenExpired(token);
	}

}