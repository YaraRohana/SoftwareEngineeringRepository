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
public class AddOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddOrder() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String type = request.getParameter("type");
		if (type != null) {
			int realType = type.equals("uponArrivalOrder") ? 0 : 1;
			OrderType ordertype = (realType == 0) ? Order.OrderType.uponArrivalOrder : Order.OrderType.preOrder;
			String parkingLot = request.getParameter("parkingLot");
			String arrivingAt = request.getParameter("arrival");
			String leavingAt = request.getParameter("leavingAt");
			String customerId = request.getParameter("customerId");
			String vehicle = request.getParameter("vehicleNum");
			String email = request.getParameter("email");
			DataAccess da = new DataAccess();
			int parkingLotID = 0;
			if (customerId != null) {
				try {
					parkingLotID = da.getParkingIdLotByName(parkingLot);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if (parkingLotID != 0) {
					Order o = new Order(0, ordertype, parkingLotID, arrivingAt, leavingAt, customerId, vehicle);
					try {
						da.addOrder(o);
					} catch (SQLException e) {
						System.out.println("Unable to add order");
						e.printStackTrace();
					}

					Vehicle v = new Vehicle(vehicle, customerId, false, false);
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
