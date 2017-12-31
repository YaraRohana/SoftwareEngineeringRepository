package allClasses;

import java.sql.Date;

public class RegularSubscription extends Subscription{

	String parkingLot;
	String leavingAt;
	subscriptionType type=subscriptionType.regularSubscription;
	
	public RegularSubscription(String customerId, String subsciptionId, String vehicleNumber, Date startDate,
			String parkingLot, String leavingAt, subscriptionType type) {
		super(customerId, subsciptionId, vehicleNumber, startDate);
		this.parkingLot = parkingLot;
		this.leavingAt = leavingAt;
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

	public subscriptionType getType() {
		return type;
	}

	public void setType(subscriptionType type) {
		this.type = type;
	}


	


}
