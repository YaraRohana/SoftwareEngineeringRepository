package webServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import allClasses.CPS;
import allClasses.Complaint;
import da.DataAccess;

/**
 * Servlet implementation class AddComplaint
 */
@WebServlet("/AddComplaint")
public class AddComplaint extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddComplaint() {
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
		// System.out.println("we are here");
		String parkingLot = request.getParameter("parkingLot");
		String customerId = request.getParameter("customerID");
		String text = request.getParameter("text");
		DataAccess da = new DataAccess();
		boolean res = false;
		if (customerId != null) {
			try {
				res = da.checkIfCustomerExistsById(customerId);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			if (!res) {
				System.out.println("Unable to add complaint");
				return;
			}
			Complaint c = new Complaint(parkingLot, customerId, text);
			try {
				res = da.addComplaint(c);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("res is" + res);
			// PrintWriter out=PrintWriter
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
		// response.getWriter().append("Served at: ").append(request.getContextPath());

	}

}
