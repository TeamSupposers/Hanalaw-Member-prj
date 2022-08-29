package com.member.security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class RSAContext {

	private Map<String, String> context = new ConcurrentHashMap<String, String>();

}