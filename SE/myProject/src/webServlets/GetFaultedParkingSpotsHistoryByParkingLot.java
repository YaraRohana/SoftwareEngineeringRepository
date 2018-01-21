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

import allClasses.FaultedParkingSpotHistory;
import da.DataAccess;

/**
 * Servlet implementation class GetFaultedParkingSpotsHistory
 */
@WebServlet("/GetFaultedParkingSpotsHistoryByParkingLot")
public class GetFaultedParkingSpotsHistoryByParkingLot extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetFaultedParkingSpotsHistoryByParkingLot() {
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
		String parkingLot = request.getParameter("parkingLot");
		DataAccess da = new DataAccess();
		if (parkingLot != null) {
			ArrayList<FaultedParkingSpotHistory> history = new ArrayList<FaultedParkingSpotHistory>();
			try {
				history = da.getFaultedParkingSpotsHistoryByParkingLot(parkingLot);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			JSONObject historyObject;
			for (FaultedParkingSpotHistory faultedParkingSpotHistory : history) {
				historyObject = new JSONObject();
				try {
					historyObject.put("parkingLot", faultedParkingSpotHistory.getParkingLot());
					historyObject.put("row", Integer.toString(faultedParkingSpotHistory.getRow()));
					historyObject.put("column", Integer.toString(faultedParkingSpotHistory.getColumn()));
					historyObject.put("width", Integer.toString(faultedParkingSpotHistory.getWidth()));
					historyObject.put("date", faultedParkingSpotHistory.getDate());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				out.println(historyObject);
				System.out.println(faultedParkingSpotHistory.toString());
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
