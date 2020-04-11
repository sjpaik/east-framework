package com.east.framework.web.servlet.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.MDC;

import com.east.framework.core.util.idgen.UUIDGeneratorFactory;
import com.east.framework.web.context.bean.RuntimeContext;
import com.east.framework.web.context.bean.RuntimeContextHolder;

/**
 * Request 조작을 위해 F/W 에서 제공 하는 abstract class filter
 *
 * Updated on : 2015-10-05 Updated by : love.
 */
public abstract class AbstractRequestWrappingFilter implements Filter {

    protected final static String UNIQUE_ID_NAME = "uuid";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            RuntimeContext runtimeContext = new RuntimeContext();
            runtimeContext.setUuid(UUIDGeneratorFactory.getFomatIns().getId());
            runtimeContext.setAccessIp(request.getRemoteAddr());
            RuntimeContextHolder.setRuntimeContext(runtimeContext);

            this.putMDC(request);

            this.preChain(request, response);

            chain.doFilter(request, response);

            this.postChain(request, response);

        } catch (IOException ex) {
            throw ex;
        } catch (ServletException ex) {
            throw ex;
        } finally {
            RuntimeContextHolder.removeRuntimeContext();
            this.clearMDC(request);
        }
    }

    @Override
    public void destroy() {
    }

    /**
     * chain.doFilter(request, response); 전 실행 될 메소드.
     *
     * @param request ServletRequest
     * @param response ServletResponse
     */
    public abstract void preChain(ServletRequest request, ServletResponse response);

    /**
     * chain.doFilter(request, response); 후 실행 될 메소드.
     *
     * @param request ServletRequest
     * @param response ServletResponse
     */
    public abstract void postChain(ServletRequest request, ServletResponse response);

    /**
     * MDC 셋팅.
     *
     * @param request ServletRequest
     */
    @SuppressWarnings("static-access")
    protected void putMDC(ServletRequest request) {
        MDC.put(this.UNIQUE_ID_NAME, UUIDGeneratorFactory.getFomatIns().getId());
    }

    /**
     * MDC clear.
     *
     * @param request ServletRequest
     */
    protected void clearMDC(ServletRequest request) {
        MDC.clear();
    }

}
