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

import allClasses.Customer;
import allClasses.FullSubscription;
import allClasses.OneCarRegularSubscription;
import allClasses.Order;
import allClasses.Vehicle;
import allClasses.Order.OrderType;
import allClasses.Subscription.subscriptionType;
import da.DataAccess;

/**
 * Servlet implementation class AddRegularOneCarSubscription
 */
@WebServlet("/AddRegularOneCarSubscription")
public class AddRegularOneCarSubscription extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddRegularOneCarSubscription() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());

		String customerId = request.getParameter("customerId");
		//String subscriptionId = request.getParameter("subscriptionId");
		String vehicleNumber = request.getParameter("vehicleNumber");
		String startDate = request.getParameter("startDate");
		String parkingLot = request.getParameter("parkingLot");
		String leavingAt = request.getParameter("leavingAt");
		String email = request.getParameter("email");
		
		
		DataAccess da = new DataAccess();
		boolean res = false;
		if (vehicleNumber != null && customerId != null) {

			Customer customer = new Customer(customerId, email);
			customer.setCredit(0);
			try {
				res = da.addCustomer(customer);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
			java.util.Date date = null;
			try {
				date = sdf1.parse(startDate);
				System.out.println("date after parse is" + date);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());
			OneCarRegularSubscription c = new OneCarRegularSubscription(customerId, null, vehicleNumber, sqlStartDate,
					email, subscriptionType.oneCarRegularSubscription, parkingLot, leavingAt);
			try {
				res = da.addOneCarRegularSubscription(c);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			Vehicle v = new Vehicle(vehicleNumber, customerId); 
			try {
				da.addVehicle(v);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			System.out.println("res is" + res);
	
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
	}

}
