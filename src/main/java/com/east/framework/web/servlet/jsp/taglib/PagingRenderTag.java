package com.east.framework.web.servlet.jsp.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.servlet.support.RequestContextUtils;

import com.east.framework.core.persistence.dao.page.PageInfo;
import com.east.framework.web.servlet.jsp.taglib.render.AbstractPagingRender;
import com.east.framework.web.servlet.jsp.taglib.render.DefinedPagingRender;

/**
 * <pre>
 * 공통 페이징 render tag .
 * <ul>
 * 		<li> item (필수): pagingList</li>
 * 		<li> template : paging template</li>
 * 		<li> funcName : paging call function</li>
 * </ul>
 * </pre>
 * 
 * Updated on : 2016-06-14 Updated by : love
 */
public class PagingRenderTag extends TagSupport {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("rawtypes")
    private PageInfo item;
    private String template;
    private String funcName;
    
    @Override
    public void release() {
        super.release();
        this.item = null;
        this.template = null;
        this.funcName = null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
     */
    @SuppressWarnings("rawtypes")
    @Override
    public int doStartTag() throws JspException {

        if (this.item == null)
            this.item = new PageInfo();
        if (this.template == null)
            this.template = "";
        if (this.id == null)
            this.id = "";
        if (this.funcName == null)
            this.funcName = "";

        return SKIP_BODY;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
     */
    @Override
    public int doEndTag() throws JspException {
        try {
            JspWriter jwt;
            HttpServletRequest req;
            AbstractPagingRender pr;
            int pagingTotalNo;
            int pagingCurrentNo;
            int pagingPerPageNo;

            jwt = this.pageContext.getOut();
            req = (HttpServletRequest) this.pageContext.getRequest();

            if ("".equals(this.template)) {
                pr = new DefinedPagingRender();
            } else {
                pr = ((DefinedPagingRender) RequestContextUtils.findWebApplicationContext(req).getBean(this.template));
            }

            if (this.item != null) {
                pagingTotalNo = this.item.getTotalCount();
                pagingCurrentNo = this.item.getPageIndex();
                pagingPerPageNo = this.item.getPageSize();
            } else {
                pagingTotalNo = 0;
                pagingCurrentNo = 1;
                pagingPerPageNo = 10;
            }

            pr.doWritePageIndex(req, jwt, pagingTotalNo, pagingCurrentNo, pagingPerPageNo, this.funcName);

        } catch (Exception e) {
            throw new JspTagException("I/O 예외 " + e.getMessage());
        } finally {
            this.release();
        }

        return EVAL_PAGE;
    }

    /**
     * @return item
     */
    @SuppressWarnings("rawtypes")
    public PageInfo getItem() {
        return this.item;
    }

    /**
     * @param item item
     */
    @SuppressWarnings("rawtypes")
    public void setItem(PageInfo item) {
        this.item = item;
    }

    /**
     * @return template
     */
    public String getTemplate() {
        return this.template;
    }

    /**
     * @param template template
     */
    public void setTemplate(String template) {
        this.template = template;
    }

    /**
     * @return funcName
     */
    public String getFuncName() {
        return this.funcName;
    }

    /**
     * @param funcName funcName
     */
    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

}
