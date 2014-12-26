package vo;

public class File {
	
	private int fileNo; 
	private int boardNo; 
	private String originalName;
	private String savedPath;
	private int flag; 
	
	
	
	//getter&setter 
	public int getFileNo() {
		return fileNo;
	}
	public void setFileNo(int fileNo) {
		this.fileNo = fileNo;
	}
	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
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
	
	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	public int getFlag() {
		return flag;
	}
	@Override
	public String toString() {
		return "File [fileNo=" + fileNo + ", boardNo=" + boardNo + ", originalName=" + originalName + ", savedPath="
				+ savedPath + ", flag=" + flag + "]";
	}
	
	
	
	
	
	
}
