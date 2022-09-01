package com.member.controller;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.member.request.RefreshRequest;
import com.member.request.Test;
import com.member.response.AuthResponse;
import com.member.response.KeyResponse;
import com.member.security.KeyProp;
import com.member.security.RSAContext;
import com.member.security.utils.RSAUtils;
import com.member.service.AuthenticationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authentication")
@Slf4j
public class AuthenticationController {

	private final AuthenticationService authenticationService;

	private final RSAContext rsaContext;

	@GetMapping("/key")
	public Mono<KeyResponse> getKey() throws NoSuchAlgorithmException {

		KeyPair keyPair = RSAUtils.createKeyPair(1024);
		String base64PrivateKey = RSAUtils.privateKeyToBase64(keyPair); // 개인키를 base64로
		String base64PublicKey = RSAUtils.publicKeyToBase64(keyPair); // 공개키를 base64로

		KeyProp prop = new KeyProp();
		prop.setPrivateKey(base64PrivateKey);

		String uuid = UUID.randomUUID().toString();
		rsaContext.getContext().put(uuid, prop);

		KeyResponse keyResponse = new KeyResponse();
		keyResponse.setPublicKey(base64PublicKey);
		keyResponse.setUuid(uuid);

		log.info("is rsaContext {} exists ? = {} ", uuid, rsaContext.getContext().get(uuid) != null);

		return Mono.just(keyResponse);
	}

	@PostMapping("/encrypt")
	public Mono<String> test(@RequestBody Test test) throws NoSuchAlgorithmException, InvalidKeyException,
			InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException {
		return Mono.just(RSAUtils.encrypt(test.getPw(), test.getPub()));
	}

	@PostMapping("/refresh")
	public Mono<ResponseEntity<AuthResponse>> refresh(@RequestBody RefreshRequest refreshRequest)
			throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, IllegalBlockSizeException,
			BadPaddingException, NoSuchPaddingException {
		return authenticationService.refresh(refreshRequest.getRefreshToken()).flatMap(auth -> Mono.just(ResponseEntity.ok(auth)))
				.switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
	}
}