package allClasses;



public class Order {
	
	public enum OrderType
	{
		atParkingLot(0) , prior(1) , routineSubscription(2) , fullSubscription(3);
		
		private final int value;
	    private OrderType(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }
	}

	int id;
	OrderType type;
	String arrivingAt;
	String leavingAt;
	String chargement;
	String Compensation;
	String SubscriptionDate;
	
	public Order(int id, OrderType type, String arrivingAt, String leavingAt, String chargement, String compensation, String subscriptionDate) {
		super();
		this.id = id;
		this.type = type;
		this.arrivingAt = arrivingAt;
		this.leavingAt = leavingAt;
		this.chargement = chargement;
		this.Compensation = compensation;
		this.SubscriptionDate = subscriptionDate;
	}

	public int getId() {
		return id;
	}

	public String getSubscriptionDate() {
		return SubscriptionDate;
	}

	public void setSubscriptionDate(String subscriptionDate) {
		SubscriptionDate = subscriptionDate;
	}

	public void setId(int id) {
		this.id = id;
	}

	public OrderType isType() {
		return type;
	}

	public void setType(OrderType type) {
		this.type = type;
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

	public String getChargement() {
		return chargement;
	}

	public void setChargement(String chargement) {
		this.chargement = chargement;
	}

	public String getCompensation() {
		return Compensation;
	}

	public void setCompensation(String compensation) {
		Compensation = compensation;
	}
	
	
	
}
