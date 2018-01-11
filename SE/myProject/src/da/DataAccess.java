package da;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import allClasses.Complaint;
import allClasses.Customer;
import allClasses.FullSubscription;
import allClasses.OneCarBusinessSubscription;
import allClasses.OneCarRegularSubscription;
import allClasses.Order;
import allClasses.Order.OrderType;
import allClasses.Subscription.subscriptionType;
import allClasses.ParkingLot;
import allClasses.Subscription;
import allClasses.Vehicle;
import allClasses.Employee.employeeType;
import da.DataInterface;
import sqlStatements.Allstatements;

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
		stm.setInt(3, customer.getCredit());
		stm.setBoolean(4, false);
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
		stm.setString(3, o.getArrivingDate());
		stm.setString(4, o.getArrivingAt());
		stm.setString(5, o.getLeavingDate());
		stm.setString(6, o.getLeavingAt());
		stm.setString(7, o.getCustomerId());
		stm.setString(8, o.getVehicleNum());
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
			if (res.getBoolean("canceled") == false) {
				OrderType type = (OrderType) res.getObject("type");
				o = new Order(res.getInt("orderID"), type, res.getString("parkingLot"), res.getString("arrivingDate"),
						res.getString("leavingDate"), res.getString("arrivingAt"), res.getString("leavingAt"),
						res.getString("customerId"), res.getString("vehicleNumber"), res.getBoolean("arrivingLate"),
						res.getBoolean("leavingLate"), res.getBoolean("canceled"));
				allOrders.add(o);
			}
		}
		return allOrders;
	}

	public ArrayList<Order> getAllOrdersByCustomerId(String id) throws SQLException {
		boolean result = checkIfCustomerExistsById(id);
		if (!result) {
			return null;
		}
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getAlOrdersByCustomerId);
		stm.setString(1, id);
		ResultSet res = stm.executeQuery();
		ArrayList<Order> allOrders = new ArrayList<Order>();
		Order o = null;
		while (res.next()) {
			if (res.getBoolean("canceled") == false) {
				OrderType type = (OrderType) res.getObject("type");
				o = new Order(res.getInt("orderID"), type, res.getString("parkingLot"), res.getString("arrivingDate"),
						res.getString("leavingDate"), res.getString("arrivingAt"), res.getString("leavingAt"),
						res.getString("customerId"), res.getString("vehicleNumber"), res.getBoolean("arrivingLate"),
						res.getBoolean("leavingLate"), res.getBoolean("canceled"));
				allOrders.add(o);
			}
		}
		return allOrders;
	}

	public ArrayList<Order> getAllOrdersByVehicleNumber(String vehicleNum) throws SQLException {
		boolean result = checkIfVehicleExistsByNumber(vehicleNum);
		if (!result) {
			return null;
		}
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getAllOrdersByVehicleNum);
		stm.setString(1, vehicleNum);
		ResultSet res = stm.executeQuery();
		ArrayList<Order> allOrders = new ArrayList<Order>();
		Order o = null;
		while (res.next()) {
			if (res.getBoolean("canceled") == false) {
				OrderType type = (OrderType) res.getObject("type");
				o = new Order(res.getInt("orderID"), type, res.getString("parkingLot"), res.getString("arrivingDate"),
						res.getString("leavingDate"), res.getString("arrivingAt"), res.getString("leavingAt"),
						res.getString("customerId"), res.getString("vehicleNumber"), res.getBoolean("arrivingLate"),
						res.getBoolean("leavingLate"), res.getBoolean("canceled"));
				allOrders.add(o);
			}
		}
		return allOrders;
	}

	public ArrayList<Subscription> getAllSubsByCustomerId(String customerId) throws SQLException {// TODO: this
		boolean result = checkIfCustomerExistsById(customerId);
		if (!result)
			return null;
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getAllFullSubsByCustomerId);
		stm.setString(1, customerId);
		ResultSet res = stm.executeQuery();
		ArrayList<Subscription> subs = new ArrayList<Subscription>();
		FullSubscription s = null;
		while (res.next()) {
			s = new FullSubscription(customerId, res.getString("subscriptionId"), res.getString("vehicleNumber"),
					res.getDate("startingDate"), res.getString("email"), res.getDate("arrivedSince"),
					subscriptionType.fullSubscription);
			subs.add(s);
		}
		stm = c.prepareStatement(sqlStatements.Allstatements.getAllRegularSubsByCustomerId);
		stm.setString(1, customerId);
		res = stm.executeQuery();
		while (res.next()) {
			Object type = res.getObject("type");
			if (type.equals("oneCar")) {
				OneCarRegularSubscription oc = new OneCarRegularSubscription(customerId,
						res.getString("subscriptionId"), res.getString("vehicleNumber"), res.getDate("startDate"),
						res.getString("email"), subscriptionType.oneCarRegularSubscription, res.getString("parkingLot"),
						res.getString("leavingAt"));
				subs.add(oc);
			}
			if (type.equals("business")) {
				OneCarBusinessSubscription bs = new OneCarBusinessSubscription(customerId,
						res.getString("subscriptionId"), res.getString("vehicleNumber"), res.getDate("startDate"),
						res.getString("email"), subscriptionType.regularBusinessSubscription,
						res.getString("parkingLot"), res.getString("leavingAt"));
				subs.add(bs);
			}
		}
		return subs;
	}

	public boolean addVehicle(Vehicle v) throws SQLException {
		boolean res = checkIfVehicleExistsByNumber(v.getVehicleNumber());
		if (res)
			return false;
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.addNewVehicle);
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
		// stm.setDate(5, fullSubscription.getStartDate());
		stm.executeUpdate();
		System.out.println("Full Subscription Added Successfully");
		return true;

	}

	public boolean addOneCarRegularSubscription(OneCarRegularSubscription oneCarRegularSubscription)
			throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.addOneCarRegularSubscription);
		stm.setString(1, oneCarRegularSubscription.getCustomerId());
		stm.setString(2, oneCarRegularSubscription.getVehicleNumber());
		stm.setDate(3, oneCarRegularSubscription.getStartDate());
		stm.setString(4, oneCarRegularSubscription.getParkingLot());
		stm.setString(5, oneCarRegularSubscription.getLeavingAt());
		stm.setString(6, oneCarRegularSubscription.getEmail());
		stm.executeUpdate();
		System.out.println("One Car Regular Subscription Added Successfully");
		return true;
	}

	public boolean checkIfVehicleExistsByNumber(String vehicleNum) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.checkIfVehicleExists);
		stm.setString(1, vehicleNum);
		ResultSet res = stm.executeQuery();
		if (res.next()) {
			System.out.println("Vehicle exists in CPS");
			return true;
		}
		return false;
	}

	public ArrayList<Complaint> getAllComplaints() throws SQLException {
		ArrayList<Complaint> complaints = new ArrayList<Complaint>();
		PreparedStatement statement = c.prepareStatement(sqlStatements.Allstatements.getAllComplaints);
		Complaint complaint = null;
		ResultSet res = statement.executeQuery();
		while (res.next()) {
			complaint = new Complaint(res.getString("parkingLot"), res.getString("customerId"), res.getString("text"));
			complaint.setSubmissionDate(res.getString("submissionDate"));
			complaint.setChecked(res.getBoolean("isChecked"));
			complaints.add(complaint);
		}
		return complaints;
	}

	public boolean checkIfEmployeeExists(String name, String password) throws SQLException {
		PreparedStatement statement = c.prepareStatement(sqlStatements.Allstatements.checkIfEmployeeExists);
		statement.setString(1, name);
		statement.setString(2, password);
		ResultSet res = statement.executeQuery();
		return res.next();
	}

	public boolean addBuisnessRegularSubscription(OneCarBusinessSubscription sub) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.addBusinessRegularSubscription);
		stm.setString(1, sub.getCustomerId());
		stm.setString(2, sub.getSubsciptionId());
		stm.setString(3, sub.getVehicleNumber());
		stm.setDate(4, sub.getStartDate());
		stm.setString(5, sub.getParkingLot());
		stm.setString(6, sub.getLeavingAt());
		stm.setString(7, sub.getEmail());
		stm.executeUpdate();
		System.out.println("Business Regular subscription added successfully!");
		return true;
	}

	public Order checkIfOrderExistsByAllParameters(String customerId, String vehicle, String arrivingDate,
			String arrivingAt, String leavingDate, String leavingAt, String parkingLot) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.checkIfOrderExistsByAllParameters);
		stm.setString(1, parkingLot);
		stm.setString(2, vehicle);
		stm.setString(3, customerId);
		stm.setString(4, arrivingDate);
		stm.setString(5, arrivingAt);
		stm.setString(6, leavingDate);
		stm.setString(7, leavingAt);
		ResultSet res = stm.executeQuery();
		Order o = null;
		if (res.next()) {
			OrderType type = (OrderType) res.getObject("type");
			o = new Order(res.getInt("orderID"), type, parkingLot, arrivingDate, leavingDate, arrivingDate, leavingDate,
					customerId, vehicle, res.getBoolean("arrivingLate"), res.getBoolean("leavingLate"),
					res.getBoolean("canceled"));

		}
		return o;

	}

	public void cancelOrder(Order order) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.cancelOrder);
		stm.setString(1, order.getParkingLot());
		stm.setString(2, order.getVehicleNum());
		stm.setString(3, order.getCustomerId());
		stm.setString(4, order.getArrivingDate());
		stm.setString(5, order.getArrivingAt());
		stm.setString(6, order.getLeavingDate());
		stm.setString(7, order.getLeavingAt());
		stm.executeUpdate();
		System.out.println("Order canceled");

	}

	public void setupParkingLot(String parkingLot, int width) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.setupParkingLot);
		stm.setInt(1, width);
		stm.setString(2, parkingLot);
		stm.executeUpdate();
		System.out.println("Setup of parking lot " + parkingLot + " is finished");
	}

	public void logOutEmployee(String name) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.logOutEmployee);
		stm.setString(1, name);
		stm.executeUpdate();
		System.out.println("Employee logged out");
	}

	public void logOutCustomer(String customerId) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.logOutCustomer);
		stm.setString(1, customerId);
		stm.executeUpdate();
		System.out.println("Customer logged out");
	}

	public void loginCustomer(String customerId) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.logInCustomer);
		stm.setString(1, customerId);
		stm.executeUpdate();
		System.out.println("Customer logged in");

	}

	public void loginEmployee(String name, String password) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.logInEmployee);
		stm.setString(1, name);
		stm.setString(2, password);
		stm.executeUpdate();
		System.out.println("Employee logged in");

	}

	public String getEmployeeType(String name) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getEmployeeByName);
		stm.setString(1, name);
		String type1 = null;
		ResultSet res = stm.executeQuery();
		while (res.next()) {
			Object type = res.getObject("type");

			if (type.equals("companyManager")) {
				type1 = "companyManager";
			} else if (type.equals("parkingLotManager")) {
				type1 = "parkingLotManager";
			} else if (type.equals("customerServiceEmployee")) {
				type1 = "customerServiceEmployee";
			} else if (type.equals("parkingLotEmployee")) {
				type1 = "parkingLotEmployee";
			}
		}
		return type1;
	}
}