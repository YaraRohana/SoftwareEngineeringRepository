package allClasses;

import java.sql.SQLException;
import java.util.ArrayList;

import da.DataAccess;

public class CPS {

	
	static ArrayList<ParkingLot> parkingLots;
//	ArrayList<Order> orders;
//	ArrayList<Customer> customers;
//	ArrayList<Complaint> complaints;
//	ArrayList<Subscription> subscriptions;
//	ArrayList<Vehicle> vehicles;
	DataAccess da=new DataAccess();
	
	private static CPS single_instance = null;
	private CPS() throws SQLException{
		CPS.parkingLots=new ArrayList<ParkingLot>();
		CPS.parkingLots=da.GetAllParkingLots();
//		this.orders=new ArrayList<Order>();
//		this.customers=new ArrayList<Customer>();
//		this.complaints=new ArrayList<Complaint>();
//		this.subscriptions=new ArrayList<Subscription>();
//		this.vehicles=new ArrayList<Vehicle>();
	}
	
//	 public ArrayList<Vehicle> getVehicles() {
//		return vehicles;
//	}
//
//	public void setVehicles(ArrayList<Vehicle> vehicles) {
//		this.vehicles = vehicles;
//	}

	public static CPS getSingle_instance() {
		return single_instance;
	}

	public static void setSingle_instance(CPS single_instance) {
		CPS.single_instance = single_instance;
	}

	public static CPS getInstance() throws SQLException
	    {
	        if (single_instance == null)
	            single_instance = new CPS();
	 
	        return single_instance;
	    }

	public ArrayList<ParkingLot> getParkingLots() throws SQLException {
		return parkingLots;
	}

	public void setParkingLots(ArrayList<ParkingLot> parkingLots) {
		CPS.parkingLots = parkingLots;
	}

//	public ArrayList<Order> getOrders() {
//		return orders;
//	}
//
//	public void setOrders(ArrayList<Order> orders) {
//		this.orders = orders;
//	}
//
//	public ArrayList<Customer> getCustomers() {
//		return customers;
//	}
//
//	public void setCustomers(ArrayList<Customer> customers) {
//		this.customers = customers;
//	}
//
//	public ArrayList<Complaint> getComplaints() {
//		return complaints;
//	}
//
//	public void setComplaints(ArrayList<Complaint> complaints) {
//		this.complaints = complaints;
//	}
//
//	public ArrayList<Subscription> getSubscriptions() {
//		return subscriptions;
//	}
//
//	public void setSubscriptions(ArrayList<Subscription> subscriptions) {
//		this.subscriptions = subscriptions;
//	}
	 
	 
}
