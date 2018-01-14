package webServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import allClasses.Order;
import da.DataAccess;

/**
 * Servlet implementation class GetAllOrdersByCustomerId
 */
@WebServlet("/GetAllOrdersByCustomerId")
public class GetAllOrdersByCustomerId extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetAllOrdersByCustomerId() {
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
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String id = request.getParameter("customerId");
		ArrayList<Order> orders = new ArrayList<Order>();
		DataAccess da = new DataAccess();
		try {
			orders = da.getAllOrdersByCustomerId(id);
			DateFormat dateFormat = new SimpleDateFormat("HH:mm");
			Date date = new Date();
			String now = dateFormat.format(date);
			for (Order order : orders) {
				String leavingAt = order.getLeavingAt();
				Date d1 = null;
				Date d2=null;
				try {
					d1 = new SimpleDateFormat("HH:mm").parse(leavingAt);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				try {
					d2 = new SimpleDateFormat("HH:mm").parse(now);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(d2.after(d1)) {
					orders.remove(order);
				}
			}
		} catch (SQLException e) {
			System.out.println("Unable to retrieve orders for customer " + id);
			e.printStackTrace();
		}
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		JSONObject jsonOrder;
		for (Order order : orders) {
			try {
				jsonOrder = new JSONObject();
				jsonOrder.put("order ID", order.getOrderId());
				jsonOrder.put("type", order.getType());
				jsonOrder.put("parking lot", order.getParkingLot());
				jsonOrder.put("arriving at", order.getArrivingAt());
				jsonOrder.put("leaving at", order.getLeavingAt());
				jsonOrder.put("customer Id", order.getCustomerId());
				jsonOrder.put("vehicle number", order.getVehicleNum());
				out.println(jsonOrder);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.flush();
			System.out.println(order.toString());
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
