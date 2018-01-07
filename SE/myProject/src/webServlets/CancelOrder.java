package webServlets;

import java.io.IOException;
import java.sql.SQLException;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String customerId=request.getParameter("customerId");
		String vehicleNumber=request.getParameter("vehicle");
		String type=request.getParameter("type");
		OrderType type1=type.equals("uponArrivalOrder")? OrderType.uponArrivalOrder: OrderType.preOrder;
		String parkingLot=request.getParameter("parkingLot");
		String arrivingAt=request.getParameter("arrivingAt");
		String leavingAt=request.getParameter("leavingAt");
		String arrivingDate=request.getParameter("arrivingDate");
		String leavingDate=request.getParameter("leavingDate");
		Order res=null;
		DataAccess da=new DataAccess();
		if(customerId!=null & vehicleNumber!=null & parkingLot!=null) {
			try {
				res=da.checkIfOrderExistsByAllParameters(customerId, vehicleNumber, arrivingDate, arrivingAt, leavingDate, leavingAt, parkingLot);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(res!=null) {
				try {
					da.cancelOrder(res);
				} catch (SQLException e) {
					e.printStackTrace();
				}
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
