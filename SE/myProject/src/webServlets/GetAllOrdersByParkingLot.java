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
 * Servlet implementation class GetAllOrdersByParkingLot
 */
@WebServlet("/GetAllOrdersByParkingLot")
public class GetAllOrdersByParkingLot extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAllOrdersByParkingLot() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String parkingLot=request.getParameter("parkingLot");
		DataAccess da=new DataAccess();
		if(parkingLot!=null) {
			ArrayList<Order> orders=new ArrayList<Order>();
			try {
				orders=da.getAllOrdersByParkingLot(parkingLot);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			JSONObject jsonOrder;
			for (Order order : orders) {
				jsonOrder=new JSONObject();
				try {
					jsonOrder.put("leavingDate", order.getLeavingDate());
					jsonOrder.put("type", order.getType());
					jsonOrder.put("parkingLot", order.getParkingLot());
					jsonOrder.put("arrivingAt", order.getArrivingAt());
					jsonOrder.put("leavingAt", order.getLeavingAt());
					jsonOrder.put("arrivingDate", order.getArrivingDate());
					jsonOrder.put("vehicle", order.getVehicleNum());
					jsonOrder.put("canceled", order.isCanceled());

					out.println(jsonOrder);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				out.flush();
				System.out.println(order.toString());
			}
			
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
