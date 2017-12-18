package allClasses;

import java.util.ArrayList;

public class ParkingLot {


	int id;
	String name;
	String location;
	boolean isActive;
	boolean available;
	String manager;
	ArrayList<Customer> allCustomers;

	public ParkingLot(String name, String location, boolean isActive,
			boolean available, String manager) {
		super();
		this.name = name;
		this.location = location;
		this.isActive = isActive;
		this.available = available;
		this.manager = manager;
		this.allCustomers=new ArrayList<Customer>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}
	
	@Override
	public String toString() {
		return "ParkingLot [id=" + id + ", name=" + name + ", location=" + location + ", isActive=" + isActive
				+ ", available=" + available + ", manager=" + manager + "]";
	}
}
