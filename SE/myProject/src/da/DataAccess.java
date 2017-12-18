package da;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import allClasses.Customer;
import allClasses.Order;
import allClasses.ParkingLot;
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

	public boolean AddParkingLot(ParkingLot p) throws SQLException {
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
			stm1.setBoolean(4, p.isAvailable());
			stm1.setString(5, p.getManager());

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
		PreparedStatement stm=c.prepareStatement(sqlStatements.Allstatements.selectParkingLotByName);
		stm.setString(1, name);
		ResultSet rs=stm.executeQuery();
		int id=0;
		while(rs.next()) {
			id=rs.getInt("parkingLotId");
			return id;
		}
		return 0;	
	}
	
	
	@Override
	/*public boolean AddOrder(Order order, Customer customer, Vehicle v, ParkingLot p) throws SQLException {

			PreparedStatement stm1 = c.prepareStatement(sqlStatements.Allstatements.selectParkingLotById);
			stm1.setInt(1, p.getId());
			rs = stm1.executeQuery();

			if (rs.next()) {
				// adding order info into orders table
				stm1 = c.prepareStatement(sqlStatements.Allstatements.addNewOrder);
				stm1.setInt(1, order.getId());
				stm1.setInt(2, order.isType().getValue());
				stm1.setInt(3, p.getId());
				stm1.setString(4, order.getArrivingAt());
				stm1.setString(5, order.getLeavingAt());
				stm1.setString(6, order.getChargement());
				stm1.setString(7, order.getCompensation());
				stm1.setString(8, order.getSubscriptionDate());

				stm1.executeUpdate();

				// adding new customer into customers table in case customer doesn't exist

				stm1 = c.prepareStatement(sqlStatements.Allstatements.selectCustomerById);
				stm1.setString(1, customer.getId());
				rs = stm1.executeQuery();
				if (!rs.next()) {
					System.out.println("fffffff");
					stm1 = c.prepareStatement(sqlStatements.Allstatements.addNewCustomer);
					stm1.setString(1, customer.getId());
					stm1.setString(2, customer.getEmail());
					stm1.executeUpdate();
				}

				stm1 = c.prepareStatement(sqlStatements.Allstatements.selectVehicleByOrderId);
				stm1.setInt(1, order.getId());
				rs = stm1.executeQuery();
				if (!rs.next()) {
					System.out.println("gggggggg");
					stm1 = c.prepareStatement(sqlStatements.Allstatements.addNewVehicle);
					stm1.setString(1, v.getVehicleNumber());
					stm1.setString(2, customer.getId());
					stm1.setInt(3, order.getId());
					stm1.setBoolean(4, v.isLate());
					stm1.executeUpdate();
				}

				System.out.println("order added to database");
				return true;
			} else
				return false;
		}
	}
*/
	public ArrayList<ParkingLot> GetAllParkingLots() throws SQLException {
		PreparedStatement stm = c.prepareStatement(sqlStatements.Allstatements.getAllParkingLots);
		ArrayList<ParkingLot> allParkingLots = new ArrayList<ParkingLot>();
		ParkingLot p = null;
		ResultSet rs = stm.executeQuery();
		while (rs.next()) {
			p = new ParkingLot( rs.getString("name"), rs.getString("location"),
					rs.getBoolean("isActive"), rs.getBoolean("available"), rs.getString("manager"));
			allParkingLots.add(p);
		}
		return allParkingLots;
	}

}
