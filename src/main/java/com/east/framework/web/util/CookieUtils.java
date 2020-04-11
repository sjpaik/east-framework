package com.east.framework.web.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 쿠키 유틸.
 *
 * Updated on : 2017-03-20 Updated by : love.
 */
public class CookieUtils {
    /**
     * Cookie 생성, 기본 생성시간 30분.
     *
     * @param response
     *            HttpServletResponse
     * @param name
     *            이름
     * @param value
     *            값
     * @param path
     *            경로
     * @return Cookie
     */
    public static Cookie setCookie(HttpServletResponse response, String name, String value, String path) {
        return addCookie(response, name, value, path, 60 * 30);
    }

    /**
     * Cookie 생성.
     * 
     * @param response
     *            HttpServletResponse
     * @param name
     *            이름
     * @param value
     *            값
     * @param path
     *            경로
     * @param maxAge
     *            쿠키유지시간
     * @return Cookie
     */
    public static Cookie addCookie(HttpServletResponse response, String name, String value, String path, int maxAge) {
        Cookie cookie = null;
        try {
            cookie = new Cookie(name, URLEncoder.encode(value, "UTF-8"));
            cookie.setMaxAge(maxAge);
            if (null != path) {
                cookie.setPath(path);
            }
            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return cookie;
    }

    /**
     * 쿠키생성.
     * 
     * @param response
     *            HttpServletResponse
     * @param values
     *            쿠키생성 내용
     * @param path
     *            경로
     * @param maxAge
     *            유지시간
     * @return ArrayList<Cookie>
     */
    public static ArrayList<Cookie> addCookies(HttpServletResponse response, Map<String, String> values, String path, int maxAge) {
        Set<Map.Entry<String, String>> entries = values.entrySet();
        ArrayList<Cookie> cookies = new ArrayList<Cookie>();
        try {
            for (Map.Entry<String, String> entry : entries) {
                Cookie cookie = new Cookie(entry.getKey(), URLEncoder.encode(entry.getValue(), "UTF-8"));
                cookie.setMaxAge(maxAge);
                if (null != path) {
                    cookie.setPath(path);
                }
                response.addCookie(cookie);
                cookies.add(cookie);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return cookies;
    }

    /**
     * 쿠키 획득.
     * 
     * @param request
     *            HttpServletRequest
     * @param name
     *            이름
     * @return 쿠키값
     */
    public static String getCookie(HttpServletRequest request, String name) {
        return getCookie(request, null, name, false);
    }

    /**
     * 쿠키획득.
     * 
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @param name
     *            이름
     * @return 쿠키값
     */
    public static String getCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        return getCookie(request, response, name, true);
    }

    /**
     * 쿠키 획득(획득한 쿠키 삭제).
     * 
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @param name
     *            이름
     * @param isRemoved
     *            삭제여부
     * @return 쿠키값
     */
    public static String getCookie(HttpServletRequest request, HttpServletResponse response, String name, boolean isRemoved) {
        String value = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    try {
                        value = URLDecoder.decode(cookie.getValue(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    if (isRemoved) {
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                    }
                    return value;
                }
            }
        }
        return value;
    }
}
