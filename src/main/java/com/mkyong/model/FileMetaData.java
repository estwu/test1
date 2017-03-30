package com.mkyong.model;

public class FileMetaData {
//	private boolean isDirectory;
//	private boolean isRegularFile;
	private long fileSize;
	private String lastAccessTime;
	
//	public boolean getIsDirectory() {
//		return isDirectory;
//	}
//	public void setDirectory(boolean isDirectory) {
//		this.isDirectory = isDirectory;
//	}
//	public boolean getIsRegularFile() {
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
		builder.append("File Size :").append(getFileSize()).append(" ").
		append("Last Access Time :").append(getLastAccessTime()).append(" ");
		return builder.toString();
		
	}
}
