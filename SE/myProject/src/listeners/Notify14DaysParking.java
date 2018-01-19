package listeners;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import allClasses.Email;
import allClasses.FullSubscription;
import da.DataAccess;

public class Notify14DaysParking implements Runnable {

	@Override
	public void run() {

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -14);
		Date dt = cal.getTime();
		java.sql.Date parkedAt = (new java.sql.Date(dt.getTime()));
		System.out.println("parked at" + parkedAt);
		DataAccess db = new DataAccess();
		ArrayList<FullSubscription> fullSubs;
		try {
			fullSubs = db.getAllFullSubsByArrivedSince(parkedAt);

			for (FullSubscription sub : fullSubs) {
				if (db.isVehicleParking(sub.getVehicleNumber()))
					Email.sendEmail(db.getCustomerMailById(sub.getCustomerId()), "Parking 14 days in a row", getBody(sub));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private String getBody(FullSubscription sub) {

		StringBuilder sb = new StringBuilder();
		sb.append("Subscription details: ").append(System.lineSeparator());
		sb.append("Customer Id- " + sub.getCustomerId()).append(System.lineSeparator());
		sb.append("Subscription Id- " + sub.getSubsciptionId()).append(System.lineSeparator());
		sb.append("Car Number- " + sub.getVehicleNumber()).append(System.lineSeparator());
		sb.append("Parked the car since- " + sub.getArrivedSince()).append(System.lineSeparator());
		sb.append("*Car can park maximum 14 days in a row.").append(System.lineSeparator());

		return sb.toString();

	}
}
