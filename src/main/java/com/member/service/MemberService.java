package com.member.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.member.consts.Role;
import com.member.entity.Authority;
import com.member.entity.Member;
import com.member.exception.KeyValidationException;
import com.member.repository.AuthorityRepository;
import com.member.repository.MemberRepository;
import com.member.request.AuthRequest;
import com.member.request.JoinRequest;
import com.member.request.UpdateRequest;
import com.member.response.AuthResponse;
import com.member.security.RSAContext;
import com.member.security.utils.JWTUtil;
import com.member.security.utils.PBKDF2Encoder;
import com.member.security.utils.RSAUtils;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final PBKDF2Encoder passwordEncoder;

	private final MemberRepository memberRepository;

	private final AuthorityRepository authorityRepository;

	private final InterestService interestService;

	private final RSAContext rsaContext;

	private final JWTUtil jwtUtil;

	public Mono<Member> join(JoinRequest joinRequest) {
		return isDuplicated(joinRequest.getUserId()).flatMap(isDup -> {
			if (isDup) {
				throw new DuplicateKeyException("중복된 아이디입니다.");
			} else if (rsaContext.getContext().get(joinRequest.getUuid()) == null) {
				throw new KeyValidationException("유효한 uuid가 아닙니다.");
			}
			return memberRepository
					.save(Member.builder().userId(joinRequest.getUserId()).userName(joinRequest.getUserName())
							.userPassword(passwordEncoder.encode(RSAUtils.decrypt(joinRequest.getUserPassword(),
									rsaContext.getContext().get(joinRequest.getUuid()).getPrivateKey())))
							.phoneNumber(joinRequest.getPhoneNumber()).nickName(joinRequest.getNickName())
							.ageRange(joinRequest.getAgeRange()).enabled(true).build())
					.flatMap(member -> authorityRepository.save(new Authority(member.getMemberId(), Role.ROLE_USER))
							.thenReturn(member));
		});
	}

	public Mono<ResponseEntity<AuthResponse>> login(AuthRequest authRequest)
			throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException, KeyValidationException {
		return memberRepository.findByUserId(authRequest.getUserId())
				.flatMap(member -> authorityRepository.findById(member.getMemberId())
						.doOnNext(authority -> member.setRoles(Arrays.asList(authority.getRole()))).thenReturn(member))
				.filter(member -> {
					if(rsaContext.getContext().get(authRequest.getUuid()) == null) {
						throw new KeyValidationException("유효한 uuid가 아닙니다.");
					}
					return member.getPassword()
							.equals(passwordEncoder.encode(RSAUtils.decrypt(authRequest.getUserPassword(),
									rsaContext.getContext().get(authRequest.getUuid()).getPrivateKey())));
				}).flatMap(member -> {
					AuthResponse response = jwtUtil.generateToken(member);
					member.setRefreshToken(response.getRefreshToken());
					return memberRepository.save(member)
							.doOnNext(save -> response.setRefreshToken(member.getRefreshToken())).thenReturn(response);
				}).flatMap(response -> Mono.just(ResponseEntity.ok(response)))
				.switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
	}

	public Mono<Boolean> isDuplicated(String userId) {
		return memberRepository.findByUserId(userId).hasElement();
	}

	public Mono<Member> retrive(Long memberId) {
		return memberRepository.findById(memberId)
				.flatMap(member -> interestService.retriveInterest(member.getMemberId())
						.doOnSuccess(list -> member.setInterests(list)).thenReturn(member));

	}

	public Mono<Boolean> update(UpdateRequest updateRequest) {
		return retrive(updateRequest.getMemberId()).flatMap(findMember -> updateValidation(findMember, updateRequest)
				.flatMap(updatedMember -> interestService.updateInterest(updatedMember, updateRequest)));
	}

	public Mono<Member> updateValidation(Member findMember, UpdateRequest updateRequest) {
		if(rsaContext.getContext().get(updateRequest.getUuid()) == null) {
			throw new KeyValidationException("유효한 uuid가 아닙니다.");
		}
		findMember.setPassword(passwordEncoder.encode(RSAUtils.decrypt(updateRequest.getUserPassword(),
				rsaContext.getContext().get(updateRequest.getUuid()).getPrivateKey())));
		findMember.setPhoneNumber(updateRequest.getPhoneNumber());
		findMember.setNickName(updateRequest.getNickName());
		findMember.setAgeRange(updateRequest.getAgeRange());
		return memberRepository.save(findMember);
	}

}