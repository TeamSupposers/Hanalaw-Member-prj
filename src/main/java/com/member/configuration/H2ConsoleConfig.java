package com.member.configuration;

import java.sql.SQLException;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@Profile("local")
public class H2ConsoleConfig {

	private Server webServer;

	@Value("${webclient.h2-console-port}")
	Integer h2ConsolePort;

	// ApplicationContext : Bean ����, Dependency Injection, ResourceLoader,
	// ApplicationEventPublisher�� ����
	// ContextRefreshedEvent: ApplicationContext�� �ʱ�ȭ �ÿ� �߻��ϴ� �̺�Ʈ
	@EventListener(ContextRefreshedEvent.class)
	public void start() throws SQLException {
		log.info("starting h2 console at port {}", h2ConsolePort);
		this.webServer = Server.createWebServer("-webPort", h2ConsolePort.toString());
		this.webServer.start();
	}

	// ContextClosedEvent: ApplicationContext�� ����ÿ� �߻��ϴ� �̺�Ʈ
	@EventListener(ContextClosedEvent.class)
	public void stop() {
		log.info("stopping h2 console at port {}", h2ConsolePort);
		this.webServer.stop();
	}
}
