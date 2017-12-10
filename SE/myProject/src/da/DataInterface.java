package da;
import java.sql.SQLException;

import allClasses.Customer;
import allClasses.Order;
import allClasses.ParkingLot;
import allClasses.Vehicle;

public interface  DataInterface {
	
	public boolean AddParkingLot(ParkingLot p) throws SQLException;
	public boolean AddOrder(Order order, Customer c , Vehicle v ,ParkingLot p ) throws SQLException;

	
	

}
