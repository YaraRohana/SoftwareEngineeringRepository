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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.crypto.KeySelector.Purpose;

import allClasses.CPS;
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
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.selectVehicleByVehicleNum);
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

	@SuppressWarnings("deprecation")
	public double getOrderCost(String arrivingAt, String leavingAt, String arrivingDate, String leavingDate,
			OrderType type) throws SQLException, Exception {
		// int h = 0;
		double preOrderPrice = 0.0, uponArrivalPrice = 0.0;
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		DateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date ArrivingAt = dateFormat.parse(arrivingAt);
		java.util.Date LeavingAt = dateFormat.parse(leavingAt);
		java.util.Date ArrivingDate = dateFormat1.parse(arrivingDate);
		java.util.Date LeavingDate = dateFormat1.parse(leavingDate);
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getPrices);
		// stm.setString(1, parkingLot);
		ResultSet res = stm.executeQuery();
		while (res.next()) {
			preOrderPrice = Integer.parseInt(res.getString("preOrderPrice"));
			// System.out.println("pre order price"+preOrderPrice);
			uponArrivalPrice = Integer.parseInt(res.getString("uponArrivalPrice"));
			// System.out.println("upon arrival price"+uporArrivalPrice);
		}

		long diff = 0;
		if (ArrivingAt.before(LeavingAt))
			diff = LeavingAt.getTime() - ArrivingAt.getTime();
		else
			diff = ArrivingAt.getTime() - LeavingAt.getTime();
		System.out.println(diff);
		double diffMinutes = diff / (60 * 1000) % 60;
		double diffHours = diff / (60 * 60 * 1000) % 24;
		if (diffMinutes != 0)
			diffHours++;
		if (LeavingAt.before(ArrivingAt))
			diffHours = -diffHours;

		diff = LeavingDate.getTime() - ArrivingDate.getTime();
		double diffDays = (int) (diff / (1000 * 60 * 60 * 24));

		if (type == OrderType.preOrder) {
			return ((preOrderPrice * (diffHours)) + (preOrderPrice * (diffDays * 24)));
		}

		if (type == OrderType.uponArrivalOrder) {
			return ((uponArrivalPrice * (diffHours)) + (uponArrivalPrice * (diffDays * 24)));
		}

		return -1;
	}

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

	public int getBusinessRegularSubscriptionCost(String customerId) throws NumberFormatException, SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getPrices);
		// stm.setString(1, parkingLot);
		ResultSet res = stm.executeQuery();
		int price1 = 0, price2 = 0;
		while (res.next()) {
			price1 = Integer.parseInt(res.getString("preOrderPrice"));
			price2 = Integer.parseInt(res.getString("regularSubsForMulCars"));
		}
		stm = c.prepareStatement(sqlStatements.Allstatements.getAllBusinessSubsByCustomerId);
		stm.setString(1, customerId);
		res = stm.executeQuery();
		int count = 0;
		while (res.next()) {
			count++;
		}
		return price1 * price2 * count;
	}

	/*
	 * public boolean getCancelOrderCredit(String parkingLot, String customerId,
	 * String arrivingDate, String arrivingAt, String leavingAt, OrderType type)
	 * throws SQLException, Exception { double credit = 0; int Credit; arrivingDate
	 * = arrivingDate + " " + arrivingAt; DateFormat dateFormat = new
	 * SimpleDateFormat("dd-MM-yyyy HH:mm"); java.util.Date Arriving =
	 * dateFormat.parse(arrivingDate); java.util.Date now = new java.util.Date();
	 * long diff = Arriving.getTime() - now.getTime(); long diffHours = diff / (60 *
	 * 60 * 1000) % 24; long diffDays = diff / (24 * 60 * 60 * 1000);
	 * System.out.println("diff hours " + diffHours);
	 * System.out.println("diff days " + diffDays); if (diffDays > 0 || diffHours >
	 * 3) { System.out.println("first if"); credit = -(1 / 10) *
	 * getOrderCost(parkingLot, arrivingAt, leavingAt, type); }
	 * 
	 * else if (diffHours >= 1 && diffHours <= 3) { System.out.println("second if");
	 * System.out.println("price is" + getOrderCost(parkingLot, arrivingAt,
	 * leavingAt, type)); credit = -(1 / 2) * getOrderCost(parkingLot, arrivingAt,
	 * leavingAt, type); } else { System.out.println("third if"); credit =
	 * -getOrderCost(parkingLot, arrivingAt, leavingAt, type); } PreparedStatement
	 * stm = c.prepareStatement(sqlStatements.Allstatements.selectCustomerById);
	 * stm.setString(1, customerId); ResultSet res = stm.executeQuery(); while
	 * (res.next()) { credit += Integer.parseInt(res.getString("credit")); } Credit
	 * = (int) credit;
	 * 
	 * stm =
	 * c.prepareStatement(sqlStatements.Allstatements.updateCreditByCustomerId);
	 * stm.setInt(1, Credit); stm.setString(2, customerId); stm.executeUpdate();
	 * return true; }
	 */

	public int getCancelOrderCredit(Order order) throws SQLException, Exception {
		double credit = 0;
		int Credit;
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
		if (diffDays > 0 || diffHours > 3) {
			System.out.println("first if");
			credit = -(1 / 10) * getOrderCost(arrivingAt, order.getLeavingAt(), arrivingDate, order.getLeavingDate(),
					order.getType());
		}

		else if (diffHours >= 1 && diffHours <= 3) {
			System.out.println("second if");
			System.out.println("price is" + getOrderCost(arrivingAt, order.getLeavingAt(), arrivingDate,
					order.getLeavingDate(), order.getType()));
			credit = -(1 / 2) * getOrderCost(arrivingAt, order.getLeavingAt(), arrivingDate, order.getLeavingDate(),
					order.getType());
		} else {
			System.out.println("third if");
			credit = -getOrderCost(arrivingAt, order.getLeavingAt(), arrivingDate, order.getLeavingDate(),
					order.getType());
		}
		updateCreditByCustomerId(order.getCustomerId(), credit);
		Credit = (int) credit;
		return Credit;
		// return true;
	}

	public int getCreditByCustomerId(String customerId) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.selectCustomerById);
		stm.setString(1, customerId);
		int credit = 0;
		ResultSet res = stm.executeQuery();
		while (res.next()) {
			credit = res.getInt("credit");
		}
		return credit;
	}

	public void updateCreditByCustomerId(String customerId, double newCredit) throws SQLException {
		if (customerId != null) {
			PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.selectCustomerById);
			stm.setString(1, customerId);
			ResultSet res = stm.executeQuery();
			while (res.next()) {
				newCredit += Integer.parseInt(res.getString("credit"));
			}
			int Credit = (int) newCredit;

			stm = c.prepareStatement(sqlStatements.Allstatements.updateCreditByCustomerId);
			stm.setInt(1, Credit);
			stm.setString(2, customerId);
			stm.executeUpdate();
		}
	}

	public ParkingLot getParkingLotByNameFromCPS(String parkingLot) throws SQLException {
		if (parkingLot != null) {
			CPS cps = CPS.getInstance();
			for (ParkingLot i : cps.getParkingLots()) {
				if (i.getName().equals(parkingLot)) {
					System.out.println("found it,it's " + i.getName());
					return i;
				}
			}
		}
		return null;
	}

	public void printParkingSpots(String parkingLot) throws SQLException {
		ParkingLot pl = getParkingLotByNameFromCPS(parkingLot);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				for (int k = 0; k < pl.getWidth(); k++) {
					System.out.println("is Saved= " + pl.getParkingSpots()[i][j][k].isSaved() + " is Faulted= "
							+ pl.getParkingSpots()[i][j][k].isFaulted() + " is Occupied= "
							+ pl.getParkingSpots()[i][j][k].isOccupied());
				}
			}
		}
	}

	public boolean saveParkingSpot(String parkingLot, int row, int column, int width) throws SQLException {
		if (row >= 0 && row <= 2 && column >= 0 && column <= 2) {
			CPS cps = CPS.getInstance();
			for (ParkingLot parkinglot : cps.getParkingLots()) {
				System.out.println(parkinglot.getName());
				if (parkinglot.getName().equals(parkingLot)) {
					ParkingSpot[][][] tmp = parkinglot.getParkingSpots();
					ParkingSpot ps = tmp[row][column][width];
					if (ps.isSaved() == false && ps.isFaulted() == false && ps.isOccupied() == false) {
						parkinglot.setSavedParkingSpot(row, column, width);
						printParkingSpots(parkingLot);
						return true;
					}
				}
			}

		}
		return false;
	}

	public boolean unsaveParkingSpot(String parkingLot, int row, int column, int width) throws SQLException {
		if (row >= 0 && row <= 2 && column >= 0 && column <= 2) {
			CPS cps = CPS.getInstance();
			for (ParkingLot parkinglot : cps.getParkingLots()) {
				if (parkinglot.getName().equals(parkingLot)) {
					ParkingSpot[][][] tmp = parkinglot.getParkingSpots();
					ParkingSpot ps = tmp[row][column][width];
					if (ps.isSaved() == true) {
						parkinglot.unsaveParkingSpot(row, column, width);
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean setFaultedParkingSpot(String parkingLot, int row, int column, int width) throws SQLException {
		if (row >= 0 && row <= 2 && column >= 0 && column <= 2) {
			CPS cps = CPS.getInstance();
			for (ParkingLot parkinglot : cps.getParkingLots()) {
				if (parkinglot.getName().equals(parkingLot)) {
					ParkingSpot[][][] tmp = parkinglot.getParkingSpots();
					ParkingSpot ps = tmp[row][column][width];
					if (ps.isFaulted() == false) {
						parkinglot.setFaultedParkingSpot(row, column, width);
						if (ps.isOccupied() == true) {
							parkinglot.unsetOccupiedParkingSpot(row, column, width);
						}
						if (ps.isSaved() == true) {
							parkinglot.unsaveParkingSpot(row, column, width);
						}
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean unsetFaultedParkingSpot(String parkingLot, int row, int column, int width) throws SQLException {
		if (row >= 0 && row <= 2 && column >= 0 && column <= 2) {
			CPS cps = CPS.getInstance();
			for (ParkingLot parkinglot : cps.getParkingLots()) {
				if (parkinglot.getName().equals(parkingLot)) {
					ParkingSpot[][][] tmp = parkinglot.getParkingSpots();
					ParkingSpot ps = tmp[row][column][width];
					if (ps.isFaulted() == true) {
						parkinglot.unsetFaultedParkingSpot(row, column, width);
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean employeePriceChange(String name, int preOrder, int uponArrival) throws SQLException, Exception {
		String parkingLot = getEmployeeParkingLot(name);
		System.out.println("parking lot " + parkingLot);
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.updatePrices);
		stm.setInt(1, preOrder);
		stm.setInt(2, uponArrival);
		stm.setString(3, parkingLot);
		stm.executeUpdate();
		return true;
	}

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

	public void sendComplaintReplyToMail(String email, String reply) {
		Email.sendEmail(email, "Complaint Reply", reply);
	}

	public ArrayList<Order> getAllPreOrdersByArrivingDate(String arriveDate, String arrivingAt) throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getAllOrdersByArrivingDate);
		stm.setString(1, arriveDate);
		stm.setString(2, arrivingAt);
		ResultSet res = stm.executeQuery();
		ArrayList<Order> allOrders = new ArrayList<Order>();
		Order o = null;
		while (res.next()) {
			OrderType type = (OrderType) res.getObject("type");
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

	public ParkingSpot[][][] getParkingLotImage(String parkingLot) throws SQLException {
		CPS cps = CPS.getInstance();
		if (parkingLot != null) {
			for (ParkingLot i : cps.getParkingLots()) {
				if (i.getName().equals(parkingLot)) {
					return i.getParkingSpots();
				}
			}
		}
		return null;
	}

	public ArrayList<ParkingSpot[][][]> getAllParkingLotsImages() throws SQLException {
		CPS cps = CPS.getInstance();
		ArrayList<ParkingSpot[][][]> parkingSpots = new ArrayList<ParkingSpot[][][]>();
		ArrayList<ParkingLot> parkingLots = cps.getParkingLots();
		for (ParkingLot parkingLot : parkingLots) {
			parkingSpots.add(getParkingLotImage(parkingLot.getName()));
		}
		return parkingSpots;
	}

	public void printAllParkingLots() throws SQLException {
		CPS cps = CPS.getInstance();
		ArrayList<ParkingLot> parkingLots = cps.getParkingLots();
		for (ParkingLot parkingLot : parkingLots) {
			System.out.println(parkingLot.getName());
		}
	}

	/*public boolean insertCarIntoParkingLot(String parkingLot, String vehicleNumber) throws SQLException {
		if (parkingLot != null && vehicleNumber != null) {
			// ParkingLot pl = getParkingLotByNameFromCPS(parkingLot);
			CPS cps = CPS.getInstance();
			PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.selectVehicleByVehicleNum);
			stm.setString(1, vehicleNumber);
			ResultSet res = stm.executeQuery();
			while (res.next()) {
				if (res.getInt("row") == -1 && res.getInt("column") == -1 && res.getInt("width") == -1) {
					for (ParkingLot currParkingLot : cps.getParkingLots()) {
						if (currParkingLot.getName().equals(parkingLot)) {
							for (int i = 0; i < 3; i++) {
								for (int j = 0; j < 3; j++) {
									for (int k = 0; k < currParkingLot.getWidth(); k++) {
										ParkingSpot tmp = currParkingLot.getParkingSpots()[i][j][k];
										if (tmp.isFaulted() == false && tmp.isOccupied() == false
												&& tmp.isSaved() == false) {
											tmp.setOccupied(true);
											setParkingSpotAsOccupied(parkingLot, vehicleNumber, i, j, k);
											return true;
										}
									}
								}
							}

						}
					}

				}
			}
		}
		return false;
	}
*/
/*	public void setParkingSpotAsOccupied(String parkingLot, String vehicleNumber, int row, int column, int width)
			throws SQLException {
	//	TODO: THIS
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.enterVehicleIntoParkingLot);
		stm.setInt(1, row);
		stm.setInt(2, column);
		stm.setInt(3, width);
		stm.setString(4, parkingLot);
		stm.setString(5, vehicleNumber);
		stm.executeUpdate();
	}*/

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
			if (isChecked == false && (diffDays > 1))
				return true;
		}
		return false;
	}
	
	ArrayList<Order> getAllVehiclesCurrentlyInParkingLot(String parkingLot) throws SQLException{
		PreparedStatement stm=c.prepareStatement(sqlStatements.Allstatements.getAllVehiclesCurrentlyInParkingLot);
		stm.setString(1, parkingLot);
		ResultSet res=stm.executeQuery();
		ArrayList<Order> orders=new ArrayList<Order>();
		while(res.next()) {
			Order o=new Order(res.get, type, parkingLot, arrivingDate, leavingDate, arrivingAt, leavingAt, customerId, vehicleNum, arrivingLate, leavingLate, isCanceled)
		}
	}

}