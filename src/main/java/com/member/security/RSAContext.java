package com.member.security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class RSAContext {

	private Map<String, KeyProp> context = new ConcurrentHashMap<String, KeyProp>();

}