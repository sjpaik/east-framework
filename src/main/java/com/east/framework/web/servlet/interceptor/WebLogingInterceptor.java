package com.east.framework.web.servlet.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 요청값과 응답값의 로깅을 위한 인터셉터.
 *
 * Updated on : 2015-11-11 Updated by : love.
 */
public class WebLogingInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession hses = request.getSession();
        Class<?> clazz = handler.getClass();
        Logger logger = LoggerFactory.getLogger(clazz);

        if (logger.isInfoEnabled()) {
            String reqUrl;
            String refererUrl;
            String reqHeaderDump;
            String reqParamDump;
            StringBuffer sb;
            String qs;
            Enumeration<?> en;
            boolean flag;

            sb = new StringBuffer();
            sb.append(request.getRequestURL());
            qs = request.getQueryString();
            if (qs != null) {
                sb.append("?").append(qs);
            }
            reqUrl = sb.toString();
            refererUrl = request.getHeader("referer") == null ? "" : request.getHeader("referer");
            sb.setLength(0);
            en = request.getHeaderNames();
            flag = false;
            while (en.hasMoreElements()) {
                String hnm;

                hnm = (String) en.nextElement();
                if (flag) {
                    sb.append("\n");
                } else {
                    flag = true;
                }
                sb.append(hnm).append(" : ").append(request.getHeader(hnm));
            }
            reqHeaderDump = sb.toString();
            sb.setLength(0);
            en = request.getParameterNames();
            flag = false;
            while (en.hasMoreElements()) {
                String pnm;
                String[] pvs;

                if (flag) {
                    sb.append("\n");
                } else {
                    flag = true;
                }
                pnm = (String) en.nextElement();
                sb.append(pnm).append(" : ");

                pvs = request.getParameterValues(pnm);
                for (int i = 0; i < pvs.length; i++) {
                    if (i > 0) {
                        sb.append(", ");
                    }
                    sb.append("\"").append(pvs[i]).append("\"");
                }
            }
            reqParamDump = sb.toString();
            logger.info(
                    " \n" + "################################################# [    REQUEST INFO     ] ################################################# \n"
                            + "[     SESSION ID    ]\n" + "SESSION ID  : {} \n" + "[    REQUEST, METHOD, REFFER URL    ]\n" + "REQUEST URL : {} \n"
                            + "METHOD : {} \n" + "REFFER URL : {} \n" + "[   REQUEST HEADER  ]\n" + "{} \n" + "[ REQUEST PARAMETER ]\n" + "{} \n"
                            + "################################################# [    REQUEST INFO       ] ################################################# \n",
                    hses.getId(), reqUrl, request.getMethod(), refererUrl, reqHeaderDump, reqParamDump);
        }

        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

}