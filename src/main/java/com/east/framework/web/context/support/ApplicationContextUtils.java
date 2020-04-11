package com.east.framework.web.context.support;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * ApplicationContextAware Util 클래스.
 *
 * Updated on : 2016-07-01 Updated by : love.
 */
public class ApplicationContextUtils implements ApplicationContextAware {

    private static ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext appContext) throws BeansException {
        ctx = appContext;
    }

    /**
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return ctx;
    }

    /**
     * @param beanName 빈 이름
     * @return Object
     */
    public static Object getBean(String beanName) {
        if (ctx != null) {
            return ctx.getBean(beanName);
        } else {
            return null;
        }
    }

    /**
     * @param requiredType 빈 클래스
     * @param <T> 제너릭 타입.
     * @return T 제너릭 타입
     */
    public static <T> T getBean(Class<T> requiredType) {
        if (ctx != null) {
            return ctx.getBean(requiredType);
        } else {
            return null;
        }
    }

}
