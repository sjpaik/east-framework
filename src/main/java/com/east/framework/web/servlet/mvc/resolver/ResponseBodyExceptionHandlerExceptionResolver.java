/*
 * Copyright (c) 2015 by east.
 * All right reserved.
 */
package com.east.framework.web.servlet.mvc.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

/**
 * ResponseBody 어노테이션이 있는 경우 Excepion 을 새로 만듬.
 *
 * Updated on : 2015-09-25 Updated by : love.
 */
public class ResponseBodyExceptionHandlerExceptionResolver extends ExceptionHandlerExceptionResolver {

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.web.servlet.mvc.method.annotation.
     * ExceptionHandlerExceptionResolver#
     * doResolveHandlerMethodException(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse,
     * org.springframework.web.method.HandlerMethod, java.lang.Exception)
     */
    @Override
    protected ModelAndView doResolveHandlerMethodException(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod, Exception exception) {
        ModelAndView returnValue = null;
        if (handlerMethod != null) {
            if (handlerMethod.getMethodAnnotation(ResponseBody.class) != null) {
                returnValue = super.doResolveHandlerMethodException(request, response, handlerMethod, exception);
            }
        }

        if (returnValue == null) {
            return new ModelAndView("/cmm/system/exception/500");
        }

        return returnValue;
    }

}
