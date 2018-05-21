package com.cdt.credito.conf;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
@EnableJpaAuditing(modifyOnCreate = false, dateTimeProviderRef = "dateTimeProvider")
public class AppConfig {

//	@Bean
//	public AuditorAware<Long> createAuditorProvider() {
//		return new SecurityAuditor();
//	}
//
//	public static class SecurityAuditor implements AuditorAware<Long> {
//		@Override
//		public Optional<Long> getCurrentAuditor() {
//			Optional<Long> result = null;
//			AuthenticatedUser user = SecurityUtils.getAuthenticatedUser();
//			if (user != null) {
//				result = Optional.of(user.getId());
//			}
//			return result;
//		}
//	}
	
	/*
	 * DateTime Provider to JPA Auditing
	 */
    @Bean
    DateTimeProvider dateTimeProvider() {
        return CurrentDateTimeProvider.INSTANCE;
    }

	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize("1536KB");
		factory.setMaxRequestSize("1536KB");
		return factory.createMultipartConfig();
	}

    /*
     * Configure the CORS filter
     */
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("*")
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
						.exposedHeaders("errorCode", "errorField", "errorMsg", "x-auth-token", "Content-Disposition");
			}
		};
	}
}
