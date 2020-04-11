package com.east.framework.core.exception;

/**
 * 메시지 전달을 위한 Excepion.
 *
 * Updated on : 2015-10-01 Updated by : love.
 */
public class NoticeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String code;

	private Object[] args;

	/**
	 * @param code
	 *            에러코드
	 */
	public NoticeException(String code) {
		this(code, null, "");
	}

	/**
	 * @param code
	 *            에러코드
	 * @param args
	 *            에러메시지 아큐먼트
	 */
	public NoticeException(String code, Object... args) {
		this(code, null, args);
	}

	/**
	 * @param code
	 *            에러코드
	 * @param cause
	 *            원인 에러
	 */
	public NoticeException(String code, Throwable cause) {
		this(code, cause, "");
	}

	/**
	 * @param code
	 *            에러코드
	 * @param cause
	 *            에러 원인
	 * @param args
	 *            에러 메세지 아큐먼트
	 */
	public NoticeException(String code, Throwable cause, Object... args) {
		super(code, cause);
		this.code = code;
		this.args = args;
	}

	/**
	 * @return 코드
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            코드
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return 인자
	 */
	public Object[] getArgs() {
		return args;
	}

	/**
	 * @param args
	 *            인자
	 */
	public void setArgs(Object[] args) {
		this.args = args;
	}

}
