package com.east.framework.core.persistence.dao.page;

import java.io.Serializable;
import java.util.List;

/**
 * Page Info.
 *
 * Updated on : 2015-09-25 Updated by : love.
 *
 * @param <T>
 */
public class PageInfo<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 생성자.
	 */
	public PageInfo() {
	}

	/**
	 * @param data
	 *            data
	 */
	public PageInfo(List<T> data) {
		this.data = data;
	}

	/**
	 *
	 * @param pageIndex
	 *            - 현재 페이지 번호
	 * @param pageSize
	 *            - 한 페이지 조회 수(pageSize)
	 * @param totalCount
	 *            - 총 페이지 수
	 * @param data
	 *            data
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

	/**
	 * get 현재 페이지 번호.
	 *
	 * @return pageIndex
	 */
	public int getPageIndex() {
		return this.pageIndex;
	}

	/**
	 * get 한 페이지 조회 수.
	 *
	 * @return pageSize
	 */
	public int getPageSize() {
		return this.pageSize;
	}

	/**
	 * get 총 페이지 수.
	 *
	 * @return totalCount
	 */
	public int getTotalCount() {
		return this.totalCount;
	}

	/**
	 * List<데이터 테이블>.
	 *
	 * @return List<T>
	 */
	public List<T> getData() {
		return this.data;
	}

}
