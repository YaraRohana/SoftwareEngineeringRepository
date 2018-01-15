package webServlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import allClasses.CPS;
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
			CPS cps=null;
			try {
				cps = CPS.getInstance();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			boolean res=false;
			if (customerId != null) {
					Order o = new Order(0,OrderType.preOrder,parkingLot,arrivingDate,leavingDate,arrivingTime,leavingTime,customerId,vehicle,false,false,false);
					try {
						System.out.println("trying to add order");
						res=da.addOrder(o);
						if(res) {
							cps.getOrders().add(o);
						}
					} catch (SQLException e) {
						System.out.println("Unable to add order");
						e.printStackTrace();
						return;
					}

					Vehicle v = new Vehicle(vehicle, customerId,-1,-1,-1);
					try {
						res=da.addVehicle(v);
						if(res) {
							cps.getVehicles().add(v);
						}
					} catch (SQLException e) {
						System.out.println("Unable to add vehicle");
						e.printStackTrace();
					}
					Customer c = new Customer(customerId, email);
					try {
						res=da.addCustomer(c);
						if(res) {
							cps.getCustomers().add(c);
						}
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
