package sqlStatements;

public class Allstatements {

	//public final static String selectParkingLotById = "SELECT * from fur_seal_schema.parkingLots where parkingLotId=?;";
	public final static String selectCustomerById = "select * from fur_seal_schema.customers where customerID=?;";
	public final static String selectVehicleByVehicleNum = "select * from fur_seal_schema.vehicles where vehicleNumber=?;";
	public final static String selectVehicleByOrderId = "select * from fur_seal_schema.vehicles where orderID=?;";
	public final static String selectParkingLotByName = "SELECT * from fur_seal_schema.parkingLots where name=?;";

	public final static String checkIfOrderExists = "select * from fur_seal_schema.orders where parkingLot=? AND vehicleNumber=? AND customerId=?";
	public final static String checkIfVehicleExists="select * from fur_seal_schema.vehicles where vehicleNumber=?";
	
	public final static String addNewParkingLot = "INSERT INTO fur_seal_schema.parkingLots (name,location,isActive,isFull,manager,width)"
			+ "values(?,?,?,?,?,?);";
	public final static String addNewOrder = "INSERT INTO `fur_seal_schema`.`orders` (`orderID`, `type`, `parkingLot`, `arrivingAt`, `leavingAt`, `customerId`, `vehicleNumber`, `arrivingLate`, `leavingLate`) VALUES ('0',?,?,?,?,?,?, '0','0');";
	public final static String addNewCustomer = "INSERT INTO `fur_seal_schema`.`customers` (`customerID`, `email`, `password`) VALUES (?,?,?);";
	public final static String addNewVehicle = "INSERT INTO `fur_seal_schema`.`vehicles` (`vehicleNumber`, `customerID`,`row`,`column`,`width`) VALUES (?,?,'-1','-1','-1');";
	public final static String addNewComplaint = "INSERT INTO `fur_seal_schema`.`complaints` (`parkingLot`, `customerID`, `submissionDate`, `isChecked`, `text`) VALUES (?,?,?,?,?);";
	public final static String deleteParkingLot = "DELETE FROM `fur_seal_schema`.`parkingLots` WHERE `name`=?;";

	public final static String getAllParkingLots = "SELECT * FROM fur_seal_schema.parkingLots;";
	public final static String getAllCustomers = "SELECT * FROM fur_seal_schema.customers;";
	public final static String getAllOrders = "SELECT * FROM fur_seal_schema.orders;";
	public final static String getAllVehicles = "SELECT * FROM fur_seal_schema.vehicles;";

	public final static String getAlOrdersByCustomerId = "select * from fur_seal_schema.orders where customerId=?;";
	public final static String getAllOrdersByVehicleNum = "select * from fur_seal_schema.orders where vehicleNumber=?;";
	public final static String getAllRegularSubsByCustomerId="select * from fur_seal_schema.regularSubscriptions where customerId=?";
	public final static String getAllFullSubsByCustomerId="select * from fur_seal_schema.fullSubscriptions where customerId=?;";
	public final static String addFullSubscription = "INSERT INTO `fur_seal_schema`.`fullSubscription` (`customerId`, `subscriptionId`, `vehicleNumber`, `startingDate`, `arrivedSince`) VALUES (?,?,?,?,?);";
	public final static String addRegularSubscription = "";

}
