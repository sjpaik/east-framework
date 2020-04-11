/*
 * Copyright (c) 2015 by east.
 * All right reserved.
 */
package com.east.framework.web.client;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

/**
 * Response Error Handler
 *
 * Updated on : 2015-10-05 Updated by : love.
 */
public class CustomResponseErrorHandler extends DefaultResponseErrorHandler {

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		if (StringUtils.equalsIgnoreCase(String.valueOf(response.getHeaders().get("x-result-code")), "error")) {
			return false;
		} else {
			return super.hasError(response);
		}
	}

}
