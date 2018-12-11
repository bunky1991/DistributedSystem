package studentApplication;

import java.io.Serializable;

public class uniqueUser implements Serializable{
	private String firstname;
	private String lastname;
	private String age;

	public uniqueUser(String firstname, String lastname, String age) {
		this.setFirstname(firstname);
		this.setLastname(lastname);
		this.setAge(age);
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

}
