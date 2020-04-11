package com.east.framework.web.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * HttpServletRequest 상의 Session 정보의 수정, 저장, 삭제를 도와주는 유틸.
 *
 * Updated on : 2015-11-23 Updated by : love.
 */
public class SessionHelper {

	/**
	 * 세션 존재 여부 체크.
	 *
	 * @param sessionName
	 *            세션이름
	 * @return boolean
	 */
	public static boolean existSession(String sessionName) {
		if (RequestContextHolder.getRequestAttributes().getAttribute(sessionName, RequestAttributes.SCOPE_SESSION) == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 세션 존재 여부 체크.
	 *
	 * @param request
	 *            HttpServletRequest
	 * @param sessionName
	 *            세션 이름
	 * @return boolean
	 */
	public static boolean existSession(HttpServletRequest request, String sessionName) {
		if (request.getSession(false) == null || request.getSession(true).getAttribute(sessionName) == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 세션 정보 요청.
	 *
	 * @param sessionName
	 *            세션 이름
	 * @param clazz
	 *            요청 클래스
	 * @param <T>
	 *            제너릭 클래스
	 * @return T
	 */
	public static <T> T getSession(String sessionName, Class<T> clazz) {
		return clazz.cast(RequestContextHolder.getRequestAttributes().getAttribute(sessionName, RequestAttributes.SCOPE_SESSION));
	}

	/**
	 * 세션 정보 요청.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param sessionName
	 *            세션 이름
	 * @param clazz
	 *            요청 클래스
	 * @param <T>
	 *            제너릭 클래스
	 * @return T
	 */
	public static <T> T getSession(HttpServletRequest request, String sessionName, Class<T> clazz) {
		return clazz.cast(request.getSession(true).getAttribute(sessionName));
	}

	/**
	 * 세션 저장.
	 *
	 * @param sessionName
	 *            세션 이름
	 * @param session
	 *            Object
	 */
	public static void setSession(String sessionName, Object session) {
		RequestContextHolder.getRequestAttributes().setAttribute(sessionName, session, RequestAttributes.SCOPE_SESSION);
	}

	/**
	 * 세션 저장.
	 *
	 * @param request
	 *            HttpServletRequest
	 * @param sessionName
	 *            세션 이름
	 * @param session
	 *            Object
	 */
	public static void setSession(HttpServletRequest request, String sessionName, Object session) {
		request.getSession(true).setAttribute(sessionName, session);
	}

	/**
	 * 세션 삭제.
	 *
	 * @param sessionName
	 *            세션 이름
	 */
	public static void removeSession(String sessionName) {
		RequestContextHolder.getRequestAttributes().removeAttribute(sessionName, RequestAttributes.SCOPE_SESSION);
	}

	/**
	 * 세션 삭제.
	 *
	 * @param sessionName
	 *            세션 이름
	 * @param request
	 *            HttpServletRequest
	 */
	public static void removeSession(String sessionName, HttpServletRequest request) {
		HttpSession httpSession = request.getSession(false);
		if (httpSession != null) {
			httpSession.removeAttribute(sessionName);
		}
	}

}
