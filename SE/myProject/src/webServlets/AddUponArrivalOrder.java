package webServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
		String leavingDate = request.getParameter("leavingDate");
		String leavingAt = request.getParameter("leavingAt");
		String customerId = request.getParameter("id");
		String vehicle = request.getParameter("vehicle");
		String email = request.getParameter("email");
		System.out.println(email);
		DataAccess da = new DataAccess();
		boolean res = false;
		if (customerId != null && leavingAt != null && leavingDate != null && vehicle != null && email != null
				&& parkingLot != null) {
			PrintWriter out = response.getWriter();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("HH:mm");
			LocalDateTime now = LocalDateTime.now();
			String today = dtf.format(now);
			String hourNow = dtf2.format(now);
			Customer c = new Customer(customerId, email);
			try {
				res = da.addCustomer(c);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			Vehicle v = new Vehicle(vehicle, customerId);
			try {
				res = da.addVehicle(v);
			} catch (SQLException e) {
				System.out.println("Unable to add vehicle");
				e.printStackTrace();
			}
			try {
				Order o = new Order(0, OrderType.uponArrivalOrder, parkingLot, today, leavingDate, hourNow, leavingAt,
						customerId, vehicle, false, false, false);
				res = da.addOrder(o);
			} catch (SQLException e) {
				System.out.println("Unable to add order");
				e.printStackTrace();
				return;
			}

			out.println(res);

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
