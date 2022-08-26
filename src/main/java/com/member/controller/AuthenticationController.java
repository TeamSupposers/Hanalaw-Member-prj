package com.member.controller;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.member.request.AuthRequest;
import com.member.response.AuthResponse;
import com.member.security.utils.RSAUtils;
import com.member.service.AuthenticationService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authentication")
public class AuthenticationController {

	private final AuthenticationService authenticationService;
	
	@GetMapping("/key")
	public Mono<String> test() throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
		String strToEncrypt = "��ȣȭ�� ���ڿ��Դϴ�.";
		KeyPair keyPair = RSAUtils.createKeyPair(1024);  // Ű �� ����
		byte[] encryptedByte = RSAUtils.encrypt(strToEncrypt.getBytes(), keyPair.getPublic()); // ����Ű�� ��ȣȭ 
		return Mono.just(new String(Base64.getEncoder().encode(encryptedByte)));
	}
	
	@PostMapping("/login")
	public Mono<ResponseEntity<AuthResponse>> login(@RequestBody AuthRequest authRequest) {
		return authenticationService.getToken(authRequest);
	}

}