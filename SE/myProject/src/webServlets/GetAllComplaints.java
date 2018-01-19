package webServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import allClasses.Complaint;
import da.DataAccess;

/**
 * Servlet implementation class GetAllComplaints
 */
@WebServlet("/GetAllComplaints")
public class GetAllComplaints extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetAllComplaints() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean complaintStatus = false;
		ArrayList<Complaint> complaints = new ArrayList<Complaint>();
		DataAccess da = new DataAccess();
		try {
			complaints = da.getAllComplaints();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		PrintWriter out = response.getWriter();
		// PrintWriter outt = response.getWriter();
		response.setContentType("application/json");
		JSONObject jsonComplaint;
		for (Complaint complaint : complaints) {
			try {
				boolean res = da.getComplaintStatus(complaint.getSubmissionDate(), complaint.isChecked());
				if (res == true && complaintStatus==false) {
					complaintStatus = true;
					System.out.println("complaint status changed to true");
					break;
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		out.println(complaintStatus);
		System.out.println("complaint status is" + complaintStatus);
		for (Complaint complaint : complaints) {
			System.out.println("**");
			jsonComplaint = new JSONObject();
			try {
				jsonComplaint.put("parking lot", complaint.getParkingLot());
				jsonComplaint.put("customerId", complaint.getCustomerId());
				jsonComplaint.put("submissionDate", complaint.getSubmissionDate());
				jsonComplaint.put("text", complaint.getComplaintText());
				out.println(jsonComplaint);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			out.flush();
			System.out.println(complaint.toString());
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
