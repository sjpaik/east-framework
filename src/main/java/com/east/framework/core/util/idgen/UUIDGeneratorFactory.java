package com.east.framework.core.util.idgen;

/**
 * UUID 펙토리 빈.
 *
 * Updated on : 2015-10-13 Updated by : love.
 */
public class UUIDGeneratorFactory {

	/**
	 * FomatUUIDGeneratorImpl 구현체 리턴.
	 *
	 * @param fmt
	 *            String : Date Format
	 *
	 * @return new FomatUUIDGeneratorImpl()
	 */
	public static UUIDGenerator getFomatIns(String fmt) {
		return new FomatUUIDGeneratorImpl(fmt);
	}

	/**
	 * FomatUUIDGeneratorImpl 구현체 리턴.
	 *
	 * @return new FomatUUIDGeneratorImpl()
	 */
	public static UUIDGenerator getFomatIns() {
		return new FomatUUIDGeneratorImpl();
	}

	/**
	 * TimeMillisUUIDGeneratorImpl 구현체 리턴.
	 *
	 * @return new TimeMillisUUIDGeneratorImpl()
	 */
	public static UUIDGenerator getTimeMillisIns() {
		return new TimeMillisUUIDGeneratorImpl();
	}
}
