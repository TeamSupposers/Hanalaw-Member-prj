package com.member.security;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class KeyProp {

	private String privateKey;
	
	private LocalDateTime createDt = LocalDateTime.now();

}