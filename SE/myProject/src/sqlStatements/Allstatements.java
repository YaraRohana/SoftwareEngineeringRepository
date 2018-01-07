package sqlStatements;

public class Allstatements {

	//public final static String selectParkingLotById = "SELECT * from fur_seal_schema.parkingLots where parkingLotId=?;";
	public final static String selectCustomerById = "select * from fur_seal_schema.customers where customerID=?;";
	public final static String selectVehicleByVehicleNum = "select * from fur_seal_schema.vehicles where vehicleNumber=?;";
	public final static String selectVehicleByOrderId = "select * from fur_seal_schema.vehicles where orderID=?;";
	public final static String selectParkingLotByName = "SELECT * from fur_seal_schema.parkingLots where name=?;";

	public final static String checkIfOrderExists = "select * from fur_seal_schema.orders where parkingLot=? AND vehicleNumber=? AND customerId=?";
	public final static String checkIfVehicleExists="select * from fur_seal_schema.vehicles where vehicleNumber=?";
	public final static String checkIfEmployeeExists="select * from fur_seal_schema.employees where name=? AND password=?";
	
	public final static String addNewParkingLot = "INSERT INTO fur_seal_schema.parkingLots (name,location,isActive,isFull,manager,width)"
			+ "values(?,?,?,?,?,?);";
	public final static String addNewOrder = "INSERT INTO `fur_seal_schema`.`orders` (`type`,`parkingLot`, `arrivingDate`, `arrivingAt`, `leavingDate`, `leavingAt`, `customerId`, `vehicleNumber`, `arrivingLate`, `leavingLate`, `canceled`) VALUES (?,?, ?, ?, ?, ?, ?, ?, '0', '0', '0')";
	public final static String addNewCustomer = "INSERT INTO `fur_seal_schema`.`customers` (`customerID`, `email`,`credit`,`isConnected`) VALUES (?,?,?,?);";
	public final static String addNewVehicle = "INSERT INTO `fur_seal_schema`.`vehicles` (`vehicleNumber`, `customerID`,`row`,`column`,`width`) VALUES (?,?,'-1','-1','-1');";
	public final static String addNewComplaint = "INSERT INTO `fur_seal_schema`.`complaints` (`parkingLot`, `customerID`, `submissionDate`, `isChecked`, `text`) VALUES (?,?,?,?,?);";
	public final static String deleteParkingLot = "DELETE FROM `fur_seal_schema`.`parkingLots` WHERE `name`=?;";

	public final static String getAllParkingLots = "SELECT * FROM fur_seal_schema.parkingLots;";
	public final static String getAllCustomers = "SELECT * FROM fur_seal_schema.customers;";
	public final static String getAllOrders = "SELECT * FROM fur_seal_schema.orders;";
	public final static String getAllVehicles = "SELECT * FROM fur_seal_schema.vehicles;";
	public final static String getAllComplaints = "SELECT * FROM fur_seal_schema.complaints;";
	
	public final static String setupParkingLot="UPDATE `fur_seal_schema`.`parkingLots` SET `width`=? WHERE `name`=?";
	
	public final static String getAlOrdersByCustomerId = "select * from fur_seal_schema.orders where customerId=?;";
	public final static String getAllOrdersByVehicleNum = "select * from fur_seal_schema.orders where vehicleNumber=?;";
	public final static String getAllRegularSubsByCustomerId="select * from fur_seal_schema.regularSubscriptions where customerId=?";
	public final static String getAllFullSubsByCustomerId="select * from fur_seal_schema.fullSubscriptions where customerId=?;";
	public final static String addFullSubscription = "INSERT INTO `fur_seal_schema`.`fullSubscription` (`customerId`, `subscriptionId`, `vehicleNumber`, `startingDate`, `arrivedSince`) VALUES (?,?,?,?,?);";
	public final static String addOneCarRegularSubscription = "INSERT INTO `fur_seal_schema`.`regularSubscriptions` (`customerId`,`subscriptionId`, `vehicleNumber`, `startDate`, `type`, `parkingLot`, `leavingAt`, `email`) VALUES (?,'0',?,?,'oneCar',?,?,?);";
	public final static String addBusinessRegularSubscription = "INSERT INTO `fur_seal_schema`.`regularSubscriptions` (`customerId`,`subscriptionId`, `vehicleNumber`, `startDate`, `type`, `parkingLot`, `leavingAt`, `email`) VALUES (?,?,?,?,'business',?,?,?);";
	public final static String cancelOrder="UPDATE `fur_seal_schema`.`orders` SET `canceled`='1' WHERE `parkingLot`=? AND `vehicleNumber`=? AND `customerId`=? and `arrivingDate`=? and `arrivingAt`=? and `leavingDate`=? and `leavingAt`=?";
	public final static String checkIfOrderExistsByAllParameters="select * from fur_seal_schema.orders where `parkingLot`=? AND `vehicleNumber`=? AND `customerId`=? and `arrivingDate`=? and `arrivingAt`=? and `leavingDate`=? and `leavingAt`=?";

}
