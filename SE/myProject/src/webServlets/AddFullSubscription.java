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

import allClasses.Customer;
import allClasses.FullSubscription;
import allClasses.Subscription.subscriptionType;
import allClasses.Vehicle;
import da.DataAccess;

/**
 * Servlet implementation class FullSubscription
 */
@WebServlet("/AddFullSubscription")
public class AddFullSubscription extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddFullSubscription() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		String customerId = request.getParameter("customerId");
		String vehicleNumber = request.getParameter("vehicleNumber");
		String startDate = request.getParameter("startDate");
		String email = request.getParameter("email");
		DataAccess da = new DataAccess();
		boolean res = false;
		//boolean res1 = false;
		if (vehicleNumber != null && customerId != null) {
			PrintWriter out = response.getWriter();
			try {
				res = da.checkIfDateIsFromNowOn(startDate, "23:59");
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			if(res == false) {
				System.out.println("Starting Date already passed!");
				//PrintWriter out = response.getWriter();
				out.println(res);
			}
			Customer customer = new Customer(customerId, email);
			customer.setCredit(0);
			customer.setConnected(false);
			try {
				res = da.addCustomer(customer);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Vehicle v = new Vehicle(vehicleNumber, customerId);
			try {
				res = da.addVehicle(v);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
			java.util.Date date = null;
			try {
				date = sdf1.parse(startDate);
				System.out.println("date after parse is" + date);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());
			String subsId = da.getSaltString();
			FullSubscription c = new FullSubscription(customerId, subsId, vehicleNumber, sqlStartDate, email,
					sqlStartDate, subscriptionType.fullSubscription);
			try {
				res = da.addFullSubscription(c);
				//PrintWriter out = response.getWriter();
				//out.println(res);

			} catch (SQLException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.println(res);
			out.println(subsId);
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
