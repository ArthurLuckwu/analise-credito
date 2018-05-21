package com.cdt.credito.conf;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;
import org.springframework.security.web.savedrequest.NullRequestCache;

import com.cdt.credito.errorHandling.UnauthorizedEntryPoint;

@EnableWebSecurity
public class SecurityConfig {

	@Configuration
	public static class AuthTokenSessionSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

		@Resource
		private UnauthorizedEntryPoint unauthorizedEntryPoint;
		
		@Resource(name="accessDeniedHandler")
		private AccessDeniedHandler accessDeniedHandler;
		
		protected AuthTokenSessionSecurityConfigurationAdapter() {
	    	//Disable default configuration
	        super(true);
		}
		
		@Override
		public void configure(final WebSecurity web) throws Exception {
			// Only use in development
//			web.debug(true);
			web.ignoring().antMatchers(HttpMethod.GET,"/", "/index.html", "/app/**", "/scripts/**", "/assets/**", "/fonts/**", "/app/**", "/styles/**", "/android-chrome-192x192.png", "/apple-touch-icon.png", "favicon.ico");
			web.ignoring().antMatchers(HttpMethod.GET, "/**.bundle.css", "/**.bundle.js", "/**.woff2", "/**.woff", "/**.ttf");
			web.ignoring().antMatchers(HttpMethod.OPTIONS,"/**"); // Needed for CORS filter
			web.ignoring().antMatchers("/health", "/info", "/swagger-ui**", "/v2/api-docs**", "/swagger-resources/**", "/webjars/**", "/validatorUrl?**");
		}

		@Override
		protected void configure(final HttpSecurity http) throws Exception {
			http
		        .csrf().disable()
		        .cors().and()
		        .addFilter(new WebAsyncManagerIntegrationFilter())
				.exceptionHandling().accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(unauthorizedEntryPoint).and()
		        .headers().and()
		        .sessionManagement().and()
		        .securityContext().and()
				.requestCache().requestCache(new NullRequestCache()).and()
		        .anonymous().and()
		        .servletApi().and();

	        http
				.authorizeRequests()
//				.antMatchers("/**").permitAll()

				.antMatchers("/auth/captcha", "/auth/login**").permitAll()

//				.antMatchers("/processoCobranca/simularParcelamento**").permitAll()
//
				.anyRequest().authenticated();
		}
	}
}
