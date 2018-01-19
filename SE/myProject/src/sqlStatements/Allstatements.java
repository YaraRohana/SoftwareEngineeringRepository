package sqlStatements;

public class Allstatements {

	// public final static String selectParkingLotById = "SELECT * from
	// fur_seal_schema.parkingLots where parkingLotId=?;";
	public final static String selectCustomerById = "select * from fur_seal_schema.customers where customerID=?;";
	public final static String selectVehicleByVehicleNum = "select * from fur_seal_schema.vehicles where vehicleNumber=?;";
	public final static String selectVehicleByOrderId = "select * from fur_seal_schema.vehicles where orderID=?;";
	public final static String selectParkingLotByName = "SELECT * from fur_seal_schema.parkingLots where name=?;";

	public final static String checkIfOrderExists = "select * from fur_seal_schema.orders where parkingLot=? AND vehicleNumber=? AND customerId=?";
	// public final static String checkIfVehicleExists="select * from
	// fur_seal_schema.vehicles where vehicleNumber=?";
	public final static String checkIfEmployeeExists = "select * from fur_seal_schema.employees where name=? AND password=?";
	public final static String getEmployeeByName = "select * from fur_seal_schema.employees where name=?";
	public final static String addNewParkingLot = "INSERT INTO fur_seal_schema.parkingLots (name,location,isActive,isFull,manager,width)"
			+ "values(?,?,?,?,?,?);";
	public final static String addNewOrder = "INSERT INTO `fur_seal_schema`.`orders` (`type`,`parkingLot`, `arrivingDate`, `arrivingAt`, `leavingDate`, `leavingAt`, `customerId`, `vehicleNumber`, `arrivingLate`, `leavingLate`, `canceled`) VALUES (?,?, ?, ?, ?, ?, ?, ?, '0', '0', '0')";
	public final static String addNewCustomer = "INSERT INTO `fur_seal_schema`.`customers` (`customerID`, `email`,`credit`,`isConnected`) VALUES (?,?,?,?);";
	public final static String addNewVehicle = "INSERT INTO `fur_seal_schema`.`vehicles` (`vehicleNumber`, `customerID`) VALUES (?,?);";
	public final static String addNewComplaint = "INSERT INTO `fur_seal_schema`.`complaints` (`parkingLot`, `customerID`, `submissionDate`, `isChecked`, `text`) VALUES (?,?,?,'0',?);";
	public final static String deleteParkingLot = "DELETE FROM `fur_seal_schema`.`parkingLots` WHERE `name`=?;";

	public final static String getAllParkingLots = "SELECT * FROM fur_seal_schema.parkingLots;";
	public final static String getAllCustomers = "SELECT * FROM fur_seal_schema.customers;";
	public final static String getAllOrders = "SELECT * FROM fur_seal_schema.orders;";
	public final static String getAllVehicles = "SELECT * FROM fur_seal_schema.vehicles;";
	public final static String getAllComplaints = "SELECT * FROM fur_seal_schema.complaints;";

	public final static String setupParkingLot = "UPDATE `fur_seal_schema`.`parkingLots` SET `width`=? WHERE `name`=?";

	public static final String getComplaintByCustomerId = "select * from fur_seal_schema.complaints where customerID=?;";

	public final static String getAlOrdersByCustomerId = "select * from fur_seal_schema.orders where customerId=?;";
	public final static String getAllOrdersByVehicleNum = "select * from fur_seal_schema.orders where vehicleNumber=?;";
	public final static String getAllRegularSubsByCustomerId = "select * from fur_seal_schema.regularSubscriptions where customerId=?";
	public final static String getAllBusinessSubsByCustomerId = "select * from fur_seal_schema.regularSubscriptions where customerId=? and type='business';";

	public final static String getAllFullSubsByCustomerId = "select * from fur_seal_schema.fullSubscriptions where customerId=?;";
	public final static String addFullSubscription = "INSERT INTO `fur_seal_schema`.`fullSubscriptions` (`customerId`, `subscriptionId`, `vehicleNumber`, `startingDate`, `arrivedSince`) VALUES (?,?,?,?,?);";
	public final static String addOneCarRegularSubscription = "INSERT INTO `fur_seal_schema`.`regularSubscriptions` (`customerId`,`subscriptionId`, `vehicleNumber`, `startDate`, `type`, `parkingLot`, `leavingAt`, `email`) VALUES (?,?,?,?,'oneCar',?,?,?);";
	public final static String addBusinessRegularSubscription = "INSERT INTO `fur_seal_schema`.`regularSubscriptions` (`customerId`,`subscriptionId`, `vehicleNumber`, `startDate`, `type`, `parkingLot`, `leavingAt`, `email`) VALUES (?,?,?,?,'business',?,?,?);";
	public final static String cancelOrder = "UPDATE `fur_seal_schema`.`orders` SET `canceled`='1' WHERE `parkingLot`=? AND `vehicleNumber`=? AND `customerId`=? and `arrivingDate`=? and `arrivingAt`=? and `leavingDate`=? and `leavingAt`=?";
	public final static String checkIfOrderExistsByAllParameters = "select * from fur_seal_schema.orders where `parkingLot`=? AND `vehicleNumber`=? AND `customerId`=? and `arrivingDate`=? and `arrivingAt`=? and `leavingDate`=? and `leavingAt`=?";

