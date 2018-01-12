package webServlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import allClasses.Customer;
import allClasses.ParkingLot;
import allClasses.Vehicle;
import allClasses.Order;
import allClasses.Order.OrderType;
import da.DataAccess;

/**
 * Servlet implementation class AddOrder
 */
@WebServlet("/AddOrder")
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
			String arrivingTime=request.getParameter("arrivingTime");
			String leavingTime=request.getParameter("leavingTime");
			String leavingDate=request.getParameter("leavingDate");
			String arrivingDate=request.getParameter("arrivingDate");
			String parkingLot = request.getParameter("parkingLot");
			String customerId = request.getParameter("id");
			String vehicle = request.getParameter("vehicle");
			String email = request.getParameter("email");
			DataAccess da = new DataAccess();
			if (customerId != null) {
					Order o = new Order(0,OrderType.preOrder,parkingLot,arrivingDate,leavingDate,arrivingTime,leavingTime,customerId,vehicle,false,false,false);
					try {
						System.out.println("trying to add order");
						da.addOrder(o);
					} catch (SQLException e) {
						System.out.println("Unable to add order");
						e.printStackTrace();
						return;
					}

					Vehicle v = new Vehicle(vehicle, customerId,-1,-1,-1);
					try {
						da.addVehicle(v);
					} catch (SQLException e) {
						System.out.println("Unable to add vehicle");
						e.printStackTrace();
					}
					Customer c = new Customer(customerId, email);
					try {
						da.addCustomer(c);
					} catch (SQLException e) {
						System.out.println("Unable to add customer");
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

	}

}