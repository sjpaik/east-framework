package com.east.framework.security.web.authentication.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.east.framework.security.core.token.CustomTokenManager;
import com.east.framework.security.core.token.model.CustomTokenInfo;

/**
 * 권한 관리 서비스 구현체.
 *
 * Updated on : 2015-10-20 Updated by : love.
 */
public class CustomAuthenticationServiceImpl implements CustomAuthenticationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationServiceImpl.class);

	private final AuthenticationManager authenticationManager;
	private final CustomTokenManager customTokenManager;

	/**
	 * 생성자.
	 * 
	 * @param authenticationManager
	 *            AuthenticationManager
	 * @param customTokenManager
	 *            CustomTokenManager
	 */
	public CustomAuthenticationServiceImpl(AuthenticationManager authenticationManager, CustomTokenManager customTokenManager) {
		this.authenticationManager = authenticationManager;
		this.customTokenManager = customTokenManager;
	}

	@Override
	public CustomTokenInfo authenticate(String login, String password) {
		Authentication authentication = new UsernamePasswordAuthenticationToken(login, password);
		try {
			authentication = authenticationManager.authenticate(authentication);
			// Here principal=UserDetails (UserContext in our case), credentials=null (security reasons)
			SecurityContextHolder.getContext().setAuthentication(authentication);

			if (authentication.getPrincipal() != null) {
				UserDetails userContext = (UserDetails) authentication.getPrincipal();
				CustomTokenInfo newCustomToken = customTokenManager.createNewToken(userContext);
				if (newCustomToken == null) {
					return null;
				}
				return newCustomToken;
			}
		} catch (AuthenticationException e) {
			LOGGER.debug(" *** CustomAuthenticationServiceImpl.authenticate - FAILED: {}", e.toString());
		}
		return null;
	}

	@Override
	public boolean checkToken(String token) {
		UserDetails userDetails = customTokenManager.getUserDetails(token);
		if (userDetails == null) {
			return false;
		}

		UsernamePasswordAuthenticationToken securityToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(securityToken);

		return true;
	}

	@Override
	public AbstractAuthenticationToken getAuthenticationToken(String token) {
		UserDetails userDetails = customTokenManager.getUserDetails(token);
		try {
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			return authenticationToken;
		} catch (AuthenticationException e) {
			LOGGER.debug(" *** CustomAuthenticationServiceImpl.getAuthenticationToken - FAILED: {}", e.toString());
		} catch (Exception e) {
			LOGGER.debug(" *** CustomAuthenticationServiceImpl.getAuthenticationToken - Exception: {}", e.toString());
		}
		return null;
	}

	@Override
	public void logout(String token) {
		UserDetails logoutUser = customTokenManager.removeToken(token);
		LOGGER.debug(" *** CustomAuthenticationServiceImpl.logout: {}", logoutUser.toString());
		SecurityContextHolder.clearContext();
	}

	@Override
	public UserDetails currentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		return (UserDetails) authentication.getPrincipal();
	}

}
