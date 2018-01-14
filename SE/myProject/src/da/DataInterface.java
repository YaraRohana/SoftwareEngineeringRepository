package da;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import allClasses.Complaint;
import allClasses.Customer;
import allClasses.Employee.employeeType;
import allClasses.FullSubscription;
import allClasses.OneCarBusinessSubscription;
import allClasses.OneCarRegularSubscription;
import allClasses.Order;
import allClasses.Order.OrderType;
import allClasses.ParkingLot;
import allClasses.Subscription;
import allClasses.Vehicle;

public interface  DataInterface {
	
	public boolean addParkingLot(ParkingLot p) throws SQLException;
	public boolean deleteParkingLot(String nameOfParkingLot) throws SQLException;
	public ArrayList<ParkingLot> GetAllParkingLots() throws SQLException;
	public ParkingLot getParkingLotByName(String name) throws SQLException;

	public boolean addCustomer(Customer c) throws SQLException;
	public ArrayList<Customer> getAllCustomers()throws SQLException;
	
	public boolean addOrder(Order o) throws SQLException;
	public ArrayList<Order> getAllOrders() throws SQLException;
	public ArrayList<Order> getAllOrdersByCustomerId(String id) throws SQLException;
	public ArrayList<Order> getAllOrdersByVehicleNumber(String vehicleNum) throws SQLException;
	public ArrayList<Subscription> getAllSubsByCustomerId(String customerId) throws SQLException;
	public ArrayList<Complaint> getAllComplaints() throws SQLException;
	
	public boolean addVehicle(Vehicle v) throws SQLException;
	public ArrayList<Vehicle> getAllVehicles()throws SQLException;
	
	public boolean addComplaint(Complaint c) throws SQLException;
	
	public boolean checkIfCustomerExistsById(String customerId) throws SQLException;
	public boolean checkIfVehicleExistsByNumber(String vehicleNum) throws SQLException;
	public boolean checkIfEmployeeExists(String name,String password) throws SQLException;
	public Order checkIfOrderExistsByAllParameters(String customerId,String vehicle,String arrivingDate,String arrivingAt,String leavingDate,String leavingAt,String parkingLot)throws SQLException;
	public boolean addBuisnessRegularSubscription (OneCarBusinessSubscription sub) throws SQLException;
	public boolean addOneCarRegularSubscription (OneCarRegularSubscription oneCarRegularSubscription) throws SQLException;
	public boolean addFullSubscription(FullSubscription fullSubscription) throws SQLException;
	
	public void cancelOrder(Order order) throws SQLException;
	public void setupParkingLot(String parkingLot,int width)throws SQLException;
	public void logOutEmployee(String name) throws SQLException;
	public void logOutCustomer(String customerId) throws SQLException;
	
	public void loginCustomer(String customerId)throws SQLException;
	public void loginEmployee(String name,String password) throws SQLException;
	public String getEmployeeType(String name) throws SQLException;
	public void updateCompensationForCustomer(String customerId,int compensation) throws SQLException;
	public double getOrderCost(String parkingLot, String arrivingAt, String leavingAt, OrderType type) throws SQLException, Exception;
	public int getFullSubscriptionCost(String parkingLot)  throws NumberFormatException, SQLException;
	public int getBusinessRegularSubscriptionCost(String customerId,String parkingLot) throws NumberFormatException, SQLException;
	public boolean getCancelOrderCredit(String parkingLot, String customerId, String arrivingDate, String arrivingAt,String leavingAt, OrderType type) throws SQLException, Exception;
	public String getEmployeeParkingLot(String name)throws SQLException;
	public boolean employeePriceChange(String name, int preOrder, int uponArrival) throws SQLException, Exception;
	public boolean saveParkingSpot(String parkingLot, int row, int column, int width) throws SQLException;
	public String getSaltString();
	public String getCustomerMailById(String customerId) throws SQLException;
	public ArrayList<Subscription> getAllRegSubsByStartingDate(Date startDate) throws SQLException;
	public ArrayList<FullSubscription> getAllFullSubsByArrivedSince(Date arriveDate) throws SQLException;
	public ArrayList<FullSubscription> getAllFullSubsByStartingDate(Date startDate) throws SQLException;
	public ArrayList<Order> getAllPreOrdersByArrivingDate(String arriveDate, String arrivingAt) throws SQLException;
}
