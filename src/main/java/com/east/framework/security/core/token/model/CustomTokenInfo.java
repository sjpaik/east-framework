package com.east.framework.security.core.token.model;

import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * 토큰 모델.
 *
 * Updated on : 2015-10-16 Updated by : love.
 */
public final class CustomTokenInfo {

    private final long created = System.currentTimeMillis();
    private final String token;
    private final UserDetails userDetails;

    /**
     * 생성자.
     * 
     * @param token 토큰
     * @param userDetails 사용자정보
     */
    public CustomTokenInfo(String token, UserDetails userDetails) {
        this.token = token;
        this.userDetails = userDetails;
    }

    /**
     * 토큰 획득.
     * 
     * @return token
     */
    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "TokenInfo{" + "token:'" + token + '\'' + ", userDetails:" + userDetails + ", created:" + new Date(created) + '}';
    }
}
