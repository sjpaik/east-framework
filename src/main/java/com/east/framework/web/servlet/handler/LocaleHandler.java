/*
 * Copyright (c) 2015 by east.
 * All right reserved.
 */
package com.east.framework.web.servlet.handler;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.LocaleResolver;

/**
 * Locale Handler.
 *
 * Updated on : 2015-09-25 Updated by : love.
 */
public class LocaleHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(LocaleHandler.class);

	private final HttpServletRequest request;
	private final HttpServletResponse response;
	private final LocaleResolver localeResolver;

	/**
	 * Locale Handler 생성자.
	 *
	 * @param request
	 *            요청
	 * @param response
	 *            응답
	 * @param localeResolver
	 *            Locale Resolver
	 */
	public LocaleHandler(HttpServletRequest request, HttpServletResponse response, LocaleResolver localeResolver) {
		this.request = request;
		this.response = response;
		this.localeResolver = localeResolver;
	}

	/**
	 * Locale Setter.
	 *
	 * @param locale
	 *            locale
	 */
	public void setLocale(Locale locale) {
		if (localeResolver != null) {
			localeResolver.setLocale(request, response, locale);
		} else {
			LOGGER.debug("LocaleResolver not found.");
		}
	}
}
