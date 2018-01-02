package allClasses;

import java.sql.Date;

public class FullSubscription extends Subscription {

	Date arrivedSince=null;
	subscriptionType type=subscriptionType.fullSubscription;
	
	



	public FullSubscription(String customerId, String subsciptionId, String vehicleNumber, Date startDate, String email,
			Date arrivedSince, subscriptionType type) {
		super(customerId, subsciptionId, vehicleNumber, startDate, email);
		this.arrivedSince = arrivedSince;
		this.type = type;
	}


	public Date getArrivedSince() {
		return arrivedSince;
	}


	public void setArrivedSince(Date arrivedSince) {
		this.arrivedSince = arrivedSince;
	}


	public subscriptionType getType() {
		return type;
	}


	public void setType(subscriptionType type) {
		this.type = type;
	}
	
	
}
