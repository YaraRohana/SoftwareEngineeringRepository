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
	String arrivingDate;
	String leavingDate;
	String arrivingAt;
	String leavingAt;
	String customerId;
	String vehicleNum;
	boolean arrivingLate;
	boolean leavingLate;
	boolean isCanceled;
	
	public Order(int orderId, OrderType type, String parkingLot, String arrivingDate, String leavingDate,
			String arrivingAt, String leavingAt, String customerId, String vehicleNum, boolean arrivingLate,
			boolean leavingLate, boolean isCanceled) {
		super();
		this.orderId = orderId;
		this.type = type;
		this.parkingLot = parkingLot;
		this.arrivingDate = arrivingDate;
		this.leavingDate = leavingDate;
		this.arrivingAt = arrivingAt;
		this.leavingAt = leavingAt;
		this.customerId = customerId;
		this.vehicleNum = vehicleNum;
		this.arrivingLate = arrivingLate;
		this.leavingLate = leavingLate;
		this.isCanceled = isCanceled;
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

	public String getArrivingDate() {
		return arrivingDate;
	}

	public void setArrivingDate(String arrivingDate) {
		this.arrivingDate = arrivingDate;
	}

	public String getLeavingDate() {
		return leavingDate;
	}

	public void setLeavingDate(String leavingDate) {
		this.leavingDate = leavingDate;
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

	public boolean isCanceled() {
		return isCanceled;
	}

	public void setCanceled(boolean isCanceled) {
		this.isCanceled = isCanceled;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", type=" + type + ", parkingLot=" + parkingLot + ", arrivingDate="
				+ arrivingDate + ", leavingDate=" + leavingDate + ", arrivingAt=" + arrivingAt + ", leavingAt="
				+ leavingAt + ", customerId=" + customerId + ", vehicleNum=" + vehicleNum + ", arrivingLate="
				+ arrivingLate + ", leavingLate=" + leavingLate + ", isCanceled=" + isCanceled + "]";
	}
	
	


}
