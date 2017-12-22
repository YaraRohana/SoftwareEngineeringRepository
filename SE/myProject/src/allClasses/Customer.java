package allClasses;

import java.util.ArrayList;

public class Customer {

	String id;
	String email;
	String password;
	
	public Customer(String id, String email,String password) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
	}
	
	public Customer(String id, String email) {
		super();
		this.id = id;
		this.email = email;
		//this.password = password;has to be generated randomly
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "Customer [id=" + id + ", email=" + email + ", password=" + password + "]";
	}
	
}
