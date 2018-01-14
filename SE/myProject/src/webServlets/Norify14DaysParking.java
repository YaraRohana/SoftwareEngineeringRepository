package webServlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import allClasses.Email;
import allClasses.FullSubscription;
import da.DataAccess;


/**
 * Servlet implementation class Norify14DaysParking
 */
@WebServlet("/Norify14DaysParking")
public class Norify14DaysParking extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Norify14DaysParking() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -14);
        Date dt = cal.getTime();
        java.sql.Date weekLeftForSubscription = (new java.sql.Date(dt.getTime()));
        DataAccess db = new DataAccess();
		ArrayList<FullSubscription> fullSubs;
		try {
			fullSubs = db.getAllFullSubsByArrivedSince(weekLeftForSubscription);
			
			for(FullSubscription sub : fullSubs) {
				Email.sendEmail(sub.getEmail(), "Parking 14 days in a row", getBody(sub));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	private String getBody(FullSubscription sub) {

		StringBuilder sb = new StringBuilder();
		sb.append("Order details: ").append(System.lineSeparator());
		sb.append("Customer Id- " + sub.getCustomerId()).append(System.lineSeparator());
		sb.append("Subscription Id- " + sub.getSubsciptionId()).append(System.lineSeparator());
		sb.append("Car Number- " + sub.getVehicleNumber()).append(System.lineSeparator());
		sb.append("Parked the car since- " + sub.getArrivedSince()).append(System.lineSeparator());
		sb.append("*Car can park maximum 14 days in a row.").append(System.lineSeparator());
		
		return sb.toString();

	}
}
