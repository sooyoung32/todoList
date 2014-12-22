package vo;

import java.util.Date;

public class DBCommon {
	
	private Date writingDate;
	private Date modifyDate; 
	private String writingIP;
	private String modifyIP;
	
	
	public Date getWritingDate() {
		return writingDate;
	}
	public void setWritingDate(Date writingDate) {
		this.writingDate = writingDate;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getWritingIP() {
		return writingIP;
	}
	public void setWritingIP(String writingIP) {
		this.writingIP = writingIP;
	}
	public String getModifyIP() {
		return modifyIP;
	}
	public void setModifyIP(String modifyIP) {
		this.modifyIP = modifyIP;
	} 
	
	
	
	
}
