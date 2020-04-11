package com.east.framework.core.util.idgen;

import java.util.UUID;

/**
 * UUID 제너레이터 System.currentTimeMillis() 구현체.
 *
 * Updated on : 2015-10-13 Updated by : love.
 */
public class TimeMillisUUIDGeneratorImpl implements UUIDGenerator {

	private static final String REPLACE_REGEX_CHAR = "-";
	private static final String REPLACE_REPLACEMENT_CHAR = "";

	/**
	 * 생성자.
	 */
	TimeMillisUUIDGeneratorImpl() {
	}

	@Override
	public String getId() {
		return System.currentTimeMillis() + "-" + this.getUUID();
	}

	@Override
	public String getSymbolId(String preFix) {
		return preFix + "-" + System.currentTimeMillis() + "-" + this.getUUID();
	}

	/**
	 * 아이디 요청.
	 * 
	 * @return String
	 */
	private String getUUID() {
		return UUID.randomUUID().toString().replaceAll(REPLACE_REGEX_CHAR, REPLACE_REPLACEMENT_CHAR);
	}

}
