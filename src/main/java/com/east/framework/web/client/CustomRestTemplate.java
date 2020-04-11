/*
 * Copyright (c) 2015 by east.
 * All right reserved.
 */
package com.east.framework.web.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.util.IOUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.Assert;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 커스텀 Rest 템플릿.
 *
 * Updated on : 2015-10-05 Updated by : love.
 */
public class CustomRestTemplate extends RestTemplate implements ApplicationContextAware {

	private static final String HTTP_PATTERN = "(?i)(http|https):";
	private static final String USERINFO_PATTERN = "([^@\\[/?#]*)";
	private static final String HOST_IPV4_PATTERN = "[^\\[/?#:]*";
	private static final String HOST_IPV6_PATTERN = "\\[[\\p{XDigit}\\:\\.]*[%\\p{Alnum}]*\\]";
	private static final String HOST_PATTERN = "(" + HOST_IPV6_PATTERN + "|" + HOST_IPV4_PATTERN + ")";
	private static final String PORT_PATTERN = "(\\d*)";
	private static final String PATH_PATTERN = "([^?#]*)";
	private static final String LAST_PATTERN = "(.*)";

	private static final Pattern HTTP_URL_PATTERN = Pattern.compile("^" + HTTP_PATTERN + "(//(" + USERINFO_PATTERN + "@)?" + HOST_PATTERN + "(:" + PORT_PATTERN
			+ ")?" + ")?" + PATH_PATTERN + "(\\?" + LAST_PATTERN + ")?");

	/**
	 * 생성자.
	 */
	public CustomRestTemplate() {
		super();
	}

	/**
	 * Response Exception Catch 여부.
	 */
	private boolean catchEx;

	/**
	 * 기본 host 정보.
	 */
	private String baseHost;

	/**
	 * 생성자.
	 *
	 * @param requestFactory
	 *            ClientHttpRequestFactory
	 */
	public CustomRestTemplate(ClientHttpRequestFactory requestFactory) {
		super(requestFactory);
	}

	/**
	 * 에러 캐취용 Execute.
	 *
	 * @param url
	 *            URI
	 * @param method
	 *            HttpMethod
	 * @param requestCallback
	 *            RequestCallback
	 * @param responseExtractor
	 *            ResponseExtractor
	 * @param <T>
	 *            제너릭 타입
	 * @return T 제너릭 타입
	 * @throws RestClientException
	 *             RestClientException
	 */
	protected <T> T doExecuteCatchEx(URI url, HttpMethod method, RequestCallback requestCallback, ResponseExtractor<T> responseExtractor)
			throws RestClientException {

		Assert.notNull(url, "'url' must not be null");
		Assert.notNull(method, "'method' must not be null");
		ClientHttpResponse response = null;
		ByteArrayOutputStream baos = null;

		try {

			T retunT = null;

			ClientHttpRequest request = this.createRequest(url, method);
			if (requestCallback != null) {
				requestCallback.doWithRequest(request);
			}

			response = request.execute();

			baos = new ByteArrayOutputStream();

			IOUtils.copy(response.getBody(), baos);

			byte[] responseBodyAsByteArray = baos.toByteArray();

			if (!this.getErrorHandler().hasError(new DummyClientHttpResponse(responseBodyAsByteArray, response.getHeaders(), response.getStatusCode()))) {
				this.logResponseStatus(method, url, new DummyClientHttpResponse(responseBodyAsByteArray, response.getHeaders(), response.getStatusCode()));

			} else {
				this.handleResponseError(method, url, new DummyClientHttpResponse(responseBodyAsByteArray, response.getHeaders(), response.getStatusCode()));
			}
			if (responseExtractor != null) {
				retunT = responseExtractor.extractData(new DummyClientHttpResponse(responseBodyAsByteArray, response.getHeaders(), response.getStatusCode()));
			} else {
				retunT = null;
			}

			return retunT;

		} catch (IOException ex) {
			throw new ResourceAccessException("I/O error on " + method.name() + " request for \"" + url + "\":" + ex.getMessage(), ex);
		} finally {

			org.apache.commons.io.IOUtils.closeQuietly(baos);

			if (response != null) {
				response.close();
			}
		}
	}

