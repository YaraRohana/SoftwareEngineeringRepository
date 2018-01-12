package webServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import allClasses.Customer;
import allClasses.Order.OrderType;
import da.DataAccess;

/**
 * Servlet implementation class GetCancelOrderCredit
 */
@WebServlet("/GetCancelOrderCredit")
public class GetCancelOrderCredit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCancelOrderCredit() {
        super();
        // TODO Auto-generated constructor stub
    }



	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String parkingLot = request.getParameter("parkingLot");
		String customerId = request.getParameter("customerID");
		String arrivingDate = request.getParameter("arrivingDate");
		String arrivingTime =request.getParameter("arrivingTime");
		String leavingTime = request.getParameter("leavingTime");
		String type = request.getParameter("type");
		boolean res = false;
		DataAccess da = new DataAccess();
		
		if(parkingLot!=null&&customerId!=null&&arrivingDate!=null&&arrivingTime!=null&&leavingTime!=null&type!=null) {
			OrderType Type=type.equals("preOrder")? OrderType.preOrder: OrderType.uponArrivalOrder;
			//System.out.println("we are in getCancelOrderCredit and the type is "+Type);
			try {
				res=da.checkIfCustomerExistsById(customerId);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(!res) {
				System.out.println("Customer does not exist!");
				PrintWriter out=response.getWriter();
				out.println(res);
				return;
			}
			
			try {
				res = da.getCancelOrderCredit(parkingLot,customerId, arrivingDate, arrivingTime, leavingTime, Type);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
