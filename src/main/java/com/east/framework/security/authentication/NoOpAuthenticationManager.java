package com.east.framework.security.authentication;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * AuthenticationManager 단순 구현체.
 *
 * Updated on : 2015-10-16 Updated by : love.
 */
public class NoOpAuthenticationManager implements AuthenticationManager {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		return authentication;
	}

}
