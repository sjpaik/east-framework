package com.east.framework.web.file.model;

import java.io.Serializable;

/**
 * 파일 메타 정보 모델.
 *
 * Updated on : 2015-12-08 Updated by : love.
 */
public class FileMeta implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	private String originalName;

	private String fileExtension;

	private String contentType;

	private long fileSize;

	private String saveFileName;

	private String saveFilePath;

	private String fileKey;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the originalName
	 */
	public String getOriginalName() {
		return originalName;
	}

	/**
	 * @param originalName
	 *            the originalName to set
	 */
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	/**
	 * @return the fileExtension
	 */
	public String getFileExtension() {
		return fileExtension;
	}

	/**
	 * @param fileExtension
	 *            the fileExtension to set
	 */
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @param contentType
	 *            the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * @return the fileSize
	 */
	public long getFileSize() {
		return fileSize;
	}

	/**
	 * @param fileSize
	 *            the fileSize to set
	 */
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * @return the saveFileName
	 */
	public String getSaveFileName() {
		return saveFileName;
	}

	/**
	 * @param saveFileName
	 *            the saveFileName to set
	 */
	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}

	/**
	 * @return the saveFilePath
	 */
	public String getSaveFilePath() {
		return saveFilePath;
	}

	/**
	 * @param saveFilePath
	 *            the saveFilePath to set
	 */
	public void setSaveFilePath(String saveFilePath) {
		this.saveFilePath = saveFilePath;
	}

	/**
	 * @return the fileKey
	 */
	public String getFileKey() {
		return fileKey;
	}

	/**
	 * @param fileKey
	 *            the fileKey to set
	 */
	public void setFileKey(String fileKey) {
		this.fileKey = fileKey;
	}

}
