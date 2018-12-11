package studentApplication;

import java.io.Serializable;

public class toDelete implements Serializable{
	private String Fname;
	private String username;
	
	public toDelete(String Fname, String username) {
		this.Fname = Fname;
		this.username = username;
	}
	
	public String getFname() {
		return Fname;
	}
	public void setFname(String fname) {
		Fname = fname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

}
