package vo;

import java.util.Date;

public class Member extends DBCommon {
	
	private String email; 
	private String name; 
	private String password;
	private Date loginDate;
	
	//getter&setter 
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
	
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	
	public Date getLoginDate() {
		return loginDate;
	}
	
	
	@Override
	public String toString() {
		return "Member [email=" + email + ", name=" + name + ", password="
				+ password + ", loginDate=" + loginDate + "]";
	}
	
	
	
	
	
}
