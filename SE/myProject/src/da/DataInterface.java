package da;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import allClasses.Complaint;
import allClasses.Customer;
import allClasses.FullSubscription;
import allClasses.Order;
import allClasses.ParkingLot;
import allClasses.Subscription;
import allClasses.Vehicle;

public interface  DataInterface {
	
	public boolean addParkingLot(ParkingLot p) throws SQLException;
	//public boolean AddOrder(Order order, Customer c , Vehicle v ,ParkingLot p ) throws SQLException;
	public boolean deleteParkingLot(String nameOfParkingLot) throws SQLException;
	public ArrayList<ParkingLot> GetAllParkingLots() throws SQLException;
	public int getParkingIdLotByName(String name) throws SQLException;

	public boolean addCustomer(Customer c) throws SQLException;
	public ArrayList<Customer> getAllCustomers()throws SQLException;
	
	public boolean addOrder(Order o) throws SQLException;
	public ArrayList<Order> getAllOrders() throws SQLException;
	public ArrayList<Order> getAllOrdersByCustomerId(String id) throws SQLException;
	public ArrayList<Order> getAllOrdersByVehicleNumber(String vehicleNum) throws SQLException;
	public ArrayList<Subscription> getAllSubsByCustomerId(String customerId) throws SQLException;
	
	
	public boolean addVehicle(Vehicle v) throws SQLException;
	public ArrayList<Vehicle> getAllVehicles()throws SQLException;
	
	public boolean addComplaint(Complaint c) throws SQLException;
	
	public boolean checkIfCustomerExistsById(String customerId) throws SQLException;
	public boolean checkIfVehicleExistsByNumber(String vehicleNum) throws SQLException;
	
	
	//public boolean addRegularSubscription(RegularSubscription RegularSubscription) throws SQLException;
	public boolean addFullSubscription(FullSubscription fullSubscription) throws SQLException;
	//public boolean addFullSubscription(String customerId, String vehicleNumber, Date startDate) throws SQLException;
	//public boolean addRegularSubscription(String customerId, String vehicleNumber, Date startDate, String parkingLot, String leavingAt) throws SQLException;
	
}
