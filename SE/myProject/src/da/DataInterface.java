package da;
import java.sql.SQLException;
import java.util.ArrayList;

import allClasses.Customer;
import allClasses.Order;
import allClasses.ParkingLot;
import allClasses.Vehicle;

public interface  DataInterface {
	
	public boolean AddParkingLot(ParkingLot p) throws SQLException;
	//public boolean AddOrder(Order order, Customer c , Vehicle v ,ParkingLot p ) throws SQLException;
	public boolean deleteParkingLot(String nameOfParkingLot) throws SQLException;
	public ArrayList<ParkingLot> GetAllParkingLots() throws SQLException;
	public int getParkingIdLotByName(String name) throws SQLException;

}
