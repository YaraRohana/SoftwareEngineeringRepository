package allClasses;

public class Vehicle {

	String vehicleNumber;
	String customerId;
	boolean arrivingLate;
	boolean leavingLate;
	
	
	public Vehicle(String vehicleNumber, String customerId, boolean arrivingLate, boolean leavingLate) {
		super();
		this.vehicleNumber = vehicleNumber;
		this.customerId = customerId;
		this.arrivingLate = arrivingLate;
		this.leavingLate = leavingLate;
	}
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public boolean isArrivingLate() {
		return arrivingLate;
	}
	public void setArrivingLate(boolean arrivingLate) {
		this.arrivingLate = arrivingLate;
	}
	public boolean isLeavingLate() {
		return leavingLate;
	}
	public void setLeavingLate(boolean leavingLate) {
		this.leavingLate = leavingLate;
	}
	@Override
	public String toString() {
		return "Vehicle [vehicleNumber=" + vehicleNumber + ", customerId=" + customerId + ", arrivingLate="
				+ arrivingLate + ", leavingLate=" + leavingLate + "]";
	}	
	
}
