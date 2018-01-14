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
import allClasses.Subscription;
import da.DataAccess;

/**
 * Servlet implementation class NotifyEndSubscription
 */
@WebServlet("/NotifyEndSubscription")
public class NotifyEndSubscription extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NotifyEndSubscription() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
				Email.sendEmail(sub.getEmail(), "Subscription ends in 1 week",  getBody(sub));
			}
			
			for(Subscription sub : regularSubs) {
				Email.sendEmail(sub.getEmail(), "Subscription ends in 1 week", getBody(sub));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}
	
	private String getBody(Subscription sub) {

		StringBuilder sb = new StringBuilder();
		sb.append("Order details: ").append(System.lineSeparator());
		sb.append("Customer Id- " + sub.getCustomerId()).append(System.lineSeparator());
		sb.append("Subscription Id- " + sub.getSubsciptionId()).append(System.lineSeparator());
		sb.append("Subscription since- " + sub.getStartDate()).append(System.lineSeparator());
		sb.append("Car Number- " + sub.getVehicleNumber()).append(System.lineSeparator());
		
		return sb.toString();

	}

}
