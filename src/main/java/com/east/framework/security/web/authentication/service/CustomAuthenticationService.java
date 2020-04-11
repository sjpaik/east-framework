package com.east.framework.security.web.authentication.service;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import com.east.framework.security.core.token.model.CustomTokenInfo;

/**
 * 권한 관리 서비스 인터페이스.
 *
 * Updated on : 2015-10-20 Updated by : love.
 */
public interface CustomAuthenticationService {

	/**
	 * 로그인 및 기타 증명.
	 * 
	 * @param login
	 *            uniqueId
	 * @param password
	 *            비밀번호
	 * @return CustomTokenInfo
	 */
	CustomTokenInfo authenticate(String login, String password);

	/**
	 * 토큰 체크.
	 * 
	 * @param token
	 *            토큰
	 * @return boolean
	 */
	boolean checkToken(String token);

	/**
	 * AbstractAuthenticationToken 획득.
	 * 
	 * @param token
	 *            토큰
	 * @return AbstractAuthenticationToken
	 */
	AbstractAuthenticationToken getAuthenticationToken(String token);

	/**
	 * 로그아웃.
	 * 
	 * @param token
	 *            토큰
	 */
	void logout(String token);

	/**
	 * 유저정보 획득.
	 * 
	 * @return UserDetails
	 */
	UserDetails currentUser();
}
