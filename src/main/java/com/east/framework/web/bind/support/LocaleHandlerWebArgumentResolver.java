/*
 * Copyright (c) 2015 by east.
 * All right reserved.
 */
package com.east.framework.web.bind.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.LocaleResolver;

import com.east.framework.web.servlet.handler.LocaleHandler;

/**
 * LocaleHandlerWebArgumentResolver.
 *
 * Updated on : 2015-09-25 Updated by : love.
 */
public class LocaleHandlerWebArgumentResolver implements WebArgumentResolver {
	private final LocaleResolver localeResolver;

	/**
	 * 생성자.
	 *
	 * @param localeResolver
	 *            the locale resolver
	 */
	public LocaleHandlerWebArgumentResolver(LocaleResolver localeResolver) {
		this.localeResolver = localeResolver;
	}

	@Override
	public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) throws Exception {
		if (LocaleHandler.class.isAssignableFrom(methodParameter.getParameterType())) {
			return new LocaleHandler((HttpServletRequest) webRequest.getNativeRequest(), (HttpServletResponse) webRequest.getNativeResponse(), localeResolver);
		}

		return UNRESOLVED;
	}

}
