package webServlets;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import allClasses.Complaint;
import allClasses.Customer;
import allClasses.FullSubscription;
import allClasses.Subscription.subscriptionType;
import allClasses.Vehicle;
import da.DataAccess;

/**
 * Servlet implementation class FullSubscription
 */
@WebServlet("/FullSubscription")
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
		String email=request.getParameter("email");
		System.out.println(startDate);

		DataAccess da = new DataAccess();
		boolean res = false;
		if (vehicleNumber != null && customerId != null) {

				Customer customer = new Customer(customerId, email);
				try {
					res = da.addCustomer(customer);
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
			FullSubscription c = new FullSubscription(customerId, null, vehicleNumber, sqlStartDate,email, sqlStartDate,
					subscriptionType.fullSubscription);
			try {
				res = da.addFullSubscription(c);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Vehicle v = new Vehicle(vehicleNumber, customerId);
			try {
				da.addVehicle(v);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("res is" + res);

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
