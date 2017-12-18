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

		/*String arrivingAt = request.getParameter("arrivingAt");
		String leavingAt = request.getParameter("leavingAt");
		String parkingLot = request.getParameter("parkingLot");
		// String chargement =request.getParameter("chargement");
		// String compensation=request.getParameter("compensation");
		// String subscriptionDate = request.getParameter("subscriptionDate");

		// vehicle info
		// String vehicleNumber =request.getParameter("vehicleNumber");
		// String isLate=request.getParameter("isLate");

		// customer info
		// String customerID =request.getParameter("customerID");
		// String email=request.getParameter("email");

		// parkingLot info
		// String parkingLotID=request.getParameter("parkingLotID");
		// String name=request.getParameter("name");
		// String location=request.getParameter("location");
		// String isActive =request.getParameter("isActive");
		// String available=request.getParameter("available");
		// String manager=request.getParameter("manager");
		// Order order = new
		// Order(ID,OrderType.prior,arrivingAt,leavingAt,chargement,compensation);
		Order order = new Order(Order.OrderType.atParkingLot, arrivingAt, leavingAt, "20$", "0$", "8/12/2017");
		DataAccess da = new DataAccess();
		int parkingLotId = da.getParkingIdLotByName(parkingLot);
		if (parkingLotId != 0) {
			boolean res = false;
			try {
			} catch (SQLException e) {
				da.AddOrder(order);
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(res);
			// response.getWriter().append("Served at: ").append(request.getContextPath());
		}*/
	}



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
