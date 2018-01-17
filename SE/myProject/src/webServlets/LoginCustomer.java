package webServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import da.DataAccess;

/**
 * Servlet implementation class LoginCustomer
 */
@WebServlet("/LoginCustomer")
public class LoginCustomer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginCustomer() {
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
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		String customerId = request.getParameter("id");
		DataAccess da = new DataAccess();
		boolean res = false;
		if (customerId != null) {
			try {
				res = da.checkIfCustomerExistsById(customerId);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (!res) {
				System.out.println("Customer does not exist!");
				PrintWriter out = response.getWriter();
				out.println(res);
				return;
			}
			try {
				res = da.getCustomerConnectionStatus(customerId);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (!res) {
				System.out.println("Customer already connected");
				PrintWriter out = response.getWriter();
				out.println(res);
				return;
			}
			try {
				da.loginCustomer(customerId);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			PrintWriter out = response.getWriter();
			out.println(res);
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
