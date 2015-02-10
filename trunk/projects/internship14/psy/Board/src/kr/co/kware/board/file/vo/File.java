package kr.co.kware.board.file.vo;

import kr.co.kware.common.dbcommon.DeletionStatus;


public class File {
	
	private int fileNo; 
	private int articleNo; 
	private String originalName;
	private String savedPath;
//	private int flag;
	private DeletionStatus deletionStatus; 
	
	//getter&setter 
	public int getFileNo() {
		return fileNo;
	}
	public void setFileNo(int fileNo) {
		this.fileNo = fileNo;
	}
	public int getArticleNo() {
		return articleNo;
	}
	public void setArticleNo(int articleNo) {
		this.articleNo = articleNo;
	}
	public String getOriginalName() {
		return originalName;
	}
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	public String getSavedPath() {
		return savedPath;
	}
	public void setSavedPath(String savedPath) {
		this.savedPath = savedPath;
	}
	
//	public void setFlag(int flag) {
//		this.flag = flag;
//	}
//	
//	public int getFlag() {
//		return flag;
//	}
	
	public DeletionStatus getDeletionStatus() {
		return deletionStatus;
	}
	
	public void setDeletionStatus(DeletionStatus deletionStatus) {
		this.deletionStatus = deletionStatus;
	}
	
	
	@Override
	public String toString() {
		return "File [fileNo=" + fileNo + ", boardNo=" + articleNo + ", originalName=" + originalName + ", savedPath="
				+ savedPath + ", deletionStatus=" + deletionStatus + "]";
	}
	
	
	
//	@Override
//	public String toString() {
//		return "File [fileNo=" + fileNo + ", boardNo=" + boardNo + ", originalName=" + originalName + ", savedPath="
//				+ savedPath + ", flag=" + flag + "]";
//	}
	
	
	
	
	
	
	
	
}
