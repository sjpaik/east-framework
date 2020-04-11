package com.east.framework.core.persistence.dao.page;

/**
 * Page Statement Builder.
 *
 * Updated on : 2015-09-25 Updated by : love.
 */
public class PageStatementBuilder {
	private String totalCountStatementId;
	private String dataStatementId;

	/**
	 * PageStatementBuilder 생성.
	 *
	 * @return PageStatementBuilder
	 */
	public static PageStatementBuilder create() {
		return new PageStatementBuilder();
	}

	/**
	 * 총 카운터 StatementId.
	 *
	 * @param totalCountStatementId
	 *            totalCountStatementId
	 * @return PageStatementBuilder
	 */
	public PageStatementBuilder totalCountStatementId(String totalCountStatementId) {
		this.totalCountStatementId = totalCountStatementId;
		return this;
	}

	/**
	 * data StatementId.
	 *
	 * @param dataStatementId
	 *            dataStatementId
	 * @return PageStatementBuilder
	 */
	public PageStatementBuilder dataStatementId(String dataStatementId) {
		this.dataStatementId = dataStatementId;
		return this;
	}

	/**
	 * PageStatementBuilder Build.
	 *
	 * @return PageStatement
	 */
	public PageStatement build() {
		return new PageStatement(this.totalCountStatementId, this.dataStatementId);
	}

	/**
	 *
	 * @param totalCountStatementId
	 *            totalCountStatementId
	 * @param dataStatementId
	 *            dataStatementId
	 * @return PageStatement
	 */
	public static PageStatement buildStatement(final String totalCountStatementId, final String dataStatementId) {
		return new PageStatement() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see com.minigate.framework.core.persistence.dao.page
			 * .PageStatement#getTotalCountStatementId()
			 */
			@Override
			public String getTotalCountStatementId() {
				return totalCountStatementId;
			}

			/*
			 * (non-Javadoc)
			 *
			 * @see com.minigate.framework.core.persistence.dao.page
			 * .PageStatement#getDataStatementId()
			 */
			@Override
			public String getDataStatementId() {
				return dataStatementId;
			}
		};
	}
}
