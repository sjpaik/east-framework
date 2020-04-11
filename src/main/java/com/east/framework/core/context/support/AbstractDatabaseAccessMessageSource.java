package com.east.framework.core.context.support;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.util.Assert;

/**
 * DataBase로부터 메세지코드를 받아와 사용하기 위한 Abstract MessageSource.
 *
 * Updated on : 2015-09-30 Updated by : love.
 */
public abstract class AbstractDatabaseAccessMessageSource extends AbstractMessageSource {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDatabaseAccessMessageSource.class);

	private Messages messages;

	private final Map<String, Map<Locale, MessageFormat>> cachedMessageFormats;

	/**
	 * 생성자.
	 */
	public AbstractDatabaseAccessMessageSource() {
		this.cachedMessageFormats = new HashMap<String, Map<Locale, MessageFormat>>();
	}

	@Override
	protected String resolveCodeWithoutArguments(String code, Locale locale) {
		String msg = messages.getMessage(code, locale);
		if (msg == null) {
			return null;
		} else {
			return msg;
		}
	}

	@Override
	protected MessageFormat resolveCode(String code, Locale locale) {
		String msg = messages.getMessage(code, locale);

		synchronized (this.cachedMessageFormats) {
			Map<Locale, MessageFormat> localeMap = this.cachedMessageFormats.get(code);
			if (localeMap != null) {
				MessageFormat result = localeMap.get(locale);
				if (result != null) {
					return result;
				}
			}

			if (msg != null) {
				if (localeMap == null) {
					localeMap = new HashMap<Locale, MessageFormat>();
					this.cachedMessageFormats.put(code, localeMap);
				}
				MessageFormat result = createMessageFormat(msg, locale);
				localeMap.put(locale, result);
				return result;
			}
		}
		return null;
	}

	/**
	 * Associate the given message with the given code.
	 *
	 * @param code
	 *            the lookup code
	 * @param locale
	 *            the locale that the message should be found within
	 * @param msg
	 *            the message associated with this lookup code
	 */
	public void addMessage(String code, Locale locale, String msg) {
		Assert.notNull(code, "Code must not be null");
		Assert.notNull(locale, "Locale must not be null");
		Assert.notNull(msg, "Message must not be null");
		this.messages.addMessage(code, locale, msg);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Added message [" + msg + "] for code [" + code + "] and Locale [" + locale + "]");
		}
	}

	/**
	 * Associate the given message values with the given keys as codes.
	 *
	 * @param messages
	 *            the messages to register, with messages codes as keys and
	 *            message texts as values
	 * @param locale
	 *            the locale that the messages should be found within
	 */
	public void addMessages(Map<String, String> messages, Locale locale) {
		Assert.notNull(messages, "Messages Map must not be null");
		for (Map.Entry<String, String> entry : messages.entrySet()) {
			addMessage(entry.getKey(), locale, entry.getValue());
		}
	}

	@Override
	public String toString() {
		return getClass().getName() + ": " + this.messages;
	}

	/**
	 * 초기 셋팅.
	 */
	@PostConstruct
	public void init() {

		String query = this.getSqlQuery();

		LOGGER.info("Initializing message source using query [{}]", query);

		JdbcTemplate jdbcTemplate = this.getJdbcTemplate();

		LOGGER.info("Initializing message source using jdbcTemplate [{}]", jdbcTemplate.toString());

		this.messages = jdbcTemplate.query(query, new ResultSetExtractor<Messages>() {
			@Override
			public Messages extractData(ResultSet rs) throws SQLException, DataAccessException {
				return extractQueryData(rs);
			}
		});
	}

	/**
	 * JdbcTemplate 획득을 위한 abstract 메소드.
	 *
	 * @return JdbcTemplate
	 */
	protected abstract JdbcTemplate getJdbcTemplate();

	/**
	 * SqlQuery 획득을 위한 abstract 메소드.
	 *
	 * @return SqlQuery
	 */
	protected abstract String getSqlQuery();

	/**
	 * 데이터 획득을 위한 abstract 메소드.
	 *
	 * @param rs
	 *            ResultSet
	 * @return Messages
	 * @throws SQLException
	 *             SQLException
	 * @throws DataAccessException
	 *             DataAccessException
	 */
	protected abstract Messages extractQueryData(ResultSet rs) throws SQLException, DataAccessException;

	/**
	 * Messages bundle.
	 */
	protected static final class Messages {

		private Map<String, Map<Locale, String>> messages;

		/**
		 * 메세지 add.
		 *
		 * @param code
		 *            코드
		 * @param locale
		 *            Locale
		 * @param msg
		 *            메세지.
		 */
		public void addMessage(String code, Locale locale, String msg) {
			if (messages == null) {
				messages = new HashMap<String, Map<Locale, String>>();
			}
			Map<Locale, String> data = messages.get(code);
			if (data == null) {
				data = new HashMap<Locale, String>();
				messages.put(code, data);
			}
			data.put(locale, msg);
		}

		/**
		 * 메세지 획득.
		 *
		 * @param code
		 *            코드
		 * @param locale
		 *            Locale
		 * @return 메세지.
		 */
		public String getMessage(String code, Locale locale) {
			Map<Locale, String> data = messages.get(code);
			return data != null ? data.get(locale) : null;
		}
	}

}
