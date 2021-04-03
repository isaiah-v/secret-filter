package org.ivcode.secretfilter;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


/**
 * Security Settings
 * @author isaiah
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests(authz -> authz
					.antMatchers("/swagger-ui/*", "/v3/api-docs", "/v3/api-docs/*", "/info")
					.permitAll()
					.anyRequest()
					.authenticated())
			.oauth2ResourceServer(oauth2 -> oauth2
					.jwt());
	}
}