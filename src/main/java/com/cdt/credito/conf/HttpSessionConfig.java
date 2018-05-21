package com.cdt.credito.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;

@Configuration
@EnableSpringHttpSession
public class HttpSessionConfig {
	
	@Value("${server.session.timeout}")
	private Integer maxInactiveInterval;

    @Bean
    public MapSessionRepository sessionRepository() {
    	MapSessionRepository sessionRepository = new MapSessionRepository();
    	sessionRepository.setDefaultMaxInactiveInterval(maxInactiveInterval);
    	return sessionRepository;
    }
    
	@Bean
	public HttpSessionStrategy httpSessionStrategy() {
		return new HeaderHttpSessionStrategy();
	}
}
