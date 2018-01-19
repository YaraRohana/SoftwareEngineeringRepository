package listeners;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import allClasses.Email;
import allClasses.Order;
import da.DataAccess;

public class NotifyLateArrival implements Runnable {

	private static final DateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
	private static final DateFormat sdfTime = new SimpleDateFormat("HH:mm");

	@Override
	public void run() {
		try {

			Date dt = new Date();
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE, -30);
			Date halfHourAgo = cal.getTime();
			DataAccess db = new DataAccess();
			ArrayList<Order> orders = db.getAllPreOrdersByArrivingDate(sdfDate.format(dt).toString(),
					sdfTime.format(dt).toString());
			ArrayList<Order> cancelOrders = db.getAllPreOrdersByArrivingDate(sdfDate.format(halfHourAgo).toString(),
					sdfTime.format(halfHourAgo).toString());
			for (Order or : orders) {
				if (!or.isArrivingLate() && !or.isCanceled() && !db.isVehicleParking(or.getVehicleNum())) {
					Email.sendEmail(db.getCustomerMailById(or.getCustomerId()), "Is Order still relevante",
							getBody(or));
				}
			}
			for (Order or : cancelOrders) {
				if (!or.isArrivingLate() && !or.isCanceled() && !db.isVehicleParking(or.getVehicleNum())) {
					if (!Email.checkAndDeleteMail(db.getCustomerMailById(or.getCustomerId()))) {
						db.cancelOrder(or);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private String getBody(Order or) {

		StringBuilder sb = new StringBuilder();
		sb.append("Reply within 30 minutes if you gonna come late. Otherwise, Your order will be canceled.")
				.append(System.lineSeparator());
		sb.append("Order details: ").append(System.lineSeparator());
		sb.append("Customer Id- " + or.getCustomerId()).append(System.lineSeparator());
		sb.append("Order Id- " + or.getOrderId()).append(System.lineSeparator());
		sb.append("Car Number- " + or.getVehicleNum()).append(System.lineSeparator());

		return sb.toString();

	}

}
