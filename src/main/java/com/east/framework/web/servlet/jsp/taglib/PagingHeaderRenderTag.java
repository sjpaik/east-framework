package com.east.framework.web.servlet.jsp.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.east.framework.core.persistence.dao.page.PageInfo;

/**
 * <pre>
 * 공통 페이징 해더  render tag.
 * <ul>
 * 		<li> item (필수): pagingList</li>
 * 		<li> mTemplate : paging header message template</li>
 * 		<li> funcName : paging call function</li>
 * </ul>
 * </pre>
 * 
 * Updated on : 2016-06-14 Updated by : love
 */
public class PagingHeaderRenderTag extends TagSupport {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("rawtypes")
    private PageInfo item;
    private String mTemplate;

    @Override
    public void release() {
        super.release();
        this.item = null;
        this.mTemplate = null;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public int doStartTag() throws JspException {

        if (this.item == null)
            this.item = new PageInfo();
        if (this.mTemplate == null)
            this.mTemplate = "";

        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        try {

            JspWriter jwt;
            HttpServletRequest req;

            int pagingTotalNo;
            int pagingCurrentNo;
            int pagingPerPageNo;
            int pagingMaxPageNo;

            String vstr;
            String msgId;

            jwt = this.pageContext.getOut();
            req = (HttpServletRequest) this.pageContext.getRequest();

            if (this.item != null) {
                pagingTotalNo = this.item.getTotalCount();
                pagingCurrentNo = this.item.getPageIndex();
                pagingPerPageNo = this.item.getPageSize();
            } else {
                pagingTotalNo = 0;
                pagingCurrentNo = 1;
                pagingPerPageNo = 10;
            }

            if (pagingTotalNo == 0) {
                pagingMaxPageNo = 1;
            } else {
                pagingMaxPageNo = pagingTotalNo / pagingPerPageNo;

                if (pagingTotalNo % pagingPerPageNo > 0)
                    ++pagingMaxPageNo;
            }

            if (this.mTemplate == null || "".equals(this.mTemplate)) {
                msgId = "common.paging.header";
            } else {
                msgId = this.mTemplate;
            }

            vstr = ((MessageSourceAccessor) RequestContextUtils.findWebApplicationContext(req).getBean("messageSourceAccessor")).getMessage(msgId,
                    new Object[] { pagingTotalNo, pagingCurrentNo, pagingMaxPageNo }, RequestContextUtils.getLocale(req));

            jwt.print(vstr);

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
     * @return mTemplate
     */
    public String getmTemplate() {
        return this.mTemplate;
    }

    /**
     * @param mTemplate mTemplate
     */
    public void setmTemplate(String mTemplate) {
        this.mTemplate = mTemplate;
    }

}
