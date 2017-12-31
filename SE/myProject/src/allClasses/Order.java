package allClasses;



public class Order {
	
	public enum OrderType
	{
		uponArrivalOrder(0) , preOrder(1);
		
		private final int value;
	    private OrderType(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }
	}

	int orderId;
	OrderType type;
	String parkingLot;//maybe should be String parkingLot?
	String arrivingAt;
	String leavingAt;
	String customerId;
	String vehicleNum;
	boolean arrivingLate;
	boolean leavingLate;
	
	
	public Order(int orderId, OrderType type, String parkingLot, String arrivingAt, String leavingAt, String customerId,
			String vehicleNum) {
		super();
		this.orderId = orderId;
		this.type = type;
		this.parkingLot = parkingLot;
		this.arrivingAt = arrivingAt;
		this.leavingAt = leavingAt;
		this.customerId = customerId;
		this.vehicleNum = vehicleNum;
		this.arrivingLate=false;
		this.leavingLate=false;
	}


	public int getOrderId() {
		return orderId;
	}


	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}


	public OrderType getType() {
		return type;
	}


	public void setType(OrderType type) {
		this.type = type;
	}


	public String getParkingLot() {
		return parkingLot;
	}


	public void setParkingLot(String parkingLot) {
		this.parkingLot = parkingLot;
	}


	public String getArrivingAt() {
		return arrivingAt;
	}


	public void setArrivingAt(String arrivingAt) {
		this.arrivingAt = arrivingAt;
	}


	public String getLeavingAt() {
		return leavingAt;
	}


	public void setLeavingAt(String leavingAt) {
		this.leavingAt = leavingAt;
	}


	public String getCustomerId() {
		return customerId;
	}


	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}


	public String getVehicleNum() {
		return vehicleNum;
	}


	public void setVehicleNum(String vehicleNum) {
		this.vehicleNum = vehicleNum;
	}


	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", type=" + type + ", parkingLotId=" + parkingLot + ", arrivingAt="
				+ arrivingAt + ", leavingAt=" + leavingAt + ", customerId=" + customerId + ", vehicleNum=" + vehicleNum
				+ "]";
	}

}
