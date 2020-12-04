package com.east.framework.core.util.model;

import java.io.Serializable;

/**
 * true, false 값과 메세지를 가지는 result 객체.
 *
 * Updated on : 2015-10-02 Updated by : love.
 */
public class BooleanAndMessageResult implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean result;

	private String message;

	/**
	 * @return the result
	 */
	public boolean isResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(boolean result) {
		this.result = result;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
