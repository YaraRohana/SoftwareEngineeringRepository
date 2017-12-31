package allClasses;

import java.util.ArrayList;

public class Customer {

	String id;
	String email;
	
	public Customer(String id, String email) {
		super();
		this.id = id;
		this.email = email;
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
		return "Customer [id=" + id + ", email=" + email  + "]";
	}
	
}