	public final static String logOutEmployee = "UPDATE `fur_seal_schema`.`employees` SET `isConnected`='0' WHERE `name`=?";
	public final static String logOutCustomer = "UPDATE `fur_seal_schema`.`customers` SET `isConnected`='0' WHERE `customerID`=?;";
	public final static String logInEmployee = "UPDATE `fur_seal_schema`.`employees` SET `isConnected`='1' WHERE `name`=? and `password`=?";
	public final static String logInCustomer = "UPDATE `fur_seal_schema`.`customers` SET `isConnected`='1' WHERE `customerID`=?;";
	public final static String updateCreditByCustomerId = "UPDATE `fur_seal_schema`.`customers` SET `credit`=? WHERE `customerID`=?;";

	public final static String getPrices = "select * from fur_seal_schema.prices where ID='1';";
	public final static String updatePrices = "UPDATE `fur_seal_schema`.`prices` SET `preOrderPrice`=?, `uponArrivalPrice`=? WHERE `parkingLot`=?;";

	public final static String getAllOrdersByArrivingDate = "select * from fur_seal_schema.orders where arrivingDate=? AND arrivingAt=? AND canceled='0';";
	public final static String getAllRegularSubsByStartingDate = "select * from fur_seal_schema.regularSubscriptions where startDate=?;";
	public final static String getAllFullSubsByStartingDate = "select * from fur_seal_schema.fullSubscriptions where startingDate=?;";
	public final static String getAllFullSubsByArrivedSince = "select * from fur_seal_schema.fullSubscriptions where arrivedSince=?;";
	// public final static String enterVehicleIntoParkingLot="UPDATE
	// `fur_seal_schema`.`vehicles` SET `row`=?, `column`=? ,
	// `width`=?,`parkingLot`=? WHERE vehicleNumber=?";
	public final static String getAllVehiclesCurrentlyInParkingLot = "select * from fur_seal_schema.vehiclesInParkingLots where parkingLot=?";
	public final static String setComplaintAsChecked = "UPDATE `fur_seal_schema`.`complaints` SET `isChecked`='1' WHERE `customerID`=?";
	// public final static String getCustomerByIdFromComplaints="select * from
	// `fur_seal_schema`.complaints where customerId=?";
	public final static String getNumberOfExecutedOrdersByParkingLot = "select * from `fur_seal_schema`.`orders` where parkingLot=? and canceled='0'";
	public final static String getNumberOfCanceledOrdersByParkingLot = "select * from `fur_seal_schema`.`orders` where parkingLot=? and canceled='1'";
	public final static String getNumberOfLateArrivalOrdersByParkingLot = "select * from `fur_seal_schema`.`orders` where parkingLot=? and arrivingLate='1'";
	public final static String setParkingSpotAsFaulted = "INSERT INTO `fur_seal_schema`.`faultedAndSavedParkingSpots` (`parkingLot`, `row`, `col`, `width`, `isSaved`, `isFaulted`) VALUES (?, ?, ?, ?, '0', '1')";
	public final static String setParkingSpotAsSaved = "INSERT INTO `fur_seal_schema`.`faultedAndSavedParkingSpots` (`parkingLot`, `row`, `col`, `width`, `isSaved`, `isFaulted`) VALUES (?, ?, ?, ?, '1', '0')";
	public final static String unsetFaultedOrSavedParkingSpot="DELETE FROM `fur_seal_schema`.`faultedAndSavedParkingSpots` WHERE `num`=?";
	public final static String getParkingSpotStatusFaultedOrSaved="select * from `fur_seal_schema`.`faultedAndSavedParkingSpots` where `parkingLot`=? and `row`=? and `col`=? and `width`=?";
	public final static String getParkingSpotStatusOccupuied="select * from `fur_seal_schema`.`occupiedParkingSpotsInAllParkingLots` where `parkingLot`=? and `row`=? and `column`=? and `width`=?";

