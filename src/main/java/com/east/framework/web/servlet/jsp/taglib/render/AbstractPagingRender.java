package com.east.framework.web.servlet.jsp.taglib.render;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

/**
 * <pre>
 * 공통 페이징 abstract interface .
 * </pre>
 * 
 * Updated on : 2016-06-14 Updated by : love
 */
public abstract interface AbstractPagingRender {

    /**
     * 인터페이스.
     * 
     * @param req req
     * @param jwt jwt
     * @param totalNo totalNo
     * @param currentNo currentNo
     * @param pagingNo pagingNo
     * @param jsFunctionName jsFunctionName
     * @throws IOException IOException
     */
    public void doWritePageIndex(HttpServletRequest req, JspWriter jwt, int totalNo, int currentNo, int pagingNo, String jsFunctionName) throws IOException;

}
