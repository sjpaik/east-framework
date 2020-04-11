package com.east.framework.security.core.token;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

import com.east.framework.security.core.token.model.CustomTokenInfo;

/**
 * Token 매니저.
 *
 * Updated on : 2015-10-16 Updated by : love.
 */
public interface CustomTokenManager {

	/**
	 * CustomTokenInfo 생성.
	 * 
	 * @param userDetails
	 *            UserDetails
	 * @return CustomTokenInfo
	 */
	CustomTokenInfo createNewToken(UserDetails userDetails);

	/**
	 * 사용자 정보 삭제.
	 * 
	 * @param userDetails
	 *            UserDetails
	 */
	void removeUserDetails(UserDetails userDetails);

	/**
	 * 토큰 정보 삭제.
	 * 
	 * @param token
	 *            토큰
	 * @return UserDetails
	 */
	UserDetails removeToken(String token);

	/**
	 * 사용자 정보 획득.
	 * 
	 * @param token
	 *            토큰
	 * @return UserDetails
	 */
	UserDetails getUserDetails(String token);

	/**
	 * 모든 토큰 정보 획득.
	 * 
	 * @param userDetails
	 *            UserDetails
	 * @return Collection<CustomTokenInfo>
	 */
	Collection<CustomTokenInfo> getUserTokens(UserDetails userDetails);

	/**
	 * 인증 유저 정보 획득.
	 * 
	 * @return Map<String, UserDetails>
	 */
	Map<String, UserDetails> getValidUsers();
}
