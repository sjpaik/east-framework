package com.east.framework.core.exception.model;

import java.io.Serializable;

/**
 * 에러 기본 객체.
 *
 * Updated on : 2015-10-01 Updated by : love.
 */
public class BaseErrorInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	protected String code;

	protected String message;

	protected String detail;

	/**
	 * @return 에러 코드.
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            에러 코드
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return 메세지
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            메세지
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return 에러 상세 내역
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * @param detail
	 *            에러 상세 내역
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}
}
