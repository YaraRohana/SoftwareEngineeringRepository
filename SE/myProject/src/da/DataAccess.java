package da;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.crypto.KeySelector.Purpose;

import org.eclipse.jdt.internal.compiler.ast.ReturnStatement;

import allClasses.Complaint;
import allClasses.Customer;
import allClasses.Email;
import allClasses.FullSubscription;
import allClasses.OneCarBusinessSubscription;
import allClasses.OneCarRegularSubscription;
import allClasses.Order;
import allClasses.Order.OrderType;
import allClasses.Subscription.subscriptionType;
import allClasses.ParkingLot;
import allClasses.ParkingSpot;
import allClasses.Subscription;
import allClasses.Vehicle;
import allClasses.Employee.employeeType;
import allClasses.FaultedParkingSpotHistory;
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

	/**
	 * return type: ParkingLot gets the parking lot from the data base with all of
	 * it's information
	 */
	public ParkingLot getParkingLotByName(String name) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.selectParkingLotByName);
		stm.setString(1, name);
		ResultSet rs = stm.executeQuery();
		ParkingLot pl = null;
		while (rs.next()) {
			pl = new ParkingLot(name, rs.getString("location"), rs.getBoolean("isActive"), rs.getBoolean("isFull"),
					rs.getString("manager"), rs.getInt("width"));
		}
		return pl;
	}

	/**
	 * return tye: an arrayList of parkingLots gets all the parking lots from the
	 * data base with all of their corresponding information
	 */
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

	/**
	 * 
	 * @param customerId wanted customer
	 * @return boolean value cheking if the customer exists in the CPS,every
	 *         customer is identified by their id
	 * @throws SQLException
	 *             gets information from DB therefore could be an exception
	 */
	public boolean checkIfCustomerExistsById(String customerId) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.selectCustomerById);
		stm.setString(1, customerId);
		ResultSet rs = stm.executeQuery();
		if (rs.next()) {
			return true;
		}
		System.out.println("User does not exist in CPS");
		return false;
	}

	/**
	 * 
	 * @param customerId wanted customer
	 * @return boolean value if the customer is connected to the CPS as registered
	 *         in data base
	 * @throws SQLException
	 *             gets information from DB therefore could be an exception
	 */
	public boolean getCustomerConnectionStatus(String customerId) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.selectCustomerById);
		stm.setString(1, customerId);
		ResultSet rs = stm.executeQuery();
		boolean isConnected = false;
		while (rs.next()) {
			isConnected = rs.getBoolean("isConnected");
		}
		return isConnected;
	}

	/**
	 * 
	 * @param name wanted employee
	 * @return boolean value if the employee is connected to the CPS as registered
	 *         in data base
	 * @throws SQLException
	 *             gets information from DB therefore could be an exception
	 */
	public boolean getEmployeeConnectionStatus(String name) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getEmployeeByName);
		stm.setString(1, name);
		ResultSet rs = stm.executeQuery();
		boolean isConnected = false;
		while (rs.next()) {
			isConnected = rs.getBoolean("isConnected");
		}
		return isConnected;
	}

	/**
	 * adds a new customer to the table customers in the data base, the customer is
	 * added with all of it's information: customerId,email,credit and connection
	 * status which is set originally to zero,returns true if the customer was
	 * added,false if the customer already exists(customerIDs are unique)
	 */
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

	/**
	 * returns an arrayList of Customers that are registered in the CPS data base
	 * with all of their corresponding info
	 */
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

	/**
	 * 
	 * @param o
	 *            gets an order with all of it's info adds an order to the CPS
	 *            database , in the orders table an order is added by: customer
	 *            Id,vehicle Number,arriving and leaving dates, arriving and leaving
	 *            times, and type of order
	 * @return boolean result suggesting if the order already exists(false) or if
	 *         the order is added successfully
	 * @throws SQLException
	 *             gets information from DB therefore could be an exception
	 * @throws ParseException
	 *             may not be able to parse if null
	 */
	public boolean addOrder(Order o) throws SQLException, ParseException {
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

	/**
	 * 
	 * @param complaint
	 *            with all of the information needed for adding it to the CPS data
	 *            base: the complaint is added by the parameters: customer
	 *            id,parking lot and a text stating what the complaint is about
	 * @return boolean
	 * @throws SQLException
	 *             gets information from DB therefore could be an exception
	 */
	public boolean addComplaint(Complaint complaint) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.addNewComplaint);
		stm.setString(1, complaint.getParkingLot());
		stm.setString(2, complaint.getCustomerId());
		stm.setString(3, complaint.getSubmissionDate());
		stm.setString(4, complaint.getComplaintText());
		stm.executeUpdate();
		System.out.println("Complaint added successfully");
		return true;
	}

	/**
	 * gets all orders from CPS data base
	 */
	public ArrayList<Order> getAllOrders() throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getAllOrders);
		ArrayList<Order> allOrders = new ArrayList<Order>();
		Order o = null;
		ResultSet res = stm.executeQuery();
		while (res.next()) {
			if (res.getBoolean("canceled") == false) {
				String type1 = res.getString("type");
				OrderType type = OrderType.valueOf(type1);
				o = new Order(res.getInt("orderID"), type, res.getString("parkingLot"), res.getString("arrivingDate"),
						res.getString("leavingDate"), res.getString("arrivingAt"), res.getString("leavingAt"),
						res.getString("customerId"), res.getString("vehicleNumber"), res.getBoolean("arrivingLate"),
						res.getBoolean("leavingLate"), res.getBoolean("canceled"));
				allOrders.add(o);
			}
		}
		return allOrders;
	}

	/**
	 * gets all orders from CPS for a customer id
	 */
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
				String type1 = res.getString("type");
				OrderType type = OrderType.valueOf(type1);
				o = new Order(res.getInt("orderID"), type, res.getString("parkingLot"), res.getString("arrivingDate"),
						res.getString("leavingDate"), res.getString("arrivingAt"), res.getString("leavingAt"),
						res.getString("customerId"), res.getString("vehicleNumber"), res.getBoolean("arrivingLate"),
						res.getBoolean("leavingLate"), res.getBoolean("canceled"));
				allOrders.add(o);
			}
		}
		return allOrders;
	}

	/**
	 * gets all orders for a certain vehicle number
	 */
	public ArrayList<Order> getAllOrdersByVehicleNumber(String vehicleNum) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getAllOrdersByVehicleNum);
		stm.setString(1, vehicleNum);
		ResultSet res = stm.executeQuery();
		ArrayList<Order> allOrders = new ArrayList<Order>();
		Order o = null;
		while (res.next()) {
			if (res.getBoolean("canceled") == false) {
				String type1 = res.getString("type");
				OrderType type = OrderType.valueOf(type1);
				o = new Order(res.getInt("orderID"), type, res.getString("parkingLot"), res.getString("arrivingDate"),
						res.getString("leavingDate"), res.getString("arrivingAt"), res.getString("leavingAt"),
						res.getString("customerId"), res.getString("vehicleNumber"), res.getBoolean("arrivingLate"),
						res.getBoolean("leavingLate"), res.getBoolean("canceled"));
				allOrders.add(o);
			}
		}
		return allOrders;
	}

	/**
	 * gets all subscriptions made by a certain customer,both full and regular
	 * subscriptions
	 *
	 */
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
			// System.out.println(s.toString());
		}
		stm = c.prepareStatement(sqlStatements.Allstatements.getAllRegularSubsByCustomerId);
		stm.setString(1, customerId);
		res = stm.executeQuery();
		while (res.next()) {
			String type1 = res.getString("type");
			// OrderType type = OrderType.valueOf(type1);
			if (type1.equals("oneCar")) {
				OneCarRegularSubscription oc = new OneCarRegularSubscription(customerId,
						res.getString("subscriptionId"), res.getString("vehicleNumber"), res.getDate("startDate"),
						res.getString("email"), subscriptionType.oneCarRegularSubscription, res.getString("parkingLot"),
						res.getString("leavingAt"));
				subs.add(oc);
				// System.out.println(oc.toString());
			}
			if (type1.equals("business")) {
				OneCarBusinessSubscription bs = new OneCarBusinessSubscription(customerId,
						res.getString("subscriptionId"), res.getString("vehicleNumber"), res.getDate("startDate"),
						res.getString("email"), subscriptionType.regularBusinessSubscription,
						res.getString("parkingLot"), res.getString("leavingAt"));
				subs.add(bs);
			}
		}
		return subs;
	}

	/**
	 * adds a vehicle to the CPS data base, a vehicle containts the vehicle number
	 * and the id of the customer who owns it vehicle insertion is done when adding
	 * an order(pre order,upon arrival) or a subscription(full,regular)
	 */
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

	/**
	 * gets all vehicles in the CPS data base
	 */
	public ArrayList<Vehicle> getAllVehicles() throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getAllVehicles);
		ArrayList<Vehicle> allVehicles = new ArrayList<>();
		ResultSet res = stm.executeQuery();
		Vehicle v = null;
		while (res.next()) {
			v = new Vehicle(res.getString("vehicleNumber"), res.getString("customerID"));
			allVehicles.add(v);
		}
		return allVehicles;
	}

	/**
	 * adds a full subscription to the full subscriptions table in the CPS data base
	 * 
	 * @param fullSubscription
	 *            - an object that contains info about the subscription: customer
	 *            id,vehicle number,starting date
	 * @return success upon adding
	 * @throws SQLException
	 *             gets information from DB therefore could be an exception
	 * @throws ParseException
	 *             may not parse if null
	 */
	public boolean addFullSubscription(FullSubscription fullSubscription) throws SQLException, ParseException {
		// System.out.println("it's true");
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.addFullSubscription);
		stm.setString(1, fullSubscription.getCustomerId());
		stm.setString(2, fullSubscription.getSubsciptionId());
		stm.setString(3, fullSubscription.getVehicleNumber());
		stm.setDate(4, fullSubscription.getStartDate());
		stm.setDate(5, fullSubscription.getStartDate());
		stm.setString(6, fullSubscription.getEmail());
		// stm.setDate(5, fullSubscription.getStartDate());
		stm.executeUpdate();
		System.out.println("Full Subscription Added Successfully");
		return true;

	}

	/**
	 * adds a regular subscription with one vehicle, meaning it's not a "business
	 * client" adds a regular subscription to the regular subscriptions table with
	 * the information of the customer
	 * 
	 * @param oneCarRegularSubscription
	 *            contains the customer id,vehicle number,starting date and other
	 *            important information
	 * @return boolean result upon success
	 * @throws SQLException
	 *             gets information from DB therefore could be an exception
	 * @throws ParseException
	 *             may not parse if null
	 */
	public boolean addOneCarRegularSubscription(OneCarRegularSubscription oneCarRegularSubscription)
			throws SQLException, ParseException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.addOneCarRegularSubscription);
		stm.setString(1, oneCarRegularSubscription.getCustomerId());
		stm.setString(2, oneCarRegularSubscription.getSubsciptionId());
		stm.setString(3, oneCarRegularSubscription.getVehicleNumber());
		stm.setDate(4, oneCarRegularSubscription.getStartDate());
		stm.setString(5, oneCarRegularSubscription.getParkingLot());
		stm.setString(6, oneCarRegularSubscription.getLeavingAt());
		stm.setString(7, oneCarRegularSubscription.getEmail());
		stm.executeUpdate();
		System.out.println("One Car Regular Subscription Added Successfully");
		return true;
	}

	/**
	 * gets a vehicle number and returns if it exists in the table of vehicles in
	 * the CPS database
	 */
	public boolean checkIfVehicleExistsByNumber(String vehicleNum) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.selectVehicleByVehicleNum);
		stm.setString(1, vehicleNum);
		ResultSet res = stm.executeQuery();
		if (res.next()) {
			return true;
		}
		return false;
	}

	/**
	 * gets all complaints from the complaints table in the CPS database, the table
	 * contains the following info about a previously filed complaint: customer
	 * id,parking lot,text of the complaint,returns an arrayList of complaints
	 */
	public ArrayList<Complaint> getAllComplaints() throws SQLException {
		ArrayList<Complaint> complaints = new ArrayList<Complaint>();
		PreparedStatement statement = c.prepareStatement(sqlStatements.Allstatements.getAllComplaints);
		Complaint complaint = null;
		ResultSet res = statement.executeQuery();
		while (res.next()) {
			if (res.getBoolean("isChecked") == false) {
				complaint = new Complaint(res.getString("parkingLot"), res.getString("customerId"),
						res.getString("text"));
				complaint.setSubmissionDate(res.getString("submissionDate"));
				complaint.setChecked(res.getBoolean("isChecked"));
				complaints.add(complaint);
			}
		}
		return complaints;
	}

	/**
	 * returns a boolean indicating if a customer exists in CPS
	 */
	public boolean checkIfEmployeeExists(String name, String password) throws SQLException {
		PreparedStatement statement = c.prepareStatement(sqlStatements.Allstatements.checkIfEmployeeExists);
		statement.setString(1, name);
		statement.setString(2, password);
		ResultSet res = statement.executeQuery();
		return res.next();
	}

	/**
	 * adds a one business regular subscription,
	 *
	 * @param sub
	 *            - an object containing all the relevant information for a
	 *            subscription to be made
	 * @return boolean value upon success in adding it to the CPS data base
	 * @throws SQLException
	 *             gets information from DB therefore could be an exception
	 * @throws ParseException
	 *             may not parse if null
	 */
	public boolean addBuisnessRegularSubscription(OneCarBusinessSubscription sub) throws SQLException, ParseException {
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

	/**
	 * checks if an order exists in the CPS orders table, since the same customer
	 * can make multiple orders with the same vehicle or with different vehicles ,
	 * the customer id is not a unique field,neither is the vehicle number,
	 * therefore each time a new order is being added, we check if the exact same
	 * order already exists
	 */
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
		// stm.setString(8, type1);
		ResultSet res = stm.executeQuery();
		Order o = null;
		if (res.next()) {
			String type1 = res.getString("type");
			OrderType type = OrderType.valueOf(type1);
			o = new Order(res.getInt("orderID"), type, parkingLot, arrivingDate, leavingDate, arrivingAt, leavingAt,
					customerId, vehicle, res.getBoolean("arrivingLate"), res.getBoolean("leavingLate"),
					res.getBoolean("canceled"));

		}
		return o;

	}

	/**
	 * cancels a given order, searches for it in the CPS data base and turns the
	 * `canceled` bit in it to 1
	 * 
	 **/
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

	/**
	 * setup parking lot function for the parking lot employee, the input is the
	 * width of the parking lot, updates the parking lots table
	 */
	public void setupParkingLot(String parkingLot, int width) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.setupParkingLot);
		stm.setInt(1, width);
		stm.setString(2, parkingLot);
		stm.executeUpdate();
		System.out.println("Setup of parking lot " + parkingLot + " is finished");
	}

	/**
	 * every employee has a boolean connection status in the CPS employees table,
	 * this function turns off this bit upon logout
	 */
	public void logOutEmployee(String name) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.logOutEmployee);
		stm.setString(1, name);
		stm.executeUpdate();
		System.out.println("Employee logged out");
	}

	/**
	 * every customer has a boolean connection status in the CPS customers table,
	 * this function turns off this bit upon logout
	 */
	public void logOutCustomer(String customerId) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.logOutCustomer);
		stm.setString(1, customerId);
		stm.executeUpdate();
		System.out.println("Customer logged out");
	}

	/**
	 * every customer has a boolean connection status in the CPS customers table,
	 * this function turns on this bit upon login
	 */
	public void loginCustomer(String customerId) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.logInCustomer);
		stm.setString(1, customerId);
		stm.executeUpdate();
		System.out.println("Customer logged in");

	}

	/**
	 * every employee has a boolean connection status in the CPS employees table,
	 * this function turns on this bit upon login
	 */
	public void loginEmployee(String name, String password) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.logInEmployee);
		stm.setString(1, name);
		stm.setString(2, password);
		stm.executeUpdate();
		System.out.println("Employee logged in");

	}

	/**
	 * every employee is assigned a type in the CPS employees table: parking lot
	 * manager,parking lot employee, customer service employee,this function returns
	 * the type of the employee
	 */
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

	/**
	 * returns the parking lot where the employee is employed
	 */
	public String getEmployeeParkingLot(String name) throws SQLException {
		String employeeType = getEmployeeType(name);
		String parkingLot = null;
		if (employeeType.equals("parkingLotEmployee") || employeeType.equals("companyManager")
				|| employeeType.equals("parkingLotManager")) {
			PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getEmployeeByName);
			stm.setString(1, name);
			ResultSet res = stm.executeQuery();
			while (res.next()) {
				parkingLot = res.getString("parkingLot");
			}
		}
		return parkingLot;
	}

	/**
	 * upon filing a complaint, the employee has to get back to the customer with a
	 * reply(and optionally a compensation), this functions handles complaints
	 * replies
	 */
	public void updateCompensationForCustomer(String customerId, int compensation) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.selectCustomerById);
		stm.setString(1, customerId);
		ResultSet res = stm.executeQuery();
		int credit = 0;
		while (res.next()) {
			credit = res.getInt("credit");
			credit += compensation;
		}
		stm = c.prepareStatement(sqlStatements.Allstatements.updateCreditByCustomerId);
		stm.setInt(1, credit);
		stm.setString(2, customerId);
		stm.executeUpdate();
	}

	/**
	 * gets an order and calculated the cost of the order for the customer,depending
	 * on the type(pre order,upon arrival) and the time and date of arrival and
	 * leaving
	 */
	@SuppressWarnings("deprecation")
	public double getOrderCost(String arrivingAt, String leavingAt, String arrivingDate, String leavingDate,
			OrderType type) throws SQLException, Exception {
		double preOrderPrice = 0.0, uponArrivalPrice = 0.0;
		DateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat fullDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		java.util.Date Arriving = dateFormat1.parse(arrivingDate);
		java.util.Date Leaving = dateFormat1.parse(leavingDate);
		String arriving = null;
		String leaving = null;
		arriving = arrivingDate + " " + arrivingAt;
		leaving = leavingDate + " " + leavingAt;
		Arriving = fullDateFormat.parse(arriving);
		Leaving = fullDateFormat.parse(leaving);
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getPrices);
		// stm.setString(1, parkingLot);
		ResultSet res = stm.executeQuery();
		while (res.next()) {
			preOrderPrice = Integer.parseInt(res.getString("preOrderPrice"));
			uponArrivalPrice = Integer.parseInt(res.getString("uponArrivalPrice"));
		}

		long diff = Leaving.getTime() - Arriving.getTime();
		System.out.println("**************************" + diff);
		double diffMinutes = diff / (60 * 1000) % 60;
		int diffHours = (int) (diff / (60 * 60 * 1000));
		System.out.println("diff hours from get cost***********" + diffHours);
		if (diffMinutes != 0)
			diffHours++;
		System.out.println("diff Hours from get order cost: " + diffHours);
		if (type == OrderType.preOrder) {
			System.out.println("price is" + preOrderPrice * (diffHours));
			return ((preOrderPrice * (diffHours)));
		}
		if (type == OrderType.uponArrivalOrder) {
			return ((uponArrivalPrice * (diffHours)));
		}

		return -1;
	}

	/**
	 * gets the cost of the full subscription
	 */
	public int getFullSubscriptionCost() throws NumberFormatException, SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getPrices);
		// stm.setString(1, parkingLot);
		ResultSet res = stm.executeQuery();
		int price1 = 0, price2 = 0;
		while (res.next()) {
			price1 = Integer.parseInt(res.getString("preOrderPrice"));
			price2 = Integer.parseInt(res.getString("fullSubscriptionPrice"));
		}
		return price1 * price2;
	}

	/**
	 * gets the regular subscription price for one vehicle
	 */
	public int getOneCarRegularSubscriptionCost() throws NumberFormatException, SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getPrices);
		// stm.setString(1, parkingLot);
		ResultSet res = stm.executeQuery();
		int price1 = 0, price2 = 0;
		while (res.next()) {
			price1 = Integer.parseInt(res.getString("preOrderPrice"));
			price2 = Integer.parseInt(res.getString("regularSubsForOneCar"));
		}
		return price1 * price2;
	}

	/**
	 * calculates the business regular(multiple cars regular subscription) cost
	 */
	public int getBusinessRegularSubscriptionCost(int numOfCars) throws NumberFormatException, SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getPrices);
		// stm.setString(1, parkingLot);
		ResultSet res = stm.executeQuery();
		int price1 = 0, price2 = 0;
		while (res.next()) {
			price1 = Integer.parseInt(res.getString("preOrderPrice"));
			price2 = Integer.parseInt(res.getString("regularSubsForMulCars"));
		}

		return price1 * price2 * numOfCars;
	}

	/**
	 * if an order is canceled, this function calculated the amount of money the
	 * customer gets back(if any) using the date and time at which he made the
	 * cancellation
	 */
	public double getCancelOrderCredit(Order order) throws SQLException, Exception {
		double credit = 0;
		String arrivingDate = order.getArrivingDate();
		String arrivingAt = order.getArrivingAt();
		arrivingDate = arrivingDate + " " + arrivingAt;
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		java.util.Date Arriving = dateFormat.parse(arrivingDate);
		java.util.Date now = new java.util.Date();
		long diff = Arriving.getTime() - now.getTime();
		long diffHours = diff / (60 * 60 * 1000) % 24;
		long diffDays = diff / (24 * 60 * 60 * 1000);
		System.out.println("diff hours " + diffHours);
		System.out.println("diff days " + diffDays);
		if (diffDays > 0 || diffHours >= 3) {
			System.out.println("first if");
			credit = (0.9) * getOrderCost(arrivingAt, order.getLeavingAt(), arrivingDate, order.getLeavingDate(),
					order.getType());
		}

		else if (diffHours >= 1 && diffHours < 3) {
			System.out.println("second if");
			System.out.println("price is" + getOrderCost(arrivingAt, order.getLeavingAt(), arrivingDate,
					order.getLeavingDate(), order.getType()));
			credit = (0.5) * getOrderCost(arrivingAt, order.getLeavingAt(), arrivingDate, order.getLeavingDate(),
					order.getType());
		} else {
			System.out.println("third if");
			credit = 0;
		}
		updateCreditByCustomerId(order.getCustomerId(), credit);
		return credit;
		// return true;
	}

	/**
	 * gets the current credit in the customer's account
	 */
	public double getCreditByCustomerId(String customerId) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.selectCustomerById);
		stm.setString(1, customerId);
		double credit = 0;
		ResultSet res = stm.executeQuery();
		while (res.next()) {
			credit = res.getDouble("credit");
		}
		return credit;
	}

	/**
	 * updates the credit in the customer's account
	 */
	public void updateCreditByCustomerId(String customerId, double newCredit) throws SQLException {
		if (customerId != null) {
			PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.selectCustomerById);
			stm.setString(1, customerId);
			ResultSet res = stm.executeQuery();
			while (res.next()) {
				newCredit += res.getDouble("credit");
			}
			stm = c.prepareStatement(sqlStatements.Allstatements.updateCreditByCustomerId);
			stm.setDouble(1, newCredit);
			stm.setString(2, customerId);
			stm.executeUpdate();
		}
	}

	/**
	 * changes the prices of the parking lots
	 */
	public boolean employeePriceChange(String name, int preOrder, int uponArrival) throws SQLException, Exception {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.updatePrices);
		stm.setInt(1, preOrder);
		stm.setInt(2, uponArrival);
		stm.executeUpdate();
		return true;
	}

	/**
	 * generates a random subscription id
	 */
	public String getSaltString() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 5) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;

	}

	/**
	 * gets the email of the customer from the DB
	 */
	public String getCustomerMailById(String customerId) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.selectCustomerById);
		stm.setString(1, customerId);
		ResultSet res = stm.executeQuery();
		String email = null;
		while (res.next()) {
			email = res.getString("email");
		}
		return email;
	}

	/**
	 * when the employee replied to the complaint, the reply is sent to the customer
	 * via mail
	 */
	public void sendComplaintReplyToMail(String email, String reply) {
		Email.sendEmail(email, "Complaint Reply", reply);
	}

	/**
	 * returns an arrayList of orders by a given date and time
	 */
	public ArrayList<Order> getAllPreOrdersByArrivingDate(String arriveDate, String arrivingAt) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getAllOrdersByArrivingDate);
		stm.setString(1, arriveDate);
		stm.setString(2, arrivingAt);
		ResultSet res = stm.executeQuery();
		ArrayList<Order> allOrders = new ArrayList<Order>();
		Order o = null;
		while (res.next()) {
			String typ = res.getString("type");
			OrderType type = OrderType.uponArrivalOrder;
			if (typ.equals("preOrder"))
				type = OrderType.preOrder;
			if (type.equals(OrderType.preOrder)) {
				o = new Order(res.getInt("orderID"), type, res.getString("parkingLot"), res.getString("arrivingDate"),
						res.getString("leavingDate"), res.getString("arrivingAt"), res.getString("leavingAt"),
						res.getString("customerId"), res.getString("vehicleNumber"), res.getBoolean("arrivingLate"),
						res.getBoolean("leavingLate"), res.getBoolean("canceled"));
				allOrders.add(o);
			}
		}
		return allOrders;
	}

	/**
	 * deletes an order, for internal use only
	 * 
	 * @param or wanted order
	 * @return boolean res
	 * @throws SQLException DB connection
	 */
	public boolean deleteOrder(Order or) throws SQLException {
		Order checkOrder = checkIfOrderExistsByAllParameters(or.getCustomerId(), or.getVehicleNum(),
				or.getArrivingDate(), or.getArrivingAt(), or.getLeavingDate(), or.getLeavingAt(), or.getParkingLot());
		if (checkOrder == null) {
			System.out.println("Order does not exist,try a different Order");
			return false;
		}
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.deleteOrder);
		stm.setString(1, or.getParkingLot());
		stm.setString(2, or.getVehicleNum());
		stm.setString(3, or.getCustomerId());
		stm.setString(4, or.getArrivingDate());
		stm.setString(5, or.getArrivingAt());
		stm.setString(6, or.getLeavingDate());
		stm.setString(7, or.getLeavingAt());
		stm.executeUpdate();
		System.out.println("Order deleted successfully");
		return true;
	}

	/**
	 * chekcs if a certain vehicle is currently parked in any parking lot
	 * 
	 * @param vehicleId vehicle
	 * @return boolean value indicating if the vehicle is in any of the parking lots
	 * @throws SQLException
	 *             gets information from DB therefore could be an exception
	 */
	public boolean isVehicleParking(String vehicleId) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.checkIfVehicleParking);
		stm.setString(1, vehicleId);
		ResultSet rs = stm.executeQuery();
		if (rs.next()) {
			return true;
		}
		return false;
	}

	/**
	 * returns all the full subscriptions whose starting date are equal to the
	 * startDate input
	 */
	public ArrayList<FullSubscription> getAllFullSubsByStartingDate(Date startDate) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getAllFullSubsByStartingDate);
		stm.setDate(1, startDate);
		ResultSet res = stm.executeQuery();
		ArrayList<FullSubscription> subs = new ArrayList<FullSubscription>();
		FullSubscription s = null;
		while (res.next()) {
			s = new FullSubscription(res.getString("customerId"), res.getString("subscriptionId"),
					res.getString("vehicleNumber"), res.getDate("startingDate"), res.getString("email"),
					res.getDate("arrivedSince"), subscriptionType.fullSubscription);
			subs.add(s);
		}
		return subs;
	}

	/**
	 * returns all full subscriptions whose have been parking in a parking lot since
	 * the arriveDate
	 */
	public ArrayList<FullSubscription> getAllFullSubsByArrivedSince(Date arriveDate) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getAllFullSubsByArrivedSince);
		stm.setDate(1, arriveDate);
		ResultSet res = stm.executeQuery();
		ArrayList<FullSubscription> subs = new ArrayList<FullSubscription>();
		FullSubscription s = null;
		while (res.next()) {
			s = new FullSubscription(res.getString("customerId"), res.getString("subscriptionId"),
					res.getString("vehicleNumber"), res.getDate("startingDate"), res.getString("email"),
					res.getDate("arrivedSince"), subscriptionType.fullSubscription);
			subs.add(s);
		}
		return subs;
	}

	/**
	 * returns all the regular subscriptions whose starting date are equal to the
	 * startDate input
	 */
	public ArrayList<Subscription> getAllRegSubsByStartingDate(Date startDate) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getAllRegularSubsByStartingDate);
		stm.setDate(1, startDate);
		ResultSet res = stm.executeQuery();
		ArrayList<Subscription> subs = new ArrayList<Subscription>();
		Subscription s = null;
		while (res.next()) {
			Object type = res.getObject("type");
			if (type.equals("oneCar")) {
				s = new OneCarRegularSubscription(res.getString("customerId"), res.getString("subscriptionId"),
						res.getString("vehicleNumber"), res.getDate("startDate"), res.getString("email"),
						subscriptionType.oneCarRegularSubscription, res.getString("parkingLot"),
						res.getString("leavingAt"));
			} else {
				s = new OneCarBusinessSubscription(res.getString("customerId"), res.getString("subscriptionId"),
						res.getString("vehicleNumber"), res.getDate("startDate"), res.getString("email"),
						subscriptionType.regularBusinessSubscription, res.getString("parkingLot"),
						res.getString("leavingAt"));
			}
			subs.add(s);
		}
		return subs;
	}

	/**
	 * returns boolean if the complaint has been checked by an employee
	 */
	public boolean getComplaintStatus(String SubDate, boolean isChecked) throws SQLException {
		DateFormat fullDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		java.util.Date submissionDate = null;
		try {
			submissionDate = fullDateFormat.parse(SubDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (submissionDate != null) {
			java.util.Date now = new java.util.Date();
			long diff = 0;
			diff = now.getTime() - submissionDate.getTime();
			double diffDays = (int) (diff / (1000 * 60 * 60 * 24));
			System.out.println("diff days" + diffDays);
			if (isChecked == false && (diffDays >= 1))
				return true;
		}
		return false;
	}

	/**
	 * set complaint as checked after it's been handled by an employee
	 */
	public void setComplaintAsCheckedByCustomerId(String customerId) throws SQLException {
		PreparedStatement a = c.prepareStatement(sqlStatements.Allstatements.getComplaintByCustomerId);
		a.setString(1, customerId);
		ResultSet res1 = a.executeQuery();
		while (res1.next()) {
			System.out.println(res1.getString("customerID") + " " + res1.getString("text"));
		}
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.setComplaintAsChecked);
		stm.setString(1, customerId);
		stm.executeUpdate();
	}

	/**
	 * returns number of executed(and not canceled) orders by parking lot
	 */
	public int getNumberOfExecutedOrdersByParkingLot(String parkingLot) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getNumberOfExecutedOrdersByParkingLot);
		stm.setString(1, parkingLot);
		int sum = 0;
		ResultSet res = stm.executeQuery();
		while (res.next()) {
			sum++;
		}
		return sum;
	}

	/**
	 * returns number of canceled orders by parking lot
	 */
	public int getNumberOfCanceledOrdersByParkingLot(String parkingLot) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getNumberOfCanceledOrdersByParkingLot);
		stm.setString(1, parkingLot);
		int sum = 0;
		ResultSet res = stm.executeQuery();
		while (res.next()) {
			sum++;
		}
		return sum;
	}

	/**
	 * returns number of arrived late orders by a certain parking lot
	 */
	public int getNumberOfLateArrivalOrdersByParkingLot(String parkingLot) throws SQLException {
		PreparedStatement stm = c
				.prepareStatement(sqlStatements.Allstatements.getNumberOfLateArrivalOrdersByParkingLot);
		stm.setString(1, parkingLot);
		int sum = 0;
		ResultSet res = stm.executeQuery();
		while (res.next()) {
			sum++;
		}
		return sum;
	}

	/**
	 * sets the given parking spot (row,col,width) as faulted in the CPS data base,
	 * by adding it to the FaultedAndSavedParkingSpots table, after confirming it is
	 * not occupied\already registered as faulted
	 */
	public boolean setParkingSpotAsFaulted(String parkingLot, int row, int col, int width) throws SQLException {
		ParkingSpot tmp = getParkingSpotStatus(parkingLot, row, col, width);
		if (tmp.isFaulted() == true) {
			System.out.println("Parking Spot " + row + " " + col + " " + width + " is already faulted");
			return false;
		}
		if (tmp.isSaved() == true) {
			System.out.println("Parking Spot " + row + " " + col + " " + width + " is saved");
			unsetParkingSpotAsSavedOrFaulted(parkingLot, row, col, width);
			System.out.println("parking spot is no longer saved");
		}
		PreparedStatement statement = c.prepareStatement(sqlStatements.Allstatements.setParkingSpotAsFaulted);
		statement.setString(1, parkingLot);
		statement.setInt(2, row);
		statement.setInt(3, col);
		statement.setInt(4, width);
		statement.executeUpdate();
		return true;
	}

	/**
	 * sets the given parking spot (row,col,width) as saved in the CPS data base, by
	 * adding it to the FaultedAndSavedParkingSpots table, after confirming it is
	 * not occupied\already registered as saved
	 */
	public boolean setParkingSpotAsSaved(String parkingLot, int row, int col, int width) throws SQLException {
		ParkingSpot tmp = getParkingSpotStatus(parkingLot, row, col, width);
		if (tmp.isSaved() == true) {
			System.out.println("Parking Spot " + row + " " + col + " " + width + " is already saved");
			return false;
		}
		if (tmp.isFaulted() == true) {
			System.out.println("Parking Spot " + row + col + +width + " is faulted,cannot be saved");
			return false;
		}
		PreparedStatement statement = c.prepareStatement(sqlStatements.Allstatements.setParkingSpotAsSaved);
		statement.setString(1, parkingLot);
		statement.setInt(2, row);
		statement.setInt(3, col);
		statement.setInt(4, width);
		statement.executeUpdate();
		return true;
	}

	public void unsetParkingSpotAsSavedOrFaulted(String parkingLot, int row, int col, int width) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getParkingSpotStatusFaultedOrSaved);
		stm.setString(1, parkingLot);
		stm.setInt(2, row);
		stm.setInt(3, col);
		stm.setInt(4, width);
		ResultSet res = stm.executeQuery();
		int num = 0;
		while (res.next()) {
			num = res.getInt("num");
		}
		stm = c.prepareStatement(sqlStatements.Allstatements.addParkingSpotAsFaultedInHistory);
		stm.setString(1, parkingLot);
		stm.setInt(2, row);
		stm.setInt(3, col);
		stm.setInt(4, width);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		LocalDateTime now = LocalDateTime.now();
		stm.setString(5, dtf.format(now));
		stm.executeUpdate();

		stm = c.prepareStatement(sqlStatements.Allstatements.unsetFaultedOrSavedParkingSpot);
		stm.setInt(1, num);
		stm.execute();
	}

	/**
	 * returns all the information about the parking spot (row,column,width):
	 * isSaved,isFaulted,isOccupied
	 */
	public ParkingSpot getParkingSpotStatus(String parkingLot, int row, int col, int width) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getParkingSpotStatusFaultedOrSaved);
		stm.setString(1, parkingLot);
		stm.setInt(2, row);
		stm.setInt(3, col);
		stm.setInt(4, width);
		ResultSet res = stm.executeQuery();
		ParkingSpot tmp = null;
		while (res.next()) {
			tmp = new ParkingSpot(false, res.getBoolean("isFaulted"), res.getBoolean("isSaved"));
		}
		if (tmp == null) {
			System.out.println("null");
			// stm=c.prepareStatement(sqlStatements.Allstatements.)
			stm = c.prepareStatement(sqlStatements.Allstatements.getParkingSpotStatusOccupuied);
			stm.setString(1, parkingLot);
			stm.setInt(2, row);
			stm.setInt(3, col);
			stm.setInt(4, width);
			res = stm.executeQuery();
			while (res.next()) {
				tmp = new ParkingSpot(true, false, false);
			}
		}
		if (tmp == null) {
			tmp = new ParkingSpot(false, false, false);
		}
		System.out.println("isOccupied= " + tmp.isOccupied());
		System.out.println("isOFaulted= " + tmp.isFaulted());
		System.out.println("isSaved= " + tmp.isSaved());
		return tmp;
	}

	/**
	 * gets the current image of the a certain parking lot: for every 3*3*width the
	 * parking spots we have the function provides the following info about every
	 * parking spot: isSaved,isFaulted,isOccupied
	 */
	public ParkingSpot[][][] getParkingLotImageNew(String parkingLot) throws SQLException {
		int currWidth = getWidthByParkingLot(parkingLot);
		if (currWidth == 0)
			return null;
		ParkingSpot[][][] currImage = new ParkingSpot[3][3][currWidth];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				for (int k = 0; k < currWidth; k++) {
					currImage[i][j][k] = getParkingSpotStatus(parkingLot, i, j,
							k);/* parking spot is either faulted,occupied or saved */
					if (currImage[i][j][k] == null) {/* else if it's none of the above, it's empty */
						currImage[i][j][k] = new ParkingSpot(false, false, false);
						/*
						 * System.out.println("isOccupied= " + currImage[i][j][k]);
						 * System.out.println("isOFaulted= " + currImage[i][j][k]);
						 * System.out.println("isSaved= " + currImage[i][j][k]);
						 */
					}
				}
			}
		}
		return currImage;
	}

	/**
	 * main and actual function inserting a vehicle into a parking lot in the DB,
	 * given the indices (row,col,width) the function calculates the optimal parking
	 * spot for inserting the vehicle according to the arrival and leaving time and
	 * date
	 * 
	 * @param parkingLot
	 *            the wanted parking lot to be inserted at
	 * @param vehicleNumber
	 *            the wanted vehicle number for insertion
	 * @param type
	 *            type of order(pre order,upon arrival)
	 * @return does not return anything
	 * @throws SQLException
	 *             connects to the DB so there may be an exception
	 * @throws ParseException
	 *             cannot patsr if null
	 */
	public boolean insertCarIntoParkingLot(String parkingLot, String vehicleNumber, String type)
			throws SQLException, ParseException {
		if (parkingLot != null && vehicleNumber != null && type != null) {
			System.out.println("order is " + type);
			long diff, diff1;
			boolean canEnter = false;
			String arrivingDate = null;
			String arrivingAt = null;
			String leavingDate = null;
			String leavingAt = null;
			String customerId = null;
			String arrivedSince = null;
			int orderId = 0;
			int[] ps = new int[3];
			DateFormat fulldateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			DateFormat timeFormat = new SimpleDateFormat("HH:mm");
			// DateFormat startingDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date now = new java.util.Date();
			java.util.Date arriving = null;
			java.util.Date leaving = null;
			java.util.Date startingDate = null;
			if (type.equals("preOrder") || type.equals("uponArrivalOrder")) {
				System.out.println("first if");
				PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.checkIfOrderExistsByLess);
				stm.setString(1, parkingLot);
				stm.setString(2, vehicleNumber);
				ResultSet res = stm.executeQuery();
				while (res.next()) {
					System.out.println("we're in the while");
					customerId = res.getString("customerId");
					System.out.println(customerId + "**");
					arrivingDate = res.getString("arrivingDate");
					arrivingAt = res.getString("arrivingAt");
					leavingDate = res.getString("leavingDate");
					leavingAt = res.getString("leavingAt");
					orderId = res.getInt("orderID");
					arrivingDate = arrivingDate + " " + arrivingAt;
					try {
						arriving = fulldateFormat.parse(arrivingDate);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					arrivingDate = leavingDate + " " + leavingAt;
					try {
						leaving = fulldateFormat.parse(arrivingDate);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					diff = arriving.getTime() - now.getTime();
					diff /= 60000;
					diff1 = leaving.getTime() - now.getTime();
					diff1 /= 60000;

					if (diff1 > 0 && diff < -3) {
						PreparedStatement stmm = c.prepareStatement(sqlStatements.Allstatements.setArrivingLate);
						stmm.setInt(1, orderId);
						stmm.setString(2, customerId);
						stmm.setString(3, vehicleNumber);
						stmm.executeUpdate();
						diff = 0;
					}
					if (diff < 3 && diff > -3) {
						System.out.println("can enter is true");
						canEnter = true;
						break;
					}
					if (diff < -3) {
						System.out.println(
								"you came erlier than your arriving time! you cant enter the Parking Lot now. ");
					}
				}
			}

			if (!canEnter && type.equals("regularSubscription")) {
				PreparedStatement stm = c
						.prepareStatement(sqlStatements.Allstatements.getAllRegularSubsByVehicleNumber);
				stm.setString(1, vehicleNumber);
				ResultSet res = stm.executeQuery();
				while (res.next()) {
					if (res.getString("parkingLot").equals(parkingLot)) {
						customerId = res.getString("customerId");
						leavingAt = res.getString("leavingAt");

						startingDate = res.getDate("startDate");
						String Starting = dateFormat.format(startingDate);
						startingDate = dateFormat.parse(Starting);
						System.out.println("starting Date: " + Starting);

						leavingDate = dateFormat.format(now);

						int day = now.getDay();
						if (day == 5 || day == 6) {
							System.out.println(
									"Regular Subscriptions can't get in on weekends, come on a different Day:)");
							return false;
						}

						diff = now.getTime() - startingDate.getTime();
						diff /= (60 * 60 * 24 * 1000);
						System.out.println("days between the start of Regular Subscription & today is: " + diff);
						if (diff > -1 && diff < 29) {
							canEnter = true;
							break;
						}
					}
				}
			}

			if (!canEnter && type.equals("fullSubscription")) {
				String subId = null;
				PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getAllFullSubsByVehicleNumber);
				stm.setString(1, vehicleNumber);
				ResultSet res = stm.executeQuery();
				while (res.next()) {
					subId = res.getString("subscriptionId");
					customerId = res.getString("customerId");
					startingDate = res.getDate("startingDate");
					String Starting = dateFormat.format(startingDate);
					startingDate = dateFormat.parse(Starting);
					System.out.println("starting Date: " + Starting);
					java.util.Date now1 = new java.util.Date();
					java.util.Calendar calenedar = java.util.Calendar.getInstance();
					calenedar.setTime(now1);
					calenedar.add(java.util.Calendar.DATE, 14);
					now1 = calenedar.getTime();
					leavingAt = timeFormat.format(now1);
					leavingDate = dateFormat.format(now1);

					diff = now.getTime() - startingDate.getTime();
					diff /= (60 * 60 * 24 * 1000);
					System.out.println("days between the start of Full Subscription & today is: " + diff);
					if (diff > -1 && diff < 29) {
						canEnter = true;
						PreparedStatement stmm = c
								.prepareStatement(sqlStatements.Allstatements.setFullSubscripsionArrivedSince);
						stmm.setString(1, leavingDate);
						stmm.setString(2, customerId);
						stmm.setString(3, vehicleNumber);
						break;
					}
				}
				if (canEnter == false)
					System.out.println("Full Subscription is not valid!");
				if (canEnter == true && subId != null) {
					java.sql.Date noww = new java.sql.Date(now.getTime());
					stm = c.prepareStatement(sqlStatements.Allstatements.updateArrivedSinceInFullSub);
					stm.setDate(1, noww);
					stm.setString(2, subId);
					stm.setString(3, customerId);
					stm.executeUpdate();
				}
			}

			if (canEnter) {
				// System.out.println("going to the other function");
				try {
					ps = getOptemalParkingSpot(parkingLot, leavingDate, leavingAt);
					System.out.println("back from calculating optimal");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				setParkingSpotAsOccupied(parkingLot, customerId, vehicleNumber, ps[0], ps[1], ps[2], leavingDate,
						leavingAt, type);
				// getParkingLotImageNew(parkingLot);
				return true;
			}
		}
		return false;
	}

	/**
	 * removes a car from it's parking spot in the parking lot
	 * 
	 * @param vehicleNumber
	 *            the wanted vehicle number to be exited out of the parkimg lot
	 * @param parkingLot
	 * @return
	 * @throws SQLException
	 *             connects to DB so there may be an exception
	 * @throws Exception
	 *             other kinds of exceptions
	 */
	public double removeCarFromParkingLot(String vehicleNumber, String parkingLot) throws SQLException, Exception {
		if (isVehicleParking(vehicleNumber) == false) {
			return -2;
		}
		String customerId = null;
		String leavingDate = null;
		String leavingAt = null;
		String arrivingDate = null;
		String arrivingAt = null;
		String temp = null;
		java.util.Date leaving = null;
		java.util.Date now = new java.util.Date();
		String type = null;
		int row = -1, column = -1, width = -1;
		long diff;

		DateFormat fullDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		if (vehicleNumber != null && parkingLot != null) {
			double cost = 0;
			PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getVehicleFromOccupiedParkingSpots);
			stm.setString(1, vehicleNumber);
			stm.setString(2, parkingLot);
			ResultSet res = stm.executeQuery();
			while (res.next()) {
				row = res.getInt("row");
				column = res.getInt("column");
				width = res.getInt("width");
				leavingDate = res.getString("leavingDate");
				leavingAt = res.getString("leavingAt");
				type = res.getString("type");
				System.out.println("tye is " + type);
				customerId = res.getString("customerId");
			}
			temp = leavingDate + " " + leavingAt;
			System.out.println("temp is :" + temp);
			leaving = fullDateFormat.parse(temp);
			diff = now.getTime() - leaving.getTime();
			diff /= 60000;
			if (diff > 3 && (type.equals("uponArrivalOrder") == true || type.equals("preOrder")) == true) {
				System.out.println("e7na bawl if");
				System.out.println("diff>3");
				PreparedStatement stmm = c.prepareStatement(sqlStatements.Allstatements.setLeavingLate);
				stmm.setString(1, customerId);
				stmm.setString(2, vehicleNumber);
				stmm.setString(3, parkingLot);
				stmm.setString(4, leavingDate);
				stmm.setString(5, leavingAt);
				stmm.executeUpdate();
			}
			if (type.equals("uponArrivalOrder") == true) {
				System.out.println("btane if");
				OrderType uponArrival = OrderType.uponArrivalOrder;
				PreparedStatement stmm = c.prepareStatement(sqlStatements.Allstatements.getOrderByNotArriving);
				stmm.setString(1, parkingLot);
				stmm.setString(2, vehicleNumber);
				stmm.setString(3, customerId);
				stmm.setString(4, leavingDate);
				stmm.setString(5, leavingAt);
				ResultSet ress = stmm.executeQuery();
				while (ress.next()) {
					arrivingDate = ress.getString("arrivingDate");
					arrivingAt = ress.getString("arrivingAt");
					Order or = new Order(ress.getInt("orderID"), OrderType.uponArrivalOrder, parkingLot,
							ress.getString("arrivingDate"), leavingDate, arrivingAt, leavingAt, customerId,
							vehicleNumber, ress.getBoolean("arrivingLate"), ress.getBoolean("leavingLate"),
							ress.getBoolean("canceled"));
					arrivingOrLeavingLateCredit(or);
				}
				cost = getOrderCost(arrivingAt, leavingAt, arrivingDate, leavingDate, uponArrival);
				System.out.println("cost is" + cost);
			}

			if (type.equals("preOrder") == true) {
				System.out.println("talet if");
				stm = c.prepareStatement(sqlStatements.Allstatements.getOrderByNotArriving);
				stm.setString(1, parkingLot);
				stm.setString(2, vehicleNumber);
				stm.setString(3, customerId);
				stm.setString(4, leavingDate);
				stm.setString(5, leavingAt);
				res = stm.executeQuery();
				while (res.next()) {
					System.out.println(res.getInt("orderID"));
					Order or = new Order(res.getInt("orderID"), OrderType.preOrder, parkingLot,
							res.getString("arrivingDate"), leavingDate, res.getString("arrivingAt"), leavingAt,
							customerId, vehicleNumber, res.getBoolean("arrivingLate"), res.getBoolean("leavingLate"),
							res.getBoolean("canceled"));
					arrivingOrLeavingLateCredit(or);
				}
			}
			unsetParkingSpotAsOccupied(parkingLot, customerId, vehicleNumber, row, column, width, leavingDate,
					leavingAt);
			rearrangeParkingLot(parkingLot, row, column, width);
			return cost;

		}
		return (-1);
	}

	/**
	 * inserts the car into the given parking spot in the DB by adding it to the
	 * occupied parking spots table
	 * 
	 * @param parkingLot
	 * @param customerId
	 * @param vehicleNumber
	 * @param row
	 * @param column
	 * @param width
	 * @param leavingDate
	 * @param leavingAt
	 * @param type
	 * @return boolean status of adding
	 * @throws SQLException
	 *             connects to DB so there migh be an exception
	 */
	public boolean setParkingSpotAsOccupied(String parkingLot, String customerId, String vehicleNumber, int row,
			int column, int width, String leavingDate, String leavingAt, String type) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getVehicleByParkingLotAndSpot);
		stm.setString(1, parkingLot);
		stm.setInt(2, row);
		stm.setInt(3, column);
		stm.setInt(4, width);
		ResultSet res = stm.executeQuery();
		while (res.next()) {
			System.out.println("There is a car in that Parking Spot! , try a different one :)");
			return false;
		}

		stm = c.prepareStatement(sqlStatements.Allstatements.enterVehicleIntoParkingLot);
		stm.setString(1, vehicleNumber);
		stm.setString(2, customerId);
		stm.setString(3, parkingLot);
		stm.setInt(4, row);
		stm.setInt(5, column);
		stm.setInt(6, width);
		stm.setString(7, leavingDate);
		stm.setString(8, leavingAt);
		stm.setString(9, type);
		stm.executeUpdate();
		return true;

	}

	/**
	 * deletes the vehicle from the OccupiedParkingSpots table in the CPS DB
	 * 
	 * @param parkingLot
	 * @param customerId
	 * @param vehicleNumber
	 * @param row
	 * @param column
	 * @param width
	 * @param leavingDate
	 * @param leavingAt
	 * @throws SQLException
	 *             connects to DB so there might be an exception
	 */
	public void unsetParkingSpotAsOccupied(String parkingLot, String customerId, String vehicleNumber, int row,
			int column, int width, String leavingDate, String leavingAt) throws SQLException {
		// CPS cps = CPS.getInstance();
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getVehicleByParkingLotAndSpot);
		stm.setString(1, parkingLot);
		stm.setInt(2, row);
		stm.setInt(3, column);
		stm.setInt(4, width);
		ResultSet res = stm.executeQuery();
		while (!res.next()) {
			System.out.println("There is NO car in that Parking Spot! , try a different one :)");
			return;
		}

		stm = c.prepareStatement(sqlStatements.Allstatements.removeVehicleFromParkingLot);
		stm.setString(1, vehicleNumber);
		stm.setString(2, customerId);
		stm.setString(3, parkingLot);
		stm.setInt(4, row);
		stm.setInt(5, column);
		stm.setInt(6, width);
		stm.execute();
	}

	/**
	 * returns information about the parking spot (row,width,column) in the parking
	 * lot parkingLot
	 * 
	 * @param parkingLot
	 * @param row
	 * @param column
	 * @param width
	 * @return
	 * @throws SQLException
	 *             connects to DB
	 */
	public Order getVehicleOrderByParkinLotAndSpot(String parkingLot, int row, int column, int width)
			throws SQLException {
		Order o = null;
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getVehicleByParkingLotAndSpot);
		stm.setString(1, parkingLot);
		stm.setInt(2, row);
		stm.setInt(3, column);
		stm.setInt(4, width);
		ResultSet res = stm.executeQuery();
		while (res.next()) {
			o = new Order(0, OrderType.preOrder, res.getString("parkingLot"), "null", res.getString("leavingDate"),
					"null", res.getString("leavingAt"), "null", "null", false, false, false);
		}

		return o;
	}

	/**
	 * gets the width of the parkinglot from the DB
	 * 
	 * @param parkingLot
	 * @return
	 * @throws SQLException
	 *             DB
	 */
	public int getWidthByParkingLot(String parkingLot) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.selectParkingLotByName);
		stm.setString(1, parkingLot);
		ResultSet res = stm.executeQuery();
		int currWidth = 0;
		while (res.next()) {
			currWidth = res.getInt("width");
		}
		return currWidth;
	}

	/**
	 * calculated the optimal parking spot for a vehicle to be entered, the
	 * calculation takes into consideration the starting and leaving times and dates
	 * 
	 * @param parkingLot
	 * @param ThisLeavingDate
	 * @param ThisLeavingAt
	 * @return
	 * @throws SQLException
	 *             DB
	 * @throws Exception
	 *             other
	 */
	public int[] getOptemalParkingSpot(String parkingLot, String ThisLeavingDate, String ThisLeavingAt)
			throws SQLException, Exception {
		int[] optemalps = new int[3];
		Order order;
		boolean optemal = false;
		int currWidth = getWidthByParkingLot(parkingLot);
		ParkingSpot[][][] currentImage = getParkingLotImageNew(parkingLot);
		boolean set = false;
		String LeavingDate = null;
		String LeavingAt = null;
		ThisLeavingDate = ThisLeavingDate + " " + ThisLeavingAt;
		DateFormat fullDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		java.util.Date thisDate = fullDateFormat.parse(ThisLeavingDate);
		java.util.Date Date = null;
		long diff = -1;

		if (currentImage == null) {
			System.out.println("it's null");
			return optemalps;
		}

		for (int i = 0; i < 3; i++) {
			for (int k = 0; k < currWidth; k++) {
				ParkingSpot tmp = currentImage[0][i][k];
				if (tmp.isFaulted() == false && tmp.isOccupied() == false && tmp.isSaved() == false) {
					optemal = true;
					optemalps[0] = 0;
					optemalps[1] = i;
					optemalps[2] = k;
					System.out.println("Row" + optemalps[0]);
					System.out.println("Column" + optemalps[1]);
					System.out.println("Width" + optemalps[2]);
					return optemalps;
				}
			}
		}

		for (int i = 0; i < 3; i++) {
			for (int k = 0; k < currWidth; k++) {
				ParkingSpot tmp = currentImage[1][i][k];
				if (tmp.isFaulted() == false && tmp.isOccupied() == false && tmp.isSaved() == false) {
					if (!set) {
						optemal = true;
						optemalps[0] = 1;
						optemalps[1] = i;
						optemalps[2] = k;
						set = true;
						tmp = currentImage[0][i][k];
						if (tmp.isOccupied() == true) {
							order = getVehicleOrderByParkinLotAndSpot(parkingLot, 0, i, k);
							if (order != null) {
								LeavingDate = order.getLeavingDate();
								LeavingAt = order.getArrivingAt();
								LeavingDate = LeavingDate + " " + LeavingAt;
								Date = fullDateFormat.parse(LeavingDate);
								diff = Date.getTime() - thisDate.getTime();
								if (diff < 0) {
									System.out.println("the car in place: 1 , " + optemalps[i] + " , " + optemalps[k]
											+ " leaves the ParkingLot before the car you are entering");
									return optemalps;
								}
							}
						} else if (tmp.isFaulted() == true || tmp.isSaved() == true) {
							diff = 0;
							return optemalps;
						}
					} else {
						ParkingSpot tmp1 = currentImage[0][i][k];
						if (diff != 0 && (tmp1.isFaulted() == true || tmp1.isSaved() == true)) {
							optemal = true;
							optemalps[0] = 1;
							optemalps[1] = i;
							optemalps[2] = k;
							diff = 0;
							return optemalps;
						} else if (tmp1.isOccupied() == true) {
							order = getVehicleOrderByParkinLotAndSpot(parkingLot, 0, i, k);
							if (order != null) {
								LeavingDate = order.getLeavingDate();
								LeavingAt = order.getArrivingAt();
								LeavingDate = LeavingDate + " " + LeavingAt;
								Date = fullDateFormat.parse(LeavingDate);
								long diff1 = Date.getTime() - thisDate.getTime();
								if (diff != 0 && diff1 < diff) {
									optemal = true;
									optemalps[0] = 1;
									optemalps[1] = i;
									optemalps[2] = k;
									diff = diff1;
									if (diff > 0) {
										System.out
												.println("the car in place: 0 , " + optemalps[i] + " , " + optemalps[k]
														+ " leaves the ParkingLot before the car you are entering");
										return optemalps;
									}
								}
							}
						}
					}
				}
			}
		}

		if (optemal == true) {
			return optemalps;
		}
		set = false;
		diff = -1;

		for (int i = 0; i < 3; i++) {
			for (int k = 0; k < currWidth; k++) {
				ParkingSpot tmp = currentImage[2][i][k];
				if (tmp.isFaulted() == false && tmp.isOccupied() == false && tmp.isSaved() == false) {
					if (!set) {
						optemal = true;
						optemalps[0] = 2;
						optemalps[1] = i;
						optemalps[2] = k;
						set = true;
						tmp = currentImage[1][i][k];
						if (tmp.isOccupied() == true) {
							order = getVehicleOrderByParkinLotAndSpot(parkingLot, 1, i, k);
							if (order != null) {
								LeavingDate = order.getLeavingDate();
								LeavingAt = order.getArrivingAt();
								LeavingDate = LeavingDate + " " + LeavingAt;
								Date = fullDateFormat.parse(LeavingDate);
								diff = Date.getTime() - thisDate.getTime();
								if (diff > 0) {
									System.out.println("the car in place: 1 , " + optemalps[i] + " , " + optemalps[k]
											+ " leaves the ParkingLot before the car you are entering");
									return optemalps;
								}

							}
						} else if (tmp.isFaulted() == true || tmp.isSaved() == true) {
							tmp = currentImage[0][i][k];
							if (tmp.isOccupied() == true) {
								order = getVehicleOrderByParkinLotAndSpot(parkingLot, 0, i, k);
								if (order != null) {
									LeavingDate = order.getLeavingDate();
									LeavingAt = order.getArrivingAt();
									LeavingDate = LeavingDate + " " + LeavingAt;
									Date = fullDateFormat.parse(LeavingDate);
									diff = Date.getTime() - thisDate.getTime();
									if (diff > 0) {
										System.out
												.println("the car in place: 0 , " + optemalps[i] + " , " + optemalps[k]
														+ " leaves the ParkingLot before the car you are entering");
										return optemalps;
									}
								}
							} else if (tmp.isFaulted() == true || tmp.isSaved() == true) {
								diff = 0;
								return optemalps;
							}
						}
					} else {
						ParkingSpot tmp1 = currentImage[1][i][k];
						if (tmp1.isFaulted() == true || tmp1.isSaved() == true) {
							ParkingSpot tmp2 = currentImage[0][i][k];
							if (tmp2.isOccupied() == true) {
								order = getVehicleOrderByParkinLotAndSpot(parkingLot, 0, i, k);
								if (order != null) {
									LeavingDate = order.getLeavingDate();
									LeavingAt = order.getArrivingAt();
									LeavingDate = LeavingDate + " " + LeavingAt;
									Date = fullDateFormat.parse(LeavingDate);
									long diff1 = Date.getTime() - thisDate.getTime();
									if (diff1 < 0 || diff1 < diff) {
										optemal = true;
										optemalps[0] = 2;
										optemalps[1] = i;
										optemalps[2] = k;
										diff = diff1;
										if (diff1 < 0) {
											System.out.println(
													"the car in place: 1 , " + optemalps[i] + " , " + optemalps[k]
															+ " leaves the ParkingLot before the car you are entering");
											return optemalps;
										}
									}
								}
							} else if (tmp2.isFaulted() == true || tmp2.isSaved() == true) {
								diff = 0;
								return optemalps;
							}
						} else if (tmp1.isOccupied() == true) {
							order = getVehicleOrderByParkinLotAndSpot(parkingLot, 0, i, k);
							if (order != null) {
								LeavingDate = order.getLeavingDate();
								LeavingAt = order.getArrivingAt();
								LeavingDate = LeavingDate + " " + LeavingAt;
								Date = fullDateFormat.parse(LeavingDate);
								long diff1 = Date.getTime() - thisDate.getTime();
								if (diff1 < 0 || diff > diff1) {
									optemal = true;
									optemalps[0] = 0;
									optemalps[1] = i;
									optemalps[2] = k;
									diff = diff1;
									if (diff1 < 0) {
										System.out
												.println("the car in place: 1 , " + optemalps[i] + " , " + optemalps[k]
														+ " leaves the ParkingLot before the car you are entering");
										return optemalps;
									}
								}
							}
						}
					}
				}
			}
		}
		return optemalps;
	}

	/**
	 * re-arranges the parking lot after the vehicle (row,column,width) exists the
	 * parking lot
	 * 
	 * @param parkingSpot
	 * @param row
	 * @param column
	 * @param width
	 * @throws SQLException
	 *             DB
	 * @throws Exception
	 *             ther
	 */
	public void rearrangeParkingLot(String parkingSpot, int row, int column, int width) throws SQLException, Exception {
		String vehicleNumber = null;
		String parkingLot = null;
		boolean RowOneMoved = false;
		boolean RowIsZero = true;
		if (row == 1)
			RowIsZero = false;

		if (row == 0 || row == 1) {
			int rowPlus1 = row + 1;
			PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getVehicleByParkingLotAndSpot);
			stm.setString(1, parkingSpot);
			stm.setInt(2, rowPlus1);
			stm.setInt(3, column);
			stm.setInt(4, width);
			ResultSet res = stm.executeQuery();
			while (res.next()) {
				RowOneMoved = true;
				vehicleNumber = res.getString("vehicle");
				parkingLot = res.getString("parkingLot");
			}
			stm = c.prepareStatement(sqlStatements.Allstatements.updateRowByRestAndVehicle);
			stm.setInt(1, row);
			stm.setString(2, vehicleNumber);
			stm.setString(3, parkingLot);
			stm.setInt(4, column);
			stm.setInt(5, width);
			stm.executeUpdate();

			if (RowIsZero) {

				stm = c.prepareStatement(sqlStatements.Allstatements.getVehicleByParkingLotAndSpot);
				stm.setString(1, parkingSpot);
				stm.setInt(2, row + 2);
				stm.setInt(3, column);
				stm.setInt(4, width);
				res = stm.executeQuery();
				while (res.next()) {
					vehicleNumber = res.getString("vehicle");
					parkingLot = res.getString("parkingLot");
				}
				stm = c.prepareStatement(sqlStatements.Allstatements.updateRowByRestAndVehicle);
				if (RowOneMoved == false)
					stm.setInt(1, row);
				else
					stm.setInt(1, row + 1);
				stm.setString(2, vehicleNumber);
				stm.setString(3, parkingLot);
				stm.setInt(4, column);
				stm.setInt(5, width);
				stm.executeUpdate();
			}
		}
	}

	/**
	 * returns boolean value if the date is from now on
	 * 
	 * @param date
	 * @param time
	 * @return
	 * @throws ParseException
	 *             can't parse if null
	 */
	public boolean checkIfDateIsFromNowOn(String date, String time) throws ParseException {
		boolean check = false;
		java.util.Date now = new java.util.Date();
		java.util.Date DateAndTime = null;
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		if (time == null)
			time = "23:59";
		String dateAndTime = null;
		dateAndTime = date + " " + time;
		DateAndTime = dateFormat.parse(dateAndTime);
		check = now.before(DateAndTime);
		return check;
	}

	/**
	 * when arriving or leaving late to/from the parking lot, this function
	 * calculates the sum of money the customer is refunded or has to pay(if necessary)
	 * 
	 * @param o
	 * @return
	 * @throws Exception other
	 * @throws SQLException DB
	 */
	public boolean arrivingOrLeavingLateCredit(Order o) throws Exception, SQLException {
		if (o != null) {
			double credit = 0;
			String customerId = null;
			String leavingDate = null;
			String leavingAt = null;
			java.util.Date now = new java.util.Date();
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			DateFormat timeFormat = new SimpleDateFormat("HH:mm");
			leavingDate = dateFormat.format(now);
			leavingAt = timeFormat.format(now);
			double cost1 = getOrderCost(o.getLeavingAt(), leavingAt, o.getLeavingDate(), leavingDate, o.getType());
			double cost2 = getOrderCost(o.getArrivingAt(), o.getLeavingAt(), o.getArrivingDate(), o.getLeavingDate(),
					o.getType());
			if (o.isArrivingLate() == true) {
				credit -= 0.2 * cost2;
			}
			if (o.isLeavingLate() == true) {
				credit -= (cost1 * 2);
			}
			customerId = o.getCustomerId();
			PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.selectCustomerById);
			stm.setString(1, customerId);
			ResultSet res = stm.executeQuery();
			while (res.next()) {
				credit += res.getInt("credit");
			}

			stm = c.prepareStatement(sqlStatements.Allstatements.updateCreditByCustomerId);
			stm.setDouble(1, credit);
			stm.setString(2, customerId);
			stm.executeUpdate();
			return true;
		}
		return false;
	}
