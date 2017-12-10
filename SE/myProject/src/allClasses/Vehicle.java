package allClasses;

public class Vehicle {

	String vehicleNumber;
	boolean isLate;
	public Vehicle(String vehicleNumber, boolean isLate) {
		super();
		this.vehicleNumber = vehicleNumber;
		this.isLate = isLate;
	}
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	public boolean isLate() {
		return isLate;
	}
	public void setLate(boolean isLate) {
		this.isLate = isLate;
	}
	
	
	
	
	
}
