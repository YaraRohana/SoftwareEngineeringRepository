package webServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import allClasses.CPS;
import allClasses.Customer;
import allClasses.Order;
import allClasses.Vehicle;
import allClasses.Order.OrderType;
import da.DataAccess;

/**
 * Servlet implementation class AddPreOrder
 */
@WebServlet("/AddUponArrivalOrder")
public class AddUponArrivalOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddUponArrivalOrder() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String parkingLot = request.getParameter("parkingLot");
		System.out.println("parkingLot"+parkingLot);
		String leavingDate = request.getParameter("leavingDate");
		System.out.println("leavingDate"+leavingDate);
		String leavingAt = request.getParameter("leavingAt");
		System.out.println(leavingAt);
		String customerId = request.getParameter("id");
		System.out.println(customerId);
		String vehicle = request.getParameter("vehicle");
		System.out.println(vehicle);
		String email = request.getParameter("email");
		System.out.println(email);
		DataAccess da = new DataAccess();
		boolean res = false;
		boolean res1 = false;
		if (customerId != null && leavingAt != null && leavingDate != null && vehicle != null && email != null
				&& parkingLot != null) {
			System.out.println("we're in");
			//String orderId=da.getSaltString();
			Order o = new Order(0, OrderType.uponArrivalOrder, parkingLot, null, leavingDate, null, leavingAt,
					customerId, vehicle, false, false, false);
			Customer c = new Customer(customerId, email);
			try {
				res = da.addCustomer(c);
			} catch (SQLException e) {
				System.out.println("Unable to add customer");
				e.printStackTrace();
			}

			Vehicle v = new Vehicle(vehicle, customerId, -1, -1, -1);
			try {
				res = da.addVehicle(v);
			} catch (SQLException e) {
				System.out.println("Unable to add vehicle");
				e.printStackTrace();
			}
			try {
				// System.out.println("trying to add order");
				res1 = da.addOrder(o);
			} catch (SQLException e) {
				System.out.println("Unable to add order");
				e.printStackTrace();
				return;
			}

			PrintWriter out = response.getWriter();
			out.println(res1);

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
