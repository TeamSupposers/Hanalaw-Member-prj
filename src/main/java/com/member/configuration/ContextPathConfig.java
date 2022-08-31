package com.member.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.WebFilter;

@Configuration
public class ContextPathConfig {

	@Value("${server.servlet.context-path}")
    private String contextPath;
	
	@Bean
	public WebFilter contextPathWebFilter() {
	    return (exchange, chain) -> {
	        ServerHttpRequest request = exchange.getRequest();
	        if (request.getURI().getPath().startsWith(contextPath)) {
	            return chain.filter(
	                exchange.mutate()
	                .request(request.mutate().contextPath(contextPath).build())
	                .build());
	        }
	        return chain.filter(exchange);
	    };
	}

}
