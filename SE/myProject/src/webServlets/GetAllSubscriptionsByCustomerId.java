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
import allClasses.Subscription;
import da.DataAccess;

/**
 * Servlet implementation class GetAllSubscriptionsByCustomerId
 */
@WebServlet("/GetAllSubscriptionsByCustomerId")
public class GetAllSubscriptionsByCustomerId extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetAllSubscriptionsByCustomerId() {
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String id = request.getParameter("customerId");
		System.out.println("here");
		ArrayList<Subscription> subs = new ArrayList<Subscription>();
		DataAccess da = new DataAccess();
		if (id != null) {
			try {
				subs = da.getAllSubsByCustomerId(id);
			} catch (SQLException e) {
				System.out.println("Unable to retrieve subscriptions for customer " + id);
				e.printStackTrace();
			}
			System.out.println("Subscriptions of customer " + id + ":");
			for (Subscription subscription : subs) {
				System.out.println(subscription.toString());
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
		// doGet(request, response);

	}

}
