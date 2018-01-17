package webServlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.SQLException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import allClasses.Order;
import allClasses.Order.OrderType;
import da.DataAccess;

/**
 * Servlet implementation class GetOrderCost
 */
@WebServlet("/GetOrderCost")
public class GetOrderCost extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetOrderCost() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		double cost = -1;
		String arrivingTime = request.getParameter("arrivingTime");
		String leavingTime = request.getParameter("leavingTime");
		String arrivingDate = request.getParameter("arrivingDate");
		String leavingDate = request.getParameter("leavingDate");
		 String parkingLot = request.getParameter("parkingLot");
		String type = request.getParameter("type");
		String customerId = request.getParameter("id");
		String vehicle = request.getParameter("vehicle");
		Date ArrivingAt = null;
		Date LeavingAt = null;
		Date ArrivingDate = null;
		Date LeavingDate = null;
		OrderType Type = null;
		DataAccess da = new DataAccess();
		if (type != null && arrivingTime != null && leavingTime != null && arrivingDate != null && leavingDate != null
				&& customerId != null && vehicle != null && parkingLot!=null) {
			Order o = null;
			try {
				o = da.checkIfOrderExistsByAllParameters(customerId, vehicle, arrivingDate, arrivingTime, leavingDate,
						leavingTime, parkingLot);
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			if (o != null) {
				cost = -1;
				PrintWriter out = response.getWriter();
				out.println(cost);
			} else {
				if (type.equals("preOrder")) {
					Type = OrderType.preOrder;
				} else if (type.equals("uponArrivalOrder")) {
					Type = OrderType.uponArrivalOrder;
				}
				System.out.println("we are in GetOrderCost and the type is " + Type);
				DateFormat dateFormat = new SimpleDateFormat("HH:mm");
				DateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");
				try {
					ArrivingAt = dateFormat.parse(arrivingTime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					LeavingAt = dateFormat.parse(leavingTime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					ArrivingDate = dateFormat1.parse(arrivingDate);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					LeavingDate = dateFormat1.parse(leavingDate);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if ((ArrivingDate.before(LeavingDate) == true)
						|| (ArrivingDate.equals(LeavingDate) && ArrivingAt.before(LeavingAt))) {
					try {
						cost = da.getOrderCost(arrivingTime, leavingTime, arrivingDate, leavingDate, Type);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				PrintWriter out = response.getWriter();
				out.println(cost);
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
