package allClasses;

import java.util.ArrayList;

public class CPS {

	
	ArrayList<ParkingLot> parkingLots;
	ArrayList<Order> orders;
	ArrayList<Customer> customers;
	ArrayList<Complaint> complaints;
	ArrayList<Subscription> subscriptions;
	ArrayList<Vehicle> vehicles;
	
	private static CPS single_instance = null;
	private CPS(){
		this.parkingLots=new ArrayList<ParkingLot>();
		this.orders=new ArrayList<Order>();
		this.customers=new ArrayList<Customer>();
		this.complaints=new ArrayList<Complaint>();
		this.subscriptions=new ArrayList<Subscription>();
		this.vehicles=new ArrayList<Vehicle>();
	}
	
	 public ArrayList<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(ArrayList<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}

	public static CPS getSingle_instance() {
		return single_instance;
	}

	public static void setSingle_instance(CPS single_instance) {
		CPS.single_instance = single_instance;
	}

	public static CPS getInstance()
	    {
	        if (single_instance == null)
	            single_instance = new CPS();
	 
	        return single_instance;
	    }

	public ArrayList<ParkingLot> getParkingLots() {
		return parkingLots;
	}

	public void setParkingLots(ArrayList<ParkingLot> parkingLots) {
		this.parkingLots = parkingLots;
	}

	public ArrayList<Order> getOrders() {
		return orders;
	}

	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}

	public ArrayList<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(ArrayList<Customer> customers) {
		this.customers = customers;
	}

	public ArrayList<Complaint> getComplaints() {
		return complaints;
	}

	public void setComplaints(ArrayList<Complaint> complaints) {
		this.complaints = complaints;
	}

	public ArrayList<Subscription> getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(ArrayList<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}
	 
	 
}
