package vo;

import java.util.Date;

public class Member extends DBCommon {
	
	private String email; 
	private String name; 
	private String password;
	private Date loginDate;
	private String isFB;
	private String fbToken; 
	private String fbUserId;
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	public String getIsFB() {
		return isFB;
	}
	public void setIsFB(String isFB) {
		this.isFB = isFB;
	}
	public String getFbToken() {
		return fbToken;
	}
	public void setFbToken(String fbToken) {
		this.fbToken = fbToken;
	}
	public String getFbUserId() {
		return fbUserId;
	}
	public void setFbUserId(String fbUserId) {
		this.fbUserId = fbUserId;
	}
	
	
	
	@Override
	public String toString() {
		return "Member [email=" + email + ", name=" + name + ", password=" + password + ", loginDate=" + loginDate
				+ ", isFB=" + isFB + ", fbToken=" + fbToken + ", fbUserId=" + fbUserId + "]";
	}


	
	
	
	
	
}