/**
 * 
 * @param parkingLot
 * @return number of regular subscriptions in parking lot
 * @throws SQLException
 */
	public int getNumOfSubscriptionsByParkingLot(String parkingLot) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getNumOfSubsByParkingLot);
		stm.setString(1, parkingLot);
		ResultSet res = stm.executeQuery();
		int sum = 0;
		while (res.next()) {
			// System.out.println(res.getString("customerId"));
			sum++;
		}
		return sum;
	}
/**
 * 
 * @param parkingLot
 * @return number of regular subscriptions with multiple vehicles in parkingLot
 * @throws SQLException
 */
	public int getNumOfRegularSubsWithMultipleVehiclesByParkingLot(String parkingLot) throws SQLException {
		System.out.println("we're here**********************");
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getAllRegularSubs);
		ResultSet res = stm.executeQuery();
		ArrayList<String> customersIDs = new ArrayList<String>();
		while (res.next()) {
			String customerId = res.getString("customerId");
			int flag = 0;
			System.out.println("Customer " + customerId);
			for (String string : customersIDs) {
				if (string.equals(customerId) == true) {
					flag = 1;
				}
			}
			if (flag == 0) {
				System.out.println("this customer is about to be added" + customerId);
				customersIDs.add(customerId);
			}
		}
		int sum = 0;
		for (String string : customersIDs) {
			int numOfSubs = getNumOfRegularSubsByCustomerIdAndParkingLot(string, parkingLot);
			if (numOfSubs > 1) {
				sum++;
			}
		}
		return sum;
	}
