package da;
import java.sql.SQLException;
import java.util.ArrayList;

import allClasses.Customer;
import allClasses.Order;
import allClasses.ParkingLot;
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
	
	public boolean addVehicle(Vehicle v) throws SQLException;
	public ArrayList<Vehicle> getAllVehicles()throws SQLException;
	
}