	public final static String getVehicleByParkingLotAndSpot="select * from fur_seal_schema.occupiedParkingSpotsInAllParkingLots where `parkingLot`=? AND `row`=? AND `column`=? AND `width`=?";
	public final static String enterVehicleIntoParkingLot="INSERT INTO `fur_seal_schema`.`occupiedParkingSpotsInAllParkingLots` (`vehicle`, `customerId`, `parkingLot`, `row`, `column`, `width`, `leavingDate`, `leavingAt` , `type`) VALUES (?,?,?,?,?,?,?,?,?);";
	public final static String setFullSubscripsionArrivedSince="UPDATE `fur_seal_schema`.`fullSubscriptions` SET `arrivedSince`=? WHERE `costumerId`=? `vehicleNumber`=?;";
	public final static String removeVehicleFromParkingLot ="DELETE FROM `fur_seal_schema`.`occupiedParkingSpotsInAllParkingLots` WHERE `vehicle`=? AND `customerId`=?  AND `parkingLot`=?  AND `row`=?  AND `column`=?  AND `width`=? ;";
	public final static String changeCarParkingSpot ="UPDATE `fur_seal_schema`.`occupiedParkingSpotsInAllParkingLots` SET `row`=? WHERE `vehicle`=? AND `parkingLot`=? AND `column`=? AND `width`=?;";
	public final static String checkIfOrderExistsByLess = "select * from fur_seal_schema.orders where parkingLot=? AND vehicleNumber=?";
	public final static String getAllFullSubsByVehicleNumber="select * from fur_seal_schema.fullSubscriptions where vehicleNumber=?;";
	public final static String getAllRegularSubsByVehicleNumber="select * from fur_seal_schema.regularSubscriptions where vehicleNumber=?";
	public final static String setArrivingLate="UPDATE `fur_seal_schema`.`orders` SET `arrivingLate`='1' WHERE `orderID`=? and`customerId`=? and`vehicleNumber`=?;";
	public final static String setLeavingLate="UPDATE `fur_seal_schema`.`orders` SET `leavingLate`='1' WHERE `customerId`=? and`vehicleNumber`=? and`parkingLot`=? and`leavingDate`=? and`leavingAt`=?;";
	public final static String getVehicleFromOccupiedParkingSpots="select * from fur_seal_schema.occupiedParkingSpotsInAllParkingLots where vehicle=? and parkingLot=?;";
	public final static String getOrderByNotArriving="select * from fur_seal_schema.orders where `parkingLot`=? AND `vehicleNumber`=? AND `customerId`=? and `leavingDate`=? and `leavingAt`=?";
	public final static String updateRowByRestAndVehicle="UPDATE `fur_seal_schema`.`occupiedParkingSpotsInAllParkingLots` SET `row`=? WHERE `vehicle`=? and `parkingLot`=? and `column`=? and `width`=?;";
	public final static String deleteOrder = "DELETE FROM `fur_seal_schema`.`orders` WHERE `parkingLot`=? AND `vehicleNumber`=? AND `customerId`=? and `arrivingDate`=? and `arrivingAt`=? and `leavingDate`=? and `leavingAt`=?";
	public final static String checkIfVehicleParking="select * from fur_seal_schema.occupiedParkingSpotsInAllParkingLots where vehicle=?";
	public final static String updateArrivedSinceInFullSub="UPDATE `fur_seal_schema`.`fullSubscriptions` SET `arrivedSince`=? WHERE `subscriptionId`=? and `customerId`=? ;";
	public final static String getNumOfSubsByParkingLot="select * from `fur_seal_schema`.`regularSubscriptions` where parkingLot=?";
	public final static String getAllOrdersByParkingLot = "select * from fur_seal_schema.orders where parkingLot=?;";
	public final static String getAllFullSubsByParkingLot = "select * from fur_seal_schema.fullSubscriptions where customerId=?;";
	public final static String getAllRegularSubsByParkingLot = "select * from fur_seal_schema.regularSubscriptions where parkingLot=?";
	public final static String getAllComplaintsByParkingLot = "SELECT * FROM fur_seal_schema.complaints where parkingLot=?;";
	public final static String addParkingSpotAsFaultedInHistory="INSERT INTO `fur_seal_schema`.`faultedParkingSpotsHistory` (`parkingLot`, `row`, `col`, `width`, `date`) VALUES (?, ?, ?, ?, ?);";
	public final static String getFaultedParkingSpotsHistoryByParkingLot="select * from `fur_seal_schema`.`faultedParkingSpotsHistory` where parkingLot=?";
	public final static String getOrderByOrderID="select * from `fur_seal_schema`.`orders` where orderID=?";
	public final static String cancelOrderByOrderId="UPDATE `fur_seal_schema`.`orders` SET `canceled`='1' where orderId=?";

}
