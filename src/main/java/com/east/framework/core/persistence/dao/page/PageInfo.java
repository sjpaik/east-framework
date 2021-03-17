package com.east.framework.core.persistence.dao.page;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.east.framework.core.util.properties.PropertiesMsgUtil;

/**
 * Page Info
 * Updated on : 2015-09-25 Updated by : love.
 * @param <T>
 */
public class PageInfo<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 생성자
	 */
	public PageInfo() {}

	/**
	 * @param data data
	 */
	public PageInfo(List<T> data) {
		this.data = data;
	}

	/**
	 * @param pageIndex - 현재 페이지 번호
	 * @param pageSize - 한 페이지 조회 수(pageSize)
	 * @param totalCount - 총 페이지 수
	 * @param data data
	 */
	public PageInfo(int pageIndex, int pageSize, int totalCount, List<T> data) {
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		this.data = data;
	}

	private int totalCount;
	private int pageIndex;
	private int pageSize;
	private List<T> data;

	public int getTotalCount() {
		return totalCount;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public List<T> getData() {
		return data;
	}

	public String getPaging(String templateId) {
		PagingRenderer pagingRenderer = new PagingRenderer(templateId, ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
		pagingRenderer.doWritePageIndex(this.totalCount, this.pageIndex, this.pageSize, "goPage");
		return pagingRenderer.getJwt();
	}
	
	public String getPagingHeader(String templateId) {
		int maxPage = this.totalCount / this.pageSize;
        if (totalCount % pageSize > 0) {
            ++maxPage;
        }
		
		return PropertiesMsgUtil.getMessage(templateId + ".headerIndex", new Object[] { this.totalCount, this.pageIndex, maxPage }, ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
	}
	
}
