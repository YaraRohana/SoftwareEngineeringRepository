package webServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import allClasses.Order;
import allClasses.Order.OrderType;
import da.DataAccess;

/**
 * Servlet implementation class CancelOrder
 */
@WebServlet("/CancelOrder")
public class CancelOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CancelOrder() {
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
		String vehicleNumber = request.getParameter("vehicle");
		// String type = request.getParameter("type");
		// OrderType type1 = type.equals("uponArrivalOrder") ?
		// OrderType.uponArrivalOrder : OrderType.preOrder;
		String parkingLot = request.getParameter("parkingLot");
		String arrivingAt = request.getParameter("arrivingAt");
		String leavingAt = request.getParameter("leavingAt");
		String arrivingDate = request.getParameter("arrivingDate");
		String leavingDate = request.getParameter("leavingDate");
		Order res = null;
		int finalCredit=0;
		int changedCredit=0;
		// boolean result=false;
		DataAccess da = new DataAccess();
		if (customerId != null & vehicleNumber != null & parkingLot != null && arrivingAt != null
				&& leavingAt != null & leavingDate != null && arrivingDate != null) {
			System.out.println("we're in");
			PrintWriter out=response.getWriter();
			try {
				res = da.checkIfOrderExistsByAllParameters(customerId, vehicleNumber, arrivingDate, arrivingAt,
						leavingDate, leavingAt, parkingLot);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(res==null) {
				out.println(false);
				out.println("Order does not exist!");
				return;
			}
				if (res.isCanceled() == false) {
					try {
						da.cancelOrder(res);
						changedCredit=da.getCancelOrderCredit(res);
						finalCredit=da.getCreditByCustomerId(customerId);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					System.out.println("Order already canceled,cannot cancel again");
				}
			
			out.println(changedCredit);
			out.println(finalCredit);
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
