package da;
import java.sql.SQLException;

import allClasses.ParkingLot;

public interface  DataInterface {
	
	public boolean AddParkingLot(ParkingLot p) throws SQLException;
	

}
