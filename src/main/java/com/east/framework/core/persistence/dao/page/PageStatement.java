package com.east.framework.core.persistence.dao.page;

/**
 * PageStatement.
 *
 * Updated on : 2015-09-25 Updated by : love.
 */
public class PageStatement {
	private String totalCountStatementId;
	private String dataStatementId;

	/**
	 * PageStatement 생성자.
	 *
	 * @param totalCountStatementId
	 *            totalCountStatementId
	 * @param dataStatementId
	 *            dataStatementId
	 */
	public PageStatement(String totalCountStatementId, String dataStatementId) {
		super();

		this.totalCountStatementId = totalCountStatementId;
		this.dataStatementId = dataStatementId;
	}

	/**
	 * PageStatement.
	 */
	public PageStatement() {
		super();
	}

	/**
	 * @return the totalCountStatementId
	 */
	public String getTotalCountStatementId() {
		return this.totalCountStatementId;
	}

	/**
	 * @return the dataStatementId
	 */
	public String getDataStatementId() {
		return this.dataStatementId;
	}

}
