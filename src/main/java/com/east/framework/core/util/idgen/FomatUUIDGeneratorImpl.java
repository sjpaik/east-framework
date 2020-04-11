package com.east.framework.core.util.idgen;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * UUID 제너레이터 SimpleDateFormat("yyyyMMddHHmmss") 구현체.
 *
 * Updated on : 2015-10-13 Updated by : love.
 */
public class FomatUUIDGeneratorImpl implements UUIDGenerator {

	private static final String REPLACE_REGEX_CHAR = "-";
	private static final String REPLACE_REPLACEMENT_CHAR = "";

	private SimpleDateFormat df;

	/**
	 * 생성자.
	 */
	FomatUUIDGeneratorImpl() {
		df = new SimpleDateFormat("yyyyMMddHHmmss");
	}

	/**
	 * 생성자.
	 *
	 * @param fmt
	 *            데이터 포멧
	 */
	FomatUUIDGeneratorImpl(String fmt) {
		df = new SimpleDateFormat(fmt);
	}

	@Override
	public String getId() {
		return df.format(new Date()) + "-" + this.getUUID();
	}

	@Override
	public String getSymbolId(String preFix) {
		return preFix + "-" + df.format(new Date()) + "-" + this.getUUID();
	}

	/**
	 * id 요청.
	 *
	 * @return UUID
	 */
	private String getUUID() {
		return UUID.randomUUID().toString().replaceAll(REPLACE_REGEX_CHAR, REPLACE_REPLACEMENT_CHAR);
	}

}
