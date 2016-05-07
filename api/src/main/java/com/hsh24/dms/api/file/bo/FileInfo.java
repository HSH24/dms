package com.hsh24.dms.api.file.bo;

import com.hsh24.dms.framework.bo.SearchInfo;

/**
 * 文件信息.
 * 
 * @author JiakunXu
 * 
 */
public class FileInfo extends SearchInfo {

	private static final long serialVersionUID = 6240425668306639391L;

	private Long fileId;

	private Long supId;

	private String fileName;

	/**
	 * txt jpg ...
	 */
	private String suffix;

	/**
	 * hdfs path.
	 */
	private String filePath;

	/**
	 * U or D.
	 */
	private String state;

	private String modifyUser;

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public Long getSupId() {
		return supId;
	}

	public void setSupId(Long supId) {
		this.supId = supId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

}
