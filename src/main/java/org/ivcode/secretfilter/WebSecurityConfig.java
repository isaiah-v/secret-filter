package org.ivcode.secretfilter;

import static org.ivcode.secretfilter.authconverter.AuthenticationConverters.*;
import static org.springframework.http.HttpMethod.*;

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
	
	public static final String ROLE_ADMIN = "ROLE_properties_admin";
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests(authz -> authz
					.antMatchers("/", "/info", "/swagger-ui/*", "/v3/api-docs", "/v3/api-docs/*").permitAll()
					.antMatchers(OPTIONS).permitAll()
					.antMatchers(GET).authenticated()
					.antMatchers(DELETE).hasAuthority(ROLE_ADMIN)
					.antMatchers(PATCH).hasAuthority(ROLE_ADMIN)
					.antMatchers(POST).hasAuthority(ROLE_ADMIN)
					.antMatchers(PUT).hasAnyAuthority(ROLE_ADMIN)
					.anyRequest().denyAll())
			.oauth2ResourceServer(oauth2 -> oauth2
					.jwt()
					.jwtAuthenticationConverter(keycloak()));
	}
}