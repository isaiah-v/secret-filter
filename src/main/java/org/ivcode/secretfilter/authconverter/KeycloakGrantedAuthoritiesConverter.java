package org.ivcode.secretfilter.authconverter;

import static org.ivcode.secretfilter.utils.CollectionSafety.*;
import static java.util.stream.Collectors.*;
import static org.apache.commons.lang3.StringUtils.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * Parses the user's authorities based on the Keycloak JWT authorization token
 * 
 * @author isaiah
 */
class KeycloakGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

	/**
	 * Converts the given JWT into a set of {@link GrantedAuthority}s.
	 * 
	 * @param jwt the input JWT object
	 * @return the set of granted authorities
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Collection<GrantedAuthority> convert(Jwt jwt) {
		var authorities = new HashSet<GrantedAuthority>();

		// Scopes
		authorities.addAll(convertScopes(jwt.getClaim("scope")));

		// Realm Roles
		var realmAccess = safe(jwt.getClaimAsMap("realm_access"));
		authorities.addAll(convertRoles((Collection<String>) realmAccess.get("roles")));

		// Client Roles
		var client = jwt.getClaimAsString("azp");
		if (client != null) {
			var resourceAccess = safe(jwt.getClaimAsMap("resource_access"));
			var clientAccess = safe((Map<String, Object>) resourceAccess.get(client));
			authorities.addAll(convertRoles((Collection<String>) clientAccess.get("roles")));
		}

		return authorities;
	}

	/**
	 * Converts a collection of roles into a collection of {@link GrantedAuthority}s 
	 * @param roles
	 * 		A collection of role names
	 * @return
	 * 		A collection of GrantedAuthority representing the given roles
	 */
	private Collection<GrantedAuthority> convertRoles(Collection<String> roles) {
		return safe(roles).stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r)).collect(toList());
	}

	/**
	 * Converts scopes into a collection of {@link GrantedAuthority}s
	 * @param scopes
	 * 		The list of scopes (a string delimited with a space character)
	 * @return
	 * 		A collection of GrantedAuthority representing the given scopes
	 */
	private Collection<GrantedAuthority> convertScopes(String scopes) {
		if (scopes == null) {
			return Collections.emptyList();
		}

		var scopeAuthorities = new ArrayList<GrantedAuthority>();

		String[] scopesArray = scopes.split(" ");
		for (String scope : scopesArray) {
			scope = trimToNull(scope);
			if (scope == null) {
				continue;
			}

			scopeAuthorities.add(new SimpleGrantedAuthority("SCOPE_" + scope));
		}

		return scopeAuthorities;
	}
}
