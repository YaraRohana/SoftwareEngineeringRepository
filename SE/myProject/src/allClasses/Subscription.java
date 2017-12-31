package allClasses;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.Date;

public abstract class Subscription {
	
	
	protected String customerId;
	protected  String subsciptionId;
	protected String vehicleNumber;
	protected static final DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	protected Date startDate;

	public enum subscriptionType{
		regularSubscription(0),regularBusinessSubscription(1),fullSubscription(2);
		
		private final int value;
	    private subscriptionType(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }
	}

	public Subscription(String customerId, String subsciptionId, String vehicleNumber, Date startDate) {
		super();
		this.customerId = customerId;
		this.subsciptionId = subsciptionId;
		this.vehicleNumber = vehicleNumber;
		this.startDate = startDate;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getSubsciptionId() {
		return subsciptionId;
	}

	public void setSubsciptionId(String subsciptionId) {
		this.subsciptionId = subsciptionId;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	

}
