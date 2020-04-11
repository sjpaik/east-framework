package com.east.framework.web.servlet.context;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;

/**
 * ServletContext 에 특정 attribute를 셋팅 하기 위한 클래스.
 *
 * Updated on : 2015-09-25 Updated by : love.
 */
public class ServletContextBeanInjector implements ServletContextAware, InitializingBean {

	private final String name;

	private final Object bean;

	/**
	 * 생성자.
	 *
	 * @param name
	 *            ServletContext 내 사용될 이름.
	 * @param bean
	 *            ServletContext 사용할 클래스
	 */
	public ServletContextBeanInjector(String name, Object bean) {
		this.name = name;
		this.bean = bean;
	}

	private ServletContext servletContext;

	@Override
	public void setServletContext(ServletContext sc) {
		this.servletContext = sc;
	}

	@Override
	public void afterPropertiesSet() {
		Map<String, Object> conMap;
		Field[] flds;

		flds = this.bean.getClass().getFields();
		if (flds == null) {
			return;
		}
		conMap = new HashMap<String, Object>();

		for (Field fld : flds) {
			try {
				conMap.put(fld.getName(), fld.get(bean));
			} catch (Exception ignore) {
				ignore.printStackTrace();
			}
		}
		servletContext.setAttribute(name, conMap);
	}
}
