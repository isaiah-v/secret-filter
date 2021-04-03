package org.ivcode.secretfilter.utils;

import static org.ivcode.secretfilter.utils.CollectionSafety.*;

import org.ivcode.secretfilter.WebSecurityConfig;

import static java.util.stream.Collectors.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
		
	/**
	 * Checks if the current user has limited access to the data.
	 * 
	 * @return returns {@code true} if the user has only limited access.
	 *         {@code false} is returned if the user has full access
	 */
	public static boolean isLimitedAccess() {
		var auth = SecurityContextHolder.getContext().getAuthentication();
		var authorities = safe(auth.getAuthorities())
				.stream()
				.map(GrantedAuthority::getAuthority)
				.collect(toSet());
		
		return !authorities.contains(WebSecurityConfig.ROLE_ADMIN);
	}
}
