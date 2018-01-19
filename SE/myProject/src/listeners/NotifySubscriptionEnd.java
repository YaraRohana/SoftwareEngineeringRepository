package listeners;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.sql.SQLException;

import allClasses.Email;
import allClasses.FullSubscription;
import allClasses.Subscription;
import da.DataAccess;

/*	should send message a week before the Subscription ends	*/

public class NotifySubscriptionEnd implements Runnable {
	
	@Override
	public void run() {
		
		Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -21);
        Date dt = cal.getTime();
        java.sql.Date weekLeftForSubscription = (new java.sql.Date(dt.getTime()));
        DataAccess db = new DataAccess();
		ArrayList<FullSubscription> fullSubs;
		ArrayList<Subscription> regularSubs;
		try {
			fullSubs = db.getAllFullSubsByStartingDate(weekLeftForSubscription);
			regularSubs = db.getAllRegSubsByStartingDate(weekLeftForSubscription);
			for(FullSubscription sub : fullSubs) {
				Email.sendEmail(db.getCustomerMailById(sub.getCustomerId()), "Subscription ends in 1 week",  getBody(sub));
			}
			
			for(Subscription sub : regularSubs) {
				Email.sendEmail(db.getCustomerMailById(sub.getCustomerId()), "Subscription ends in 1 week", getBody(sub));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	private String getBody(Subscription sub) {

		StringBuilder sb = new StringBuilder();
		sb.append("Subscription details: ").append(System.lineSeparator());
		sb.append("Customer Id- " + sub.getCustomerId()).append(System.lineSeparator());
		sb.append("Subscription Id- " + sub.getSubsciptionId()).append(System.lineSeparator());
		sb.append("Subscription since- " + sub.getStartDate()).append(System.lineSeparator());
		sb.append("Car Number- " + sub.getVehicleNumber()).append(System.lineSeparator());
		
		return sb.toString();

	}
}