/**
 * 
 * @param customerId
 * @param parkingLot
 * @return number of customer's regular subscriptions in parkingLot
 * @throws SQLException
 */
	public int getNumOfRegularSubsByCustomerIdAndParkingLot(String customerId, String parkingLot) throws SQLException {
		PreparedStatement stm = c
				.prepareStatement(sqlStatements.Allstatements.getAllRegularSubsByCustomerIdAndParkingLot);
		stm.setString(1, customerId);
		stm.setString(2, parkingLot);
		ResultSet res = stm.executeQuery();
		int sum = 0;
		while (res.next()) {
			sum++;
		}
		return sum;

	}
/*
 * returnns number of all subscriptions by customer id
 */
	public int getNumberOfSubscriptionsByCustomerId(String customerId) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getAllRegularSubsByCustomerId);
		stm.setString(1, customerId);
		ResultSet res = stm.executeQuery();
		int sum = 0;
		while (res.next()) {
			sum++;
		}
		stm = c.prepareStatement(sqlStatements.Allstatements.getAllFullSubsByCustomerId);
		stm.setString(1, customerId);
		res = stm.executeQuery();
		while (res.next()) {
			sum++;
		}
		return sum;
	}
/**
 * 
 * @return total number of CPS customers with multiple vehicles subscriptions
 * @throws SQLException
 */
	public int getNumberOfCustomersWithMoreThanOneSubscription() throws SQLException {
		ArrayList<Customer> allCustomers = new ArrayList<Customer>();
		allCustomers = getAllCustomers();
		int sum = 0;
		for (Customer customer : allCustomers) {
			if (getNumberOfSubscriptionsByCustomerId(customer.getId()) > 1) {
				System.out.println("customer is" + customer.getId());
				sum++;
			}
		}
		return sum;
	}
