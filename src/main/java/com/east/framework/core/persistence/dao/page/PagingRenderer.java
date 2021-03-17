package com.east.framework.core.persistence.dao.page;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.east.framework.core.util.properties.PropertiesMsgUtil;

public class PagingRenderer {
	
	private static Pattern ptnValueExpr = Pattern.compile("\\^\\{([a-zA-Z0-9.-_]+?)\\}");

	private StringBuffer jwt;
    protected String header;
    protected String footer;

    protected String topLink;
    protected String bottomLink;
    protected String nextLink;
    protected String previousLink;
    protected String incLink;
    protected String decLink;
    protected String currentPageLink;
    protected String otherPageLink;

    protected long scale;
    protected long total;

    protected String prefix;
    
    /**
     * 기본 생성자.
     */
    public PagingRenderer() {
        this.header = "<nav class='m-t-10'><ul class='pagination pagination-primary' style='justify-content: center'>";
        this.footer = "</ul></nav>";
        this.topLink = "";
        this.bottomLink = "";
        this.previousLink = "<li class='page-item'><a class='page-link' href='#' data-role='^{dataRole}' data-page='^{no}' aria-label='Previous'><i class='fa fa-angle-double-left' ></i></a></li>";
        this.nextLink = "<li class='page-item'><a class='page-link' href='#' data-role='^{dataRole}' data-page='^{no}' aria-label='Next'><i class='fa fa-angle-double-right' ></i></a></li>";
        this.incLink = "<li class='page-item'><a class='page-link' href='#' data-role='^{dataRole}' data-page='^{no}' aria-label='inc'><i class='fa fa-angle-right' ></i></a></li>";
        this.decLink = "<li class='page-item'><a class='page-link' href='#' data-role='^{dataRole}' data-page='^{no}' aria-label='dec'><i class='fa fa-angle-left' ></i></a></li>";
        this.currentPageLink = "<li class='page-item active'><a class='page-link' href='#' data-role='^{dataRole}' data-page='^{no}'>^{no}</a></li>";
        this.otherPageLink = "<li class='page-item'><a class='page-link' href='#' data-role='^{dataRole}' data-page='^{no}'>^{no}</a></li>";
        this.total = 0;
        this.scale = 10;

        this.prefix = "ko";
        this.jwt = new StringBuffer();
    }
    
    public PagingRenderer(String templateId, HttpServletRequest request) {
    	this();
    	
    	if(StringUtils.isNotBlank(templateId)) {
    		this.header = PropertiesMsgUtil.getMessage(templateId + ".header", request);
    		this.footer = PropertiesMsgUtil.getMessage(templateId + ".footer", request);
    		this.topLink = PropertiesMsgUtil.getMessage(templateId + ".topLink", request);
    		this.bottomLink = PropertiesMsgUtil.getMessage(templateId + ".bottomLink", request);
    		this.nextLink = PropertiesMsgUtil.getMessage(templateId + ".nextLink", request);
    		this.previousLink = PropertiesMsgUtil.getMessage(templateId + ".previousLink", request);
    		this.incLink = PropertiesMsgUtil.getMessage(templateId + ".incLink", request);
    		this.decLink = PropertiesMsgUtil.getMessage(templateId + ".decLink", request);
    		this.currentPageLink = PropertiesMsgUtil.getMessage(templateId + ".currentPageLink", request);
    		this.otherPageLink = PropertiesMsgUtil.getMessage(templateId + ".otherPageLink", request);
    	}
    	
	}

