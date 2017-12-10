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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Order info
		String orderID =request.getParameter("orderID");
//		String type =request.getParameter("type");
//		String arrivingAt=request.getParameter("arrivingAt");
//		String leavingAt=request.getParameter("leavingAt");
//		String chargement =request.getParameter("chargement");
//		String compensation=request.getParameter("compensation");
//		String subscriptionDate = request.getParameter("subscriptionDate");
		
		//vehicle info
	//	String vehicleNumber =request.getParameter("vehicleNumber");
	//	String isLate=request.getParameter("isLate");
		
		//customer info
	//	String customerID =request.getParameter("customerID");
	//	String email=request.getParameter("email");
		
		//parkingLot info
	//	String parkingLotID=request.getParameter("parkingLotID");
//		String name=request.getParameter("name");
//		String location=request.getParameter("location");
//		String isActive =request.getParameter("isActive");
//		String available=request.getParameter("available");
//		String manager=request.getParameter("manager");
		
		response.getWriter().append("Trying to add new order to the list ");
		
		int ID=0;
		if(orderID!=null)
			ID=Integer.parseInt(orderID);
		System.out.println("id is" + ID);
		
		if(ID!=0){
		Customer customer=new Customer("123456789","customer@gmail.com");
		ParkingLot p=new ParkingLot(4, "Mall", "haifa", true, true, "Yaman");
		Vehicle vehicle = new Vehicle("22-222-22",false);
	//	Order order = new Order(ID,OrderType.prior,arrivingAt,leavingAt,chargement,compensation);
		Order order = new Order(ID,OrderType.prior,"10:00","12:00","20$","0$","8/12/2017");
		
		DataAccess da=new DataAccess();
		boolean res=false;
		try {
			res=da.AddOrder(order,customer,vehicle,p);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(res);
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		}
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
	}

}