	/**
	 * 에러 Throw용 Execute.
	 *
	 * @param url
	 *            URI
	 * @param method
	 *            HttpMethod
	 * @param requestCallback
	 *            RequestCallback
	 * @param responseExtractor
	 *            ResponseExtractor
	 * @param <T>
	 *            제너릭 타입
	 * @return T 제너릭 타입
	 * @throws RestClientException
	 *             RestClientException
	 */
	protected <T> T doExecuteThrowEx(URI url, HttpMethod method, RequestCallback requestCallback, ResponseExtractor<T> responseExtractor)
			throws RestClientException {
		Assert.notNull(url, "'url' must not be null");
		Assert.notNull(method, "'method' must not be null");
		ClientHttpResponse response = null;

		try {

			T retunT = null;

			ClientHttpRequest request = this.createRequest(url, method);
			if (requestCallback != null) {
				requestCallback.doWithRequest(request);
			}

			response = request.execute();

			if (!getErrorHandler().hasError(response)) {
				logResponseStatus(method, url, response);
			} else {
				handleResponseError(method, url, response);
			}
			if (responseExtractor != null) {
				retunT = responseExtractor.extractData(response);
			} else {
				retunT = null;
			}

			return retunT;

		} catch (IOException ex) {
			throw new ResourceAccessException("I/O error on " + method.name() + " request for \"" + url + "\":" + ex.getMessage(), ex);
		} finally {
			if (response != null) {
				response.close();
			}
		}
	}

	@Override
	protected <T> T doExecute(URI url, HttpMethod method, RequestCallback requestCallback, ResponseExtractor<T> responseExtractor) throws RestClientException {
		URI newUrl;
		String httpurl = url.toString();
		Matcher matcher = HTTP_URL_PATTERN.matcher(httpurl);
		if (!matcher.matches()) {
			newUrl = UriComponentsBuilder.fromHttpUrl(this.baseHost + httpurl).build().toUri();
		} else {
			newUrl = url;
		}

		if (catchEx) {
			return this.doExecuteCatchEx(newUrl, method, requestCallback, responseExtractor);
		} else {
			return this.doExecuteThrowEx(newUrl, method, requestCallback, responseExtractor);
		}
	}

	/**
	 * logResponseStatus.
	 *
	 * @param method
	 *            method
	 * @param url
	 *            url
	 * @param response
	 *            response
	 */
	private void logResponseStatus(HttpMethod method, URI url, ClientHttpResponse response) {
		if (this.logger.isDebugEnabled()) {
			try {
				this.logger
						.debug(method.name() + " request for \"" + url + "\" resulted in " + response.getStatusCode() + " (" + response.getStatusText() + ")");
			} catch (IOException e) {
				this.logger.error("logResponseStatus");
				// ignore
			}
		}
	}

	/**
	 * handleResponseError.
	 *
	 * @param method
	 *            method
	 * @param url
	 *            url
	 * @param response
	 *            response
	 *
	 * @throws IOException
	 *             IOException
	 */
	private void handleResponseError(HttpMethod method, URI url, ClientHttpResponse response) throws IOException {
		if (this.logger.isWarnEnabled()) {
			try {
				this.logger.warn(method.name() + " request for \"" + url + "\" resulted in " + response.getStatusCode() + " (" + response.getStatusText()
						+ "); invoking error handler");
			} catch (IOException e) {
				this.logger.error("handleResponseError");
				// ignore
			}
		}
		this.getErrorHandler().handleError(response);
	}

	/**
	 * @return catchEx
	 */
	public boolean isCatchEx() {
		return catchEx;
	}

	/**
	 * @param catchEx
	 *            catchEx
	 */
	public void setCatchEx(boolean catchEx) {
		this.catchEx = catchEx;
	}

	/**
	 * 호스트 정보 셋팅.
	 * 
	 * @param baseHost
	 *            the baseHost to set
	 */
	public void setBaseHost(String baseHost) {
		this.baseHost = baseHost;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	}

}
