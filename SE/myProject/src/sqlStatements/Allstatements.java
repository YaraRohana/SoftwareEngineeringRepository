package sqlStatements;

public class Allstatements {

	public final static String selectParkingLotById = "select * from fur_seal_schema.parkingLots where parkingLotId=?;";
	public final static String addNewParkingLot = "INSERT INTO fur_seal_schema.parkingLots VALUES(?,?,?,?,?,?)";
	
}
