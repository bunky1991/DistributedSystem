package studentApplication;

import java.io.Serializable;

public class changeDetails implements Serializable{
	private String fixedusername;
	private String fixedPassword;
	private String firstname;
	private String lastname;
	private String age;
	private String courseID;
	private String password;
	private String username;

	public changeDetails(String fixedUsername, String fixedPassword, String firstname, String lastname, String age, 
							String courseID, String password, String username) {
		this.fixedusername = fixedUsername;
		this.fixedPassword = fixedPassword;
		this.firstname = firstname;
		this.lastname = lastname;
		this.age = age;
		this.courseID = courseID;
		this.password = password;
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFixedusername() {
		return fixedusername;
	}

	public void setFixedusername(String fixedusername) {
		this.fixedusername = fixedusername;
	}

	public String getFixedPassword() {
		return fixedPassword;
	}

	public void setFixedPassword(String fixedPassword) {
		this.fixedPassword = fixedPassword;
	}

}