/**
 * 
 * @param parkingLot
 * @return all orders in parkingLot
 * @throws SQLException DB
 */
	public ArrayList<Order> getAllOrdersByParkingLot(String parkingLot) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getAllOrdersByParkingLot);
		stm.setString(1, parkingLot);
		ArrayList<Order> allOrders = new ArrayList<Order>();
		Order o = null;
		ResultSet res = stm.executeQuery();
		while (res.next()) {
			String type1 = res.getString("type");
			OrderType type = OrderType.valueOf(type1);
			o = new Order(res.getInt("orderID"), type, res.getString("parkingLot"), res.getString("arrivingDate"),
					res.getString("leavingDate"), res.getString("arrivingAt"), res.getString("leavingAt"),
					res.getString("customerId"), res.getString("vehicleNumber"), res.getBoolean("arrivingLate"),
					res.getBoolean("leavingLate"), res.getBoolean("canceled"));
			allOrders.add(o);

		}
		return allOrders;
	}
/**
 * 
 * @param parkingLot
 * @return all subscriptions in parking lot
 * @throws SQLException DB
 */
	public ArrayList<Subscription> getAllSubsByParkingLot(String parkingLot) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getAllFullSubsByParkingLot);
		stm.setString(1, parkingLot);
		ResultSet res = stm.executeQuery();
		ArrayList<Subscription> subs = new ArrayList<Subscription>();
		FullSubscription s = null;
		while (res.next()) {
			s = new FullSubscription(res.getString("customerId"), res.getString("subscriptionId"),
					res.getString("vehicleNumber"), res.getDate("startingDate"), res.getString("email"),
					res.getDate("arrivedSince"), subscriptionType.fullSubscription);
			subs.add(s);
			// System.out.println(s.toString());
		}
		stm = c.prepareStatement(sqlStatements.Allstatements.getAllRegularSubsByParkingLot);
		stm.setString(1, parkingLot);
		res = stm.executeQuery();
		while (res.next()) {
			String type1 = res.getString("type");
			// OrderType type = OrderType.valueOf(type1);
			if (type1.equals("oneCar")) {
				OneCarRegularSubscription oc = new OneCarRegularSubscription(res.getString("customerId"),
						res.getString("subscriptionId"), res.getString("vehicleNumber"), res.getDate("startDate"),
						res.getString("email"), subscriptionType.oneCarRegularSubscription, res.getString("parkingLot"),
						res.getString("leavingAt"));
				subs.add(oc);
				// System.out.println(oc.toString());
			}
			if (type1.equals("business")) {
				OneCarBusinessSubscription bs = new OneCarBusinessSubscription(res.getString("customerId"),
						res.getString("subscriptionId"), res.getString("vehicleNumber"), res.getDate("startDate"),
						res.getString("email"), subscriptionType.regularBusinessSubscription,
						res.getString("parkingLot"), res.getString("leavingAt"));
				subs.add(bs);
			}
		}

		return subs;
	}
