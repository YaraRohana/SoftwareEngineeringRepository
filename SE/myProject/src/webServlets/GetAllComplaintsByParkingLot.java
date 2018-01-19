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
 * Servlet implementation class GetAllComplaintsByParkingLot
 */
@WebServlet("/GetAllComplaintsByParkingLot")
public class GetAllComplaintsByParkingLot extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAllComplaintsByParkingLot() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String parkingLot=request.getParameter("parkingLot");
		DataAccess da=new DataAccess();
		if(parkingLot!=null) {
			PrintWriter out = response.getWriter();
			ArrayList<Complaint> complaints=new ArrayList<Complaint>();
			try {
				complaints=da.getAllComplaintsByParkingLot(parkingLot);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
//			if(complaints.size()==0) {
//				System.out.println("No complaints were filed in this parking lot");
//				out.println(false);	
//				return;
//			}
			response.setContentType("application/json");
			JSONObject jsonComplaint;
			for (Complaint complaint : complaints) {
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
