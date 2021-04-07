package org.ivcode.secretfilter.utils;

import static org.ivcode.secretfilter.utils.CollectionSafety.*;

import static java.util.stream.Collectors.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityHelper {
	
	@Value("${authority.admin}")
	private String adminAuthority;
	
	/**
	 * Checks if the current user has limited access to the data.
	 * 
	 * @return returns {@code true} if the user has only limited access.
	 *         {@code false} is returned if the user has full access
	 */
	public boolean isLimitedAccess() {
		var auth = SecurityContextHolder.getContext().getAuthentication();
		var authorities = safe(auth.getAuthorities())
				.stream()
				.map(GrantedAuthority::getAuthority)
				.collect(toSet());
		
		return !authorities.contains(adminAuthority);
	}
}
