package com.east.framework.core.util.properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.servlet.support.RequestContextUtils;

public class PropertiesMsgUtil {

	private static MessageSourceAccessor msgAccessor = null;
	
	public void setMessageSourceAccessor(MessageSourceAccessor msgAccessor) {
		PropertiesMsgUtil.msgAccessor = msgAccessor;
	}

	public static String getMessage(String key, HttpServletRequest request) {
		return msgAccessor.getMessage(key, RequestContextUtils.getLocale(request));
	}

	public static String getMessage(String key, Object[] objs, HttpServletRequest request) {
		return msgAccessor.getMessage(key, objs, RequestContextUtils.getLocale(request));
	}
	
}
