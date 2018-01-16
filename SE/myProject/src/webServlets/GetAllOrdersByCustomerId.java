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
import java.util.Iterator;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.jni.Local;
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
		// response.getWriter().append("Served at:
		// ").append(request.getContextPath());
		String id = request.getParameter("customerId");
		ArrayList<Order> orders = new ArrayList<Order>();
		DataAccess da = new DataAccess();
		if (id != null) {
			try {
				orders = da.getAllOrdersByCustomerId(id);
				Iterator<Order> iter = orders.iterator();
				while (iter.hasNext()) {
					Order order = iter.next();
					String leavingAt = order.getLeavingAt();
					System.out.println("leaving at from data base:" + leavingAt);
					String leavingDate = order.getLeavingDate();
					System.out.println("leaving Date from data base" + leavingDate);
					Date nowTime = null;
					Date nowDate = null;
					Date leavingDate1 = null;
					Date leavingTime1 = null;
					DateFormat dateFormatt = new SimpleDateFormat("dd-MM-yyyy");
					DateFormat timeFormat = new SimpleDateFormat("HH:mm");
					try {
						leavingDate1 = dateFormatt.parse(leavingDate);
						System.out.println("leavingDate=" + leavingDate1);
						leavingTime1 = timeFormat.parse(leavingAt);
						System.out.println("leavingTime=" + leavingTime1);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						Date todayDate = new Date();
						String NowTime = timeFormat.format(todayDate);
						nowTime = timeFormat.parse(NowTime);
						String NowDate1 = dateFormatt.format(todayDate);
						nowDate = dateFormatt.parse(NowDate1);
						System.out.println("nowTime=" + nowTime);
						System.out.println("nowDate=" + nowDate);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (nowDate.after(leavingDate1)) {
						System.out.println("it is");
						iter.remove();
					} else if (nowDate.equals(leavingDate1)) {
						if (nowTime.after(leavingTime1)) {
							iter.remove();
						}
					}
				}
				for (Order order : orders) {
					System.out.println(order.toString());
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
					jsonOrder.put("vehicle", order.getVehicleNum());
					jsonOrder.put("type", order.getType());
					jsonOrder.put("parkingLot", order.getParkingLot());
					jsonOrder.put("arrivingAt", order.getArrivingAt());
					jsonOrder.put("leavingAt", order.getLeavingAt());
					jsonOrder.put("arrivingDate", order.getArrivingDate());
					jsonOrder.put("leavingDate", order.getLeavingDate());
					jsonOrder.put("customerId", order.getCustomerId());
					out.println(jsonOrder);
				} catch (JSONException e) {
					// TODO Auto-generated
					e.printStackTrace();
				}
				out.flush();
				System.out.println(order.toString());
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
