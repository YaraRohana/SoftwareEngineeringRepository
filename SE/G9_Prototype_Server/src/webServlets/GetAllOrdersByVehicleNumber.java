package webServlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import allClasses.Order;
import da.DataAccess;

/**
 * Servlet implementation class GetAllOrdersByVehicleNumber
 */
@WebServlet("/GetAllOrdersByVehicleNumber")
public class GetAllOrdersByVehicleNumber extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAllOrdersByVehicleNumber() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String vehicle=request.getParameter("vehicleNumber");
		ArrayList<Order> orders=new ArrayList<Order>();
		DataAccess da=new DataAccess();
		try {
			orders=da.getAllOrdersByVehicleNumber(vehicle);
		} catch (SQLException e) {
			System.out.println("Unable to retrieve orders for vehicle " +vehicle);
			e.printStackTrace();
		}
		System.out.println("Orders of vehicle number: "+vehicle+":");
		for (Order order : orders) {
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
