package com.member.schedule;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.member.security.RSAContext;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class Scheduler {

	private final RSAContext rsaContext;
	
	@Async
	@Scheduled(cron = "0/30 * * * * ?")
	public void scheduleFixedRateTask() throws InterruptedException {
	    for(String uuid : rsaContext.getContext().keySet()) {
	    	Duration duration = Duration.between(rsaContext.getContext().get(uuid).getCreateDt(), LocalDateTime.now());
	    	log.info("uuid = {} and createDt = {}", uuid, rsaContext.getContext().get(uuid).getCreateDt());
	    	log.info("currentTime = {}", LocalDateTime.now());
	    	log.info("duration = {}", duration);
			if(duration.compareTo(Duration.ofSeconds(30L)) > 0) {
				rsaContext.getContext().remove(uuid);
				log.info("is rsaContext {} removed ? = {}", uuid, rsaContext.getContext().get(uuid) == null);
			}
	    }
	}
		
}
