package webServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	String id=request.getParameter("customerId");
	ArrayList<Order> orders=new ArrayList<Order>();
	DataAccess da=new DataAccess();
	try {
		orders=da.getAllOrdersByCustomerId(id);
	} catch (SQLException e) {
		System.out.println("Unable to retrieve orders for customer " +id);
		e.printStackTrace();
	}
	PrintWriter out = response.getWriter();
	response.setContentType("application/json");
	JSONObject jsonOrder;
	for (Order order : orders) {
		try {
			jsonOrder=new JSONObject();
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
