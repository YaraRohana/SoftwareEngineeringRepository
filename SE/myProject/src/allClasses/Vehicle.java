package allClasses;

public class Vehicle {

	String vehicleNumber;
	String customerId;

	public Vehicle(String vehicleNumber, String customerId) {
		super();
		this.vehicleNumber = vehicleNumber;
		this.customerId = customerId;

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

	@Override
	public String toString() {
		return "Vehicle [vehicleNumber=" + vehicleNumber + ", customerId=" + customerId + "]";
	}

}
