package sqlStatements;

public class Allstatements {

	public final static String selectParkingLotById = "SELECT * from fur_seal_schema.parkingLots where parkingLotId=?;";
	public final static String selectOrderById = "select * from fur_seal_schema.orders where orderID=?;";
	public final static String selectCustomerById = "select * from fur_seal_schema.customers where customerID=?;";
	public final static String selectVehicleById = "select * from fur_seal_schema.vehicles where vehicleNumber=?;";
	public final static String selectVehicleByOrderId = "select * from fur_seal_schema.vehicles where orderID=?;";
	public final static String selectParkingLotByName = "SELECT * from fur_seal_schema.parkingLots where name=?;";
	
	
	
	public final static String addNewParkingLot = "INSERT INTO fur_seal_schema.parkingLots (name,location,isActive,available,manager)"+"values(?,?,?,?,?);";
	public final static String addNewOrder = "INSERT INTO fur_seal_schema.orders VALUES(?,?,?,?,?,?,?,?)";
	public final static String addNewCustomer = "INSERT INTO fur_seal_schema.customers VALUES(?,?)";
	public final static String addNewVehicle = "INSERT INTO fur_seal_schema.vehicles VALUES(?,?,?,?)";
	public final static String deleteParkingLot="DELETE FROM `fur_seal_schema`.`parkingLots` WHERE `name`=?;";
	
	
	
    public final static String getAllParkingLots="SELECT * FROM fur_seal_schema.parkingLots;";
}
