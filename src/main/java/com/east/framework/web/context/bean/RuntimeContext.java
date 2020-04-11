package com.east.framework.web.context.bean;

import java.io.Serializable;
import java.util.Map;

/**
 * RuntimeContext
 * 
 * Updated on : 2013-09-01 Updated by : love.
 */
public class RuntimeContext implements Serializable {

	private static final long serialVersionUID = 1L;

	private String uuid;

	private String accessIp;

	private Map<String, Object> infoMap;

	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * @param uuid
	 *            the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * @return the accessIp
	 */
	public String getAccessIp() {
		return accessIp;
	}

	/**
	 * @param accessIp
	 *            the accessIp to set
	 */
	public void setAccessIp(String accessIp) {
		this.accessIp = accessIp;
	}

	/**
	 * @return the infoMap
	 */
	public Map<String, Object> getInfoMap() {
		return infoMap;
	}

	/**
	 * @param infoMap
	 *            the infoMap to set
	 */
	public void setInfoMap(Map<String, Object> infoMap) {
		this.infoMap = infoMap;
	}

}