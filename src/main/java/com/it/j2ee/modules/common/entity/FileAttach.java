package com.it.j2ee.modules.common.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="TC_FILE_ATTACH" ,schema="ydscm_common")
public class FileAttach implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1701275583140982725L;
	// Fields   
    private Long id;
    private String foreignId;
    private String bizCode;
    private String fileCode;
    private String fileName;
    private String filePath;
    private String fileRealPath;
    private String fileContentType;
    private String fileSize;
    private String bizName;
    private String createUser;
    private String createUserId;
    private Date createDate;
    private Long foreignId2;
    
	/**
	 * @return the id
	 */
    @Id
	@SequenceGenerator(name="SEQ_FILE_ATTACH",allocationSize=1, sequenceName="COMMONFRAMEWORK.SEQ_TC_BIZ_ATTACH")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="SEQ_FILE_ATTACH") 
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the foreignId
	 */
	public String getForeignId() {
		return foreignId;
	}
	/**
	 * @param foreignId the foreignId to set
	 */
	public void setForeignId(String foreignId) {
		this.foreignId = foreignId;
	}
	/**
	 * @return the bizCode
	 */
	public String getBizCode() {
		return bizCode;
	}
	/**
	 * @param bizCode the bizCode to set
	 */
	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}
	/**
	 * @return the fileCode
	 */
	public String getFileCode() {
		return fileCode;
	}
	/**
	 * @param fileCode the fileCode to set
	 */
	public void setFileCode(String fileCode) {
		this.fileCode = fileCode;
	}
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}
	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	/**
	 * @return the fileRealPath
	 */
	public String getFileRealPath() {
		return fileRealPath;
	}
	/**
	 * @param fileRealPath the fileRealPath to set
	 */
	public void setFileRealPath(String fileRealPath) {
		this.fileRealPath = fileRealPath;
	}
	/**
	 * @return the fileContentType
	 */
	public String getFileContentType() {
		return fileContentType;
	}
	/**
	 * @param fileContentType the fileContentType to set
	 */
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	/**
	 * @return the fileSize
	 */
	public String getFileSize() {
		return fileSize;
	}
	/**
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	/**
	 * @return the bizName
	 */
	public String getBizName() {
		return bizName;
	}
	/**
	 * @param bizName the bizName to set
	 */
	public void setBizName(String bizName) {
		this.bizName = bizName;
	}
	/**
	 * @return the createUser
	 */
	public String getCreateUser() {
		return createUser;
	}
	/**
	 * @param createUser the createUser to set
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	/**
	 * @return the createUserId
	 */
	public String getCreateUserId() {
		return createUserId;
	}
	/**
	 * @param createUserId the createUserId to set
	 */
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Long getForeignId2() {
		return foreignId2;
	}
	public void setForeignId2(Long foreignId2) {
		this.foreignId2 = foreignId2;
	}
    
    
    
}
