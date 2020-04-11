/*
 * Copyright (c) 2015 by east.
 * All right reserved.
 */
package com.east.framework.web.client;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.mock.http.client.MockClientHttpResponse;

/**
 * Response 분석 필요로 인한 더미 Response.
 *
 * Updated on : 2015-10-05 Updated by : love.
 */
public class DummyClientHttpResponse extends MockClientHttpResponse {
	private final HttpHeaders headers;

	/**
	 * @param body
	 *            바디
	 * @param httpHeaders
	 *            해더
	 * @param statusCode
	 *            상태
	 */
	public DummyClientHttpResponse(byte[] body, HttpHeaders httpHeaders, HttpStatus statusCode) {
		super(body, statusCode);
		this.headers = httpHeaders;
	}

	@Override
	public HttpHeaders getHeaders() {
		return this.headers;
	}

}