	public void doWritePageIndex(int totalNo, int currentNo, int pagingNo, String funcName) {
        long fromPageNo;
        long toPageNo;
        long bottomPageNo;

        this.total = totalNo;

        // 페이지 인덱스
        fromPageNo = currentNo - ((currentNo - 1) % this.scale);
        toPageNo = fromPageNo + this.scale - 1;
        if (this.total == 0) {
            bottomPageNo = 1;
        } else {
            bottomPageNo = this.total / pagingNo;
            if ((this.total % pagingNo) != 0) {
                bottomPageNo++;
            }
        }
        if (fromPageNo < 1) {
            fromPageNo = 1;
        }
        if (toPageNo > bottomPageNo) {
            toPageNo = bottomPageNo;
        }

        /* paging script 출력 */
        jwt.append("<!-- Paging script ");
        jwt.append(funcName);
        jwt.append("() -->");

        /* header 출력 */
        if (!StringUtils.isEmpty(this.header)) {
            jwt.append(this.header);
        }

        // 탑 페이지 출력
        if (!StringUtils.isEmpty(this.topLink)) {
            jwt.append(this.renderLink(this.topLink, currentNo, 1, bottomPageNo, funcName));
        }

        // 이전 페이지 출력
        if (!StringUtils.isEmpty(this.previousLink)) {
            long prePageNo;

            prePageNo = currentNo - this.scale;
            if (prePageNo < 1) {
                prePageNo = 1;
            }

            jwt.append(this.renderLink(this.previousLink, currentNo, prePageNo, bottomPageNo, funcName));
        }

        /* decLink 출력 */
        if (!StringUtils.isEmpty(this.decLink)) {
            long decPageNo;

            decPageNo = currentNo - 1;
            if (decPageNo < 1) {
                decPageNo = 1;
            }

            jwt.append(this.renderLink(this.decLink, currentNo, decPageNo, bottomPageNo, funcName));
        }

        /* currentPageLink or otherPageLink 출력 */
        for (long i = fromPageNo; i <= toPageNo; i++) {
            if (i > fromPageNo) {
                jwt.append("");
            }
            jwt.append(this.renderLink(i == currentNo ? this.currentPageLink : this.otherPageLink, currentNo, i, bottomPageNo, funcName));
        }

        /* incLink 출력 */
        if (!StringUtils.isEmpty(this.incLink)) {
            long incPageNo;

            incPageNo = currentNo + 1;
            if (incPageNo > bottomPageNo) {
                incPageNo = bottomPageNo;
            }

            jwt.append(this.renderLink(this.incLink, currentNo, incPageNo, bottomPageNo, funcName));
        }

        // 다음 페이지 출력
        if (!StringUtils.isEmpty(this.nextLink)) {
            long nextPageNo;

            nextPageNo = currentNo + this.scale;
            if (nextPageNo > bottomPageNo) {
                nextPageNo = bottomPageNo;
            }
            jwt.append(this.renderLink(this.nextLink, currentNo, nextPageNo, bottomPageNo, funcName));
        }

        // 마지막 페이지 출력
        if (!StringUtils.isEmpty(this.bottomLink)) {
            jwt.append(this.renderLink(this.bottomLink, currentNo, bottomPageNo, bottomPageNo, funcName));
        }

        /* footer 출력 */
        if (!StringUtils.isEmpty(this.footer)) {
            jwt.append(this.footer);
        }

    }
    
    /**
     * 링크 만들기.
     * 
     * @param src src
     * @param pageNo pageNo
     * @param workPageNo workPageNo
     * @param topPageNo topPageNo
     * @param funcName funcName
     * @return String
     */
    protected String renderLink(String src, long pageNo, long workPageNo, long topPageNo, String funcName) {
        Matcher mch;
        StringBuffer sb;
        String js;
        String link;
        String no;
        String style;

        String end;

        end = "";
        style = "";

        if (pageNo == workPageNo) {
            js = "";
            link = "#";
        } else {
            js = funcName + "(" + workPageNo + ")";
            link = "javascript:" + js;
        }

        String ufunc = funcName + "(this.value);";

        no = String.valueOf(workPageNo);
        sb = new StringBuffer();
        mch = ptnValueExpr.matcher(src);

        while (mch.find()) {
            String valueId;
            valueId = mch.group(1);

            if ("^".equals(valueId)) {
                mch.appendReplacement(sb, "\\^");
            } else if ("link".equals(valueId)) {
                mch.appendReplacement(sb, link);
            } else if ("js".equals(valueId)) {
                mch.appendReplacement(sb, js);
            } else if ("no".equals(valueId)) {
                mch.appendReplacement(sb, no);
            } else if ("style".equals(valueId)) {
                mch.appendReplacement(sb, style);
            } else if ("totPage".equals(valueId)) {
                mch.appendReplacement(sb, String.valueOf(topPageNo));
            } else if ("totPagev".equals(valueId)) {
                DecimalFormat decformater = new java.text.DecimalFormat("###,###,###");
                mch.appendReplacement(sb, String.valueOf(decformater.format(topPageNo)));
            } else if ("pageNo".equals(valueId)) {
                mch.appendReplacement(sb, String.valueOf(pageNo));
            } else if ("end".equals(valueId)) {
                mch.appendReplacement(sb, end);
            } else if ("total".equals(valueId)) {
                DecimalFormat decformater = new java.text.DecimalFormat("###,###,###");
                mch.appendReplacement(sb, String.valueOf(decformater.format(this.total)));
            } else if ("ufunc".equals(valueId)) {
                mch.appendReplacement(sb, ufunc);
            } else if ("pageNoSize".equals(valueId)) {
                mch.appendReplacement(sb, String.valueOf(String.valueOf(pageNo).length()));
            } else if ("totPageSize".equals(valueId)) {
                mch.appendReplacement(sb, String.valueOf(String.valueOf(topPageNo).length()));
            } else if ("prefix".equals(valueId)) {
                mch.appendReplacement(sb, this.prefix);
            } else if ("dataRole".equals(valueId)) {
                mch.appendReplacement(sb, funcName);
            } else {
                mch.appendReplacement(sb, "\\^{" + valueId + "}");
            }
        }

        mch.appendTail(sb);

        return sb.toString();
    }

	public String getJwt() {
		return jwt.toString();
	}

}
