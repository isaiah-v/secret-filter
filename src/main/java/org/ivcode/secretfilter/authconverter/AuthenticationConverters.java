package org.ivcode.secretfilter.authconverter;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.core.convert.converter.Converter;

public class AuthenticationConverters {
	
	public static Converter<Jwt, AbstractAuthenticationToken> keycloak() {
		JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
		converter.setJwtGrantedAuthoritiesConverter(new KeycloakGrantedAuthoritiesConverter());
		
		return converter;
	}
}