/*
 * returns all complaints filed on parkingLot
 */
	public ArrayList<Complaint> getAllComplaintsByParkingLot(String parkingLot) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getAllComplaintsByParkingLot);
		stm.setString(1, parkingLot);
		ResultSet res = stm.executeQuery();
		ArrayList<Complaint> complaints = new ArrayList<Complaint>();
		while (res.next()) {
			Complaint complaint = new Complaint(res.getString("parkingLot"), res.getString("customerId"),
					res.getString("text"));
			complaint.setSubmissionDate(res.getString("submissionDate"));
			complaint.setChecked(res.getBoolean("isChecked"));
			complaints.add(complaint);
		}
		return complaints;
	}
/**
 * contains info about the previous faukted parking spots
 * @param parkingLot
 * @return arraylist with all the faluted parking spots
 * @throws SQLException
 */
	public ArrayList<FaultedParkingSpotHistory> getFaultedParkingSpotsHistoryByParkingLot(String parkingLot)
			throws SQLException {
		PreparedStatement stm = c
				.prepareStatement(sqlStatements.Allstatements.getFaultedParkingSpotsHistoryByParkingLot);
		stm.setString(1, parkingLot);
		ResultSet res = stm.executeQuery();
		ArrayList<FaultedParkingSpotHistory> history = new ArrayList<FaultedParkingSpotHistory>();
		while (res.next()) {
			FaultedParkingSpotHistory tmp = new FaultedParkingSpotHistory(parkingLot, res.getInt("row"),
					res.getInt("col"), res.getInt("width"), res.getString("date"));
			history.add(tmp);
		}
		return history;
	}
