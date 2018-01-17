package webServlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import da.DataAccess;

/**
 * Servlet implementation class UpdateComplaintCompensation
 */
@WebServlet("/UpdateComplaintCompensationAndReply")
public class UpdateComplaintCompensationAndReply extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateComplaintCompensationAndReply() {
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
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String customerId = request.getParameter("customerId");
		String compensation = request.getParameter("compensation");
		String reply = request.getParameter("reply");
		if (customerId != null && reply != null && compensation != null) {
			System.out.println("here");
			int compensation1 = Integer.parseInt(compensation);
			DataAccess da = new DataAccess();
			try {
				da.updateCompensationForCustomer(customerId, compensation1);
				da.setComplaintAsCheckedByCustomerId(customerId);
				String mail=da.getCustomerMailById(customerId);
				da.sendComplaintReplyToMail(mail, reply);
			} catch (SQLException e) {
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
