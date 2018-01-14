package webServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.fabric.xmlrpc.base.Param;

import allClasses.CPS;
import allClasses.Customer;
import allClasses.OneCarBusinessSubscription;
import allClasses.OneCarRegularSubscription;
import allClasses.Vehicle;
import allClasses.Subscription.subscriptionType;
import da.DataAccess;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

/**
 * Servlet implementation class AddBusinessRegularSubscription
 */
@WebServlet("/AddBusinessRegularSubscription")
public class AddBusinessRegularSubscription extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddBusinessRegularSubscription() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String num = request.getParameter("num");
		int numOfVehicles = 0;
		if (num != null) {
			// System.out.println("we are here");
			numOfVehicles = Integer.parseInt(num);
			String customerId = request.getParameter("customerId");
			String email = request.getParameter("email");
			String startDate = request.getParameter("date");
			// String subsId = request.getParameter("subscriptionId");
			Date date1 = null;
			try {
				date1 = new SimpleDateFormat("dd-MM-yyyy").parse(startDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			java.sql.Date sqlDate = new java.sql.Date(date1.getTime());
			DataAccess da = new DataAccess();
			OneCarBusinessSubscription tmp = null;
			boolean res = false;
			CPS cps = CPS.getInstance();
			String subsId = da.getSaltString();
			for (int i = 0; i < numOfVehicles; i++) {
				// System.out.println("we're inside the for");
				String vehicleNum = request.getParameter("vehicleNum");
				String parkingLot = request.getParameter("parkingLot");
				String leavingAt = request.getParameter("leavingAt");
				tmp = new OneCarBusinessSubscription(customerId, subsId, vehicleNum, sqlDate, email,
						subscriptionType.regularBusinessSubscription, parkingLot, leavingAt);
				try {
					res = da.addBuisnessRegularSubscription(tmp);
					if(res) {
						cps.getSubscriptions().add(tmp);
					}
					
				} catch (SQLException e) {
					System.out.println("Unable to add business subscription");
					e.printStackTrace();
				}
			}
			PrintWriter out = response.getWriter();
			out.println(res);
			PrintWriter out1 = response.getWriter();
			out1.println(subsId);
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
