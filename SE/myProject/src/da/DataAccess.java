package da;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import allClasses.Complaint;
import allClasses.Customer;
import allClasses.FullSubscription;
import allClasses.Order;
import allClasses.Order.OrderType;
import allClasses.Subscription.subscriptionType;
import allClasses.ParkingLot;
import allClasses.RegularSubscription;
import allClasses.Vehicle;
import da.DataInterface;

public class DataAccess implements DataInterface {

	private Connection c;

	public DataAccess() {

		Logger logger = Logger.getLogger(DataAccess.class.getName());
		logger.log(Level.INFO, "DataAccess c'tor: attempting connection...");
		c = utils.DBUtils.getConnection();
		if (c == null) {
			logger.log(Level.SEVERE, "Connection Failed");
		} else {
			logger.log(Level.INFO, "Connection Established");
		}
	}

	public boolean addParkingLot(ParkingLot p) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.selectParkingLotByName);
		stm.setString(1, p.getName());
		ResultSet rs = stm.executeQuery();
		if (rs.next()) // parkingLot exists
		{
			System.out.println("rs.next(): " + rs.getString(2));
			System.out.println("BAD, USER ALREADY EXISTS");
			return false;
		}

		else {
			PreparedStatement stm1 = c.prepareStatement(sqlStatements.Allstatements.addNewParkingLot);

			stm1.setString(1, p.getName());
			stm1.setString(2, p.getLocation());
			stm1.setBoolean(3, p.isActive());
			stm1.setBoolean(4, p.isFull());
			stm1.setString(5, p.getManager());
			stm1.setInt(6, p.getWidth());

			stm1.executeUpdate();
			System.out.println("added to database");
		}
		return true;
	}

	@Override
	public boolean deleteParkingLot(String nameOfParkingLot) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.selectParkingLotByName);
		stm.setString(1, nameOfParkingLot);
		ResultSet rs = stm.executeQuery();
		if (!rs.next()) {
			System.out.println("Parking lot does not exist,try a different parking lot");
			return false;
		}
		PreparedStatement stm1 = c.prepareStatement(sqlStatements.Allstatements.deleteParkingLot);
		stm1.setString(1, nameOfParkingLot);
		stm1.executeUpdate();
		System.out.println("Parking lot deleted successfully");
		return true;
	}

	public int getParkingIdLotByName(String name) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.selectParkingLotByName);
		stm.setString(1, name);
		ResultSet rs = stm.executeQuery();
		int id = 0;
		while (rs.next()) {
			id = rs.getInt("parkingLotId");
			return id;
		}
		return 0;
	}

	public ArrayList<ParkingLot> GetAllParkingLots() throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getAllParkingLots);
		ArrayList<ParkingLot> allParkingLots = new ArrayList<ParkingLot>();
		ParkingLot p = null;
		ResultSet rs = stm.executeQuery();
		while (rs.next()) {
			p = new ParkingLot(rs.getString("name"), rs.getString("location"), rs.getBoolean("isActive"),
					rs.getBoolean("isFull"), rs.getString("manager"), rs.getInt("width"));
			allParkingLots.add(p);
		}
		return allParkingLots;
	}

	public boolean checkIfCustomerExistsById(String customerId) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.selectCustomerById);
		stm.setString(1, customerId);
		ResultSet rs = stm.executeQuery();
		if (!rs.next()) {
			System.out.println("User does not exist in CPS");
			return false;
		}
		return true;
	}

	public boolean addCustomer(Customer customer) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.selectCustomerById);
		stm.setString(1, customer.getId());
		ResultSet rs = stm.executeQuery();
		if (rs.next()) {
			System.out.println("User already exists in CPS");
			return false;
		}
		stm = c.prepareStatement(sqlStatements.Allstatements.addNewCustomer);
		stm.setString(1, customer.getId());
		stm.setString(2, customer.getEmail());
		stm.executeUpdate();
		System.out.println("User Added Successfully");
		return true;
	}

	public ArrayList<Customer> getAllCustomers() throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getAllCustomers);
		ArrayList<Customer> allCustomers = new ArrayList<Customer>();
		Customer tmp = null;
		ResultSet rs = stm.executeQuery();
		while (rs.next()) {
			tmp = new Customer(rs.getString("customerId"), rs.getString("email"));
			allCustomers.add(tmp);
		}
		return allCustomers;

	}

	public boolean addOrder(Order o) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.checkIfOrderExists);
		stm.setString(1, o.getParkingLot());
		stm.setString(2, o.getVehicleNum());
		stm.setString(3, o.getCustomerId());
		ResultSet res = stm.executeQuery();
		if (res.next()) {
			System.out.println("Order already exists,cannot be added");
			return false;
		}
		stm = c.prepareStatement(sqlStatements.Allstatements.addNewOrder);
		stm.setString(1, o.getType().name());
		stm.setString(2, o.getParkingLot());
		stm.setString(3, o.getArrivingAt());
		stm.setString(4, o.getLeavingAt());
		stm.setString(5, o.getCustomerId());
		stm.setString(6, o.getVehicleNum());
		stm.executeUpdate();
		System.out.println("Order added successfully");
		return true;
	}

	public boolean addComplaint(Complaint complaint) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.addNewComplaint);
		stm.setString(1, complaint.getParkingLot());
		stm.setString(2, complaint.getCustomerId());
		stm.setString(3, complaint.getSubmissionDate());
		stm.setBoolean(4, false);
		stm.setString(5, complaint.getComplaintText());
		stm.executeUpdate();
		System.out.println("Complaint added successfully");
		return true;
	}

	public ArrayList<Order> getAllOrders() throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getAllOrders);
		ArrayList<Order> allOrders = new ArrayList<Order>();
		Order o = null;
		ResultSet res = stm.executeQuery();
		while (res.next()) {
			o = new Order(res.getInt("orderID"), OrderType.uponArrivalOrder, res.getString("parkingLot"),
					res.getString("arrivingAt"), res.getString("leavingAt"), res.getString("customerId"),
					res.getString("vehicleNumber"));
			allOrders.add(o);
		}
		return allOrders;
	}

	public ArrayList<Order> getAllOrdersByCustomerId(String id) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getAlOrdersByCustomerId);
		stm.setString(1, id);
		ResultSet res = stm.executeQuery();
		ArrayList<Order> allOrders = new ArrayList<Order>();
		Order o = null;
		while (res.next()) {
			o = new Order(res.getInt("orderID"), OrderType.uponArrivalOrder, res.getString("parkingLot"),
					res.getString("arrivingAt"), res.getString("leavingAt"), res.getString("customerId"),
					res.getString("vehicleNumber"));
			allOrders.add(o);
		}

		return allOrders;
	}

	public ArrayList<Order> getAllOrdersByVehicleNumber(String vehicleNum) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getAllOrdersByVehicleNum);
		stm.setString(1, vehicleNum);
		ResultSet res = stm.executeQuery();
		ArrayList<Order> allOrders = new ArrayList<Order>();
		Order o = null;
		while (res.next()) {
			o = new Order(res.getInt("orderID"), OrderType.uponArrivalOrder, res.getString("parkingLot"),
					res.getString("arrivingAt"), res.getString("leavingAt"), res.getString("customerId"),
					res.getString("vehicleNumber"));
			allOrders.add(o);
		}

		return allOrders;
	}

	public boolean addVehicle(Vehicle v) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.selectVehicleByVehicleNum);
		stm.setString(1, v.getVehicleNumber());
		ResultSet res = stm.executeQuery();
		if (res.next()) {
			System.out.println("Vehicle already registered in data base");
			return false;
		}
		stm = c.prepareStatement(sqlStatements.Allstatements.addNewVehicle);
		stm.setString(1, v.getVehicleNumber());
		stm.setString(2, v.getCustomerId());

		stm.executeUpdate();
		System.out.println("Vehicle added successfully to data base");
		return true;
	}

	public ArrayList<Vehicle> getAllVehicles() throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getAllVehicles);
		ArrayList<Vehicle> allVehicles = new ArrayList<>();
		ResultSet res = stm.executeQuery();
		Vehicle v = null;
		while (res.next()) {
			v = new Vehicle(res.getString("vehicleNumber"), res.getString("customerID"), res.getInt("row"),
					res.getInt("column"), res.getInt("width"));
			allVehicles.add(v);
		}
		return allVehicles;
	}

	public boolean addFullSubscription(FullSubscription fullSubscription) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.addFullSubscription);
		stm.setString(1, fullSubscription.getCustomerId());
		stm.setString(2, fullSubscription.getSubsciptionId());
		stm.setString(3, fullSubscription.getVehicleNumber());
		stm.setDate(4, fullSubscription.getStartDate());
		stm.setDate(5, fullSubscription.getStartDate());
		//stm.setDate(5, fullSubscription.getStartDate());
		stm.executeUpdate();
		System.out.println("Full Subscription Added Successfully");
		return true;

	}

	public boolean addRegularSubscription(RegularSubscription RegularSubscription) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.addRegularSubscription);
		stm.setString(1, RegularSubscription.getCustomerId());
		//stm.setString(2, RegularSubscription.getSubsciptionId());
		stm.setString(3, RegularSubscription.getVehicleNumber());
		stm.setDate(5, RegularSubscription.getStartDate());
		stm.setString(6, RegularSubscription.getParkingLot());
		stm.setString(8, RegularSubscription.getLeavingAt());
		System.out.println("Regular Subscription Added Successfully");
		return true;
	}
	

	public boolean checkIfVehicleExistsByNumber(String vehicleNum) throws SQLException {
		PreparedStatement stm=c.prepareStatement(sqlStatements.Allstatements.checkIfVehicleExists);
		stm.setString(1, vehicleNum);
		ResultSet res=stm.executeQuery();
		if(res.next()) {
			System.out.println("Vehicle exists in CPS");
			return true;
		}
		return false;
	}
	
	
}