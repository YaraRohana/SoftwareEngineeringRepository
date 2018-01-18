package webServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;

import com.mysql.fabric.xmlrpc.base.Param;

import allClasses.Customer;
import allClasses.OneCarBusinessSubscription;
import allClasses.OneCarRegularSubscription;
import allClasses.Vehicle;
import allClasses.Subscription.subscriptionType;
import da.DataAccess;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

/**
 * Servlet implementation class AddBusinessRegularSubscription
 */
@WebServlet("/AddBusinessRegularSubscription")
public class AddBusinessRegularSubscription extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddBusinessRegularSubscription() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int i = 0, price = 0;
		String pl = null;
		DataAccess da = new DataAccess();
		OneCarBusinessSubscription tmp = null;
		String customerId = request.getParameter("customerId");
		String email = request.getParameter("email");
		String startDate = request.getParameter("date");
		String vehicles = request.getParameter("vehicles");
		Date date1 = null;
		if (customerId != null && email != null && startDate != null && vehicles != null) {
			int realVehicles = Integer.parseInt(vehicles);
			try {
				date1 = new SimpleDateFormat("dd-MM-yyyy").parse(startDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			java.sql.Date sqlDate = new java.sql.Date(date1.getTime());
			JSONArray v = new JSONArray();
			try {
				v = new JSONArray(vehicles);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			i = 0;
			try {
				while (v.getJSONObject(i) != null) {
					String vehicleNum = v.getJSONObject(i).getString("vehicleNum");
					String parkingLot = v.getJSONObject(i).getString("parkingLot");
					String leavingAt = v.getJSONObject(i).getString("leavingAt");
					String subsId = da.getSaltString();
					// pl = parkingLot;
					tmp = new OneCarBusinessSubscription(customerId, subsId, vehicleNum, sqlDate, email,
							subscriptionType.regularBusinessSubscription, parkingLot, leavingAt);
					try {
						da.addBuisnessRegularSubscription(tmp);
					} catch (SQLException e) {
						System.out.println("Unable to add business subscription");
						e.printStackTrace();
					}
					i++;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