/**
 * 
 * @param parkingLot
 * @return is full
 * @throws SQLException
 */
	public boolean isParkingLotFull(String parkingLot) throws SQLException {
		if (parkingLot != null) {
			int width = getWidthByParkingLot(parkingLot);
			int allSpots = 3 * 3 * width;
			int count = 0;
			for (ParkingSpot[][] i : getParkingLotImageNew(parkingLot)) {
				for (ParkingSpot[] parkingSpots : i) {
					for (ParkingSpot parkingSpot : parkingSpots) {
						if (parkingSpot.isOccupied() == true || parkingSpot.isSaved() == true) {
							count++;
						}
					}
				}
			}
			System.out.println();
			if (count == allSpots) {
				return true;
			} else
				return false;
		}
		return false;
	}
/**
 * 
 * @return manager's mail
 * @throws SQLException DB
 */
	public String getManagerMail() throws SQLException {
		// TODO Auto-generated method stub
		String email = null;

		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getManagerMail);
		ResultSet res = stm.executeQuery();
		while (res.next()) {
			email = res.getString("email");
			System.out.println(email);
		}
		return email;
	}

	public String getEmployeeEmailByName(String name) throws SQLException {
		String email = null;

		if (name != null) {
			System.out.println("name " + name);
			PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getEmployeeByName);
			stm.setString(1, name);
			ResultSet res = stm.executeQuery();
			while (res.next()) {
				email = res.getString("email");
				System.out.println(email);
			}
			return email;
		}
		return email;
	}

	public ArrayList<Integer> getAllPrices() throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getPrices);
		// stm.setString(1, parkingLot);
		ResultSet res = stm.executeQuery();
		ArrayList<Integer> orderPrices = new ArrayList<Integer>();
		while (res.next()) {
			orderPrices.add(Integer.parseInt(res.getString("preOrderPrice")));
			orderPrices.add(Integer.parseInt(res.getString("uponArrivalPrice")));
		}
		return orderPrices;
	}

}