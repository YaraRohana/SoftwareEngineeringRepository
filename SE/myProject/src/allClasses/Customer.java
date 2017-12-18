package allClasses;

import java.util.ArrayList;

public class Customer {

	String id;
	String email;
	ArrayList<Order> allOrders;
	
	public Customer(String id, String email) {
		super();
		this.id = id;
		this.email = email;
		this.allOrders=new ArrayList<Order>();
	}

	public ArrayList<Order> getAllOrders() {
		return allOrders;
	}

	public void setAllOrders(ArrayList<Order> allOrders) {
		this.allOrders = allOrders;
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
	
	
	
}
