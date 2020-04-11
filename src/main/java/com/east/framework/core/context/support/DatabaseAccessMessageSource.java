package com.east.framework.core.context.support;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * DataBase로부터 메세지코드를 받아와 사용하기 위한 MessageSource.
 *
 * Updated on : 2015-09-30 Updated by : love.
 */
public class DatabaseAccessMessageSource extends AbstractDatabaseAccessMessageSource {

	private final JdbcTemplate jdbcTemplate;
	private final String query;

	/**
	 * 생성자.
	 * 
	 * @param jdbcTemplate
	 *            JdbcTemplate
	 * @param query
	 *            쿼리
	 */
	public DatabaseAccessMessageSource(JdbcTemplate jdbcTemplate, String query) {
		this.jdbcTemplate = jdbcTemplate;
		this.query = query;
	}

	@Override
	protected JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}

	@Override
	protected String getSqlQuery() {
		return this.query;
	}

	@Override
	protected Messages extractQueryData(ResultSet rs) throws SQLException, DataAccessException {
		Messages messages = new Messages();
		while (rs.next()) {
			Locale locale = new Locale(rs.getString("LOCALE"));
			messages.addMessage(rs.getString("CODE"), locale, rs.getString("MSG"));
		}
		return messages;
	}

}
