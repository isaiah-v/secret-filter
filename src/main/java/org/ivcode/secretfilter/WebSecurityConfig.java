package org.ivcode.secretfilter;

import static org.ivcode.secretfilter.authconverter.AuthenticationConverters.*;
import static org.springframework.http.HttpMethod.*;

import org.ivcode.secretfilter.utils.AccessStringBuilder;
import org.springframework.beans.factory.annotation.Value;
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
	
	@Value("${authority.client}")
	private String clientAuthority;
	
	@Value("${authority.admin}")
	private String adminAuthority;
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		String clientAccess = new AccessStringBuilder()
				.hasAuthority(clientAuthority)
				.build();
		
		String clientAdminAccess = new AccessStringBuilder()
				.hasAuthority(clientAuthority)
				.hasAuthority(adminAuthority)
				.build();
		
		http
			.authorizeRequests(authz -> authz
					.antMatchers("/", "/info", "/swagger-ui/*", "/v3/api-docs", "/v3/api-docs/*").permitAll()
					.antMatchers(OPTIONS).permitAll()
					.antMatchers(GET).access(clientAccess)
					.antMatchers(DELETE).access(clientAdminAccess)
					.antMatchers(PATCH).access(clientAdminAccess)
					.antMatchers(POST).access(clientAdminAccess)
					.antMatchers(PUT).access(clientAdminAccess)
					.anyRequest().denyAll())
			.oauth2ResourceServer(oauth2 -> oauth2
					.jwt()
					.jwtAuthenticationConverter(keycloak()));
	}
}