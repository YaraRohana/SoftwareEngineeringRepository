package webServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import allClasses.Customer;
import allClasses.Vehicle;
import allClasses.Order;
import allClasses.Order.OrderType;
import da.DataAccess;

/**
 * Servlet implementation class AddOrder
 */
@WebServlet("/AddPreOrder")
public class AddPreOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddPreOrder() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String customerId = request.getParameter("id");
		String vehicle = request.getParameter("vehicle");
		String email = request.getParameter("email");
		String parkingLot = request.getParameter("parkingLot");
		String arrivingTime = request.getParameter("arrivingTime");
		String leavingTime = request.getParameter("leavingTime");
		String arrivingDate = request.getParameter("arrivingDate");
		String leavingDate = request.getParameter("leavingDate");
		DataAccess da = new DataAccess();
		boolean res = false;
		if (customerId != null && vehicle != null && email != null && parkingLot != null && leavingDate != null
				&& leavingTime != null && arrivingDate != null && arrivingTime != null) {
			
			Customer c = new Customer(customerId, email);
			try {
				res = da.addCustomer(c);
			} catch (SQLException e) {
				System.out.println("Unable to add customer");
				e.printStackTrace();
			}
			
			Vehicle v = new Vehicle(vehicle, customerId);
			try {
				res = da.addVehicle(v);
			} catch (SQLException e) {
				System.out.println("Unable to add vehicle");
				e.printStackTrace();
			}
			Order o = new Order(0, OrderType.preOrder, parkingLot, arrivingDate, leavingDate, arrivingTime, leavingTime,
					customerId, vehicle, false, false, false);
			System.out.println("trying to add order");
			try {
				res = da.addOrder(o);
			} catch (SQLException | ParseException e) {
				System.out.println("Unable to add order");
				e.printStackTrace();
				return;
			}
			PrintWriter out = response.getWriter();
			out.println(res);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
