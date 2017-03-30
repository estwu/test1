package com.mkyong.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "filemetadata")
public class FileMetaDataEntity implements Serializable {
	private static final long serialVersionUID = -1;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
//	@Column(name="isDirectory")
//	private boolean isDirectory;
//	
//	@Column(name="isRegularFile")
//	private boolean isRegularFile;
	
	@Column(name="filesize")
	private long fileSize;
	
	@Column(name="lastaccesstime")
	private String lastAccessTime;
	
	protected FileMetaDataEntity(){
		    
	}
	

//	public boolean isDirectory() {
//		return isDirectory;
//	}
//	public void setDirectory(boolean isDirectory) {
//		this.isDirectory = isDirectory;
//	}
//	public boolean isRegularFile() {
//		return isRegularFile;
//	}
//	public void setRegularFile(boolean isRegularFile) {
//		this.isRegularFile = isRegularFile;
//	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	public String getLastAccessTime() {
		return lastAccessTime;
	}
	public void setLastAccessTime(String lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
//		builder.append("IsRegularFile :").append(isRegularFile()).
//		append("IsDirectory :").append(isDirectory()).
		builder.append("File Size :").append(getFileSize()).
		append("Last Access Time :").append(getLastAccessTime());
		return builder.toString();
		
	}
}
