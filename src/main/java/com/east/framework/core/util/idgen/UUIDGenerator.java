package com.east.framework.core.util.idgen;

/**
 * UUID 제너레이터 인터페이스.
 *
 * Updated on : 2015-10-13 Updated by : love.
 */
public interface UUIDGenerator {

	/**
	 * UUID 리턴.
	 *
	 * @return UUID
	 */
	String getId();

	/**
	 * prefix+"-"+UUID 리턴.
	 * 
	 * @param prefix
	 *            UUID 앞자리 필스.
	 * @return prefix+"-"+UUID
	 */
	String getSymbolId(String prefix);
}
