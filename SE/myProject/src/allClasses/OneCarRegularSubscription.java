package allClasses;

import java.sql.Date;

public class OneCarRegularSubscription extends Subscription{

	subscriptionType type=subscriptionType.oneCarRegularSubscription;
	String parkingLot;
	String leavingAt;
	
	
	public OneCarRegularSubscription(String customerId, String subsciptionId, String vehicleNumber, Date startDate,
			String email, subscriptionType type, String parkingLot, String leavingAt) {
		super(customerId, subsciptionId, vehicleNumber, startDate, email);
		this.type = type;
		this.parkingLot = parkingLot;
		this.leavingAt = leavingAt;
	}
	public subscriptionType getType() {
		return type;
	}
	public void setType(subscriptionType type) {
		this.type = type;
	}
	public String getParkingLot() {
		return parkingLot;
	}
	public void setParkingLot(String parkingLot) {
		this.parkingLot = parkingLot;
	}
	public String getLeavingAt() {
		return leavingAt;
	}
	public void setLeavingAt(String leavingAt) {
		this.leavingAt = leavingAt;
	}
	
	
	
	
	
	
	
}
