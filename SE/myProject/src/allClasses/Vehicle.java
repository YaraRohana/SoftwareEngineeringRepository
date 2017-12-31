package allClasses;

public class Vehicle {

	String vehicleNumber;
	String customerId;
	int row;
	int column;
	int width;
	
	public Vehicle(String vehicleNumber, String customerId) {
		super();
		this.vehicleNumber = vehicleNumber;
		this.customerId = customerId;
		
	}
	
	
	public Vehicle(String vehicleNumber, String customerId, int row, int column, int width) {
		super();
		this.vehicleNumber = vehicleNumber;
		this.customerId = customerId;
		this.row = row;
		this.column = column;
		this.width = width;
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
	
	
	public int getRow() {
		return row;
	}


	public void setRow(int row) {
		this.row = row;
	}


	public int getColumn() {
		return column;
	}


	public void setColumn(int column) {
		this.column = column;
	}


	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
	}


	@Override
	public String toString() {
		return "Vehicle [vehicleNumber=" + vehicleNumber + ", customerId=" + customerId + "]";
	}	
	
}
