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

import allClasses.ParkingLot;
import da.DataAccess;

/**
 * Servlet implementation class CompanyManagerGetAllParkingLotsInfo
 */
@WebServlet("/CompanyManagerGetAllParkingLotsInfo")
public class CompanyManagerGetAllParkingLotsInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CompanyManagerGetAllParkingLotsInfo() {
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
		DataAccess da = new DataAccess();
		ArrayList<ParkingLot> parkingLots = new ArrayList<ParkingLot>();
		try {
			parkingLots = da.GetAllParkingLots();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;
		JSONObject parkingLotInfo;
		PrintWriter out=response.getWriter();
		for (ParkingLot parkingLot : parkingLots) {
			System.out.println(parkingLot.getName());
			parkingLotInfo = new JSONObject();
			try {
				parkingLotInfo.put("parking lot", parkingLot.getName());
				parkingLotInfo.put("manager", parkingLot.getManager());
				parkingLotInfo.put("width", parkingLot.getWidth());
				
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				parkingLotInfo.put("number of executed orders",
						da.getNumberOfExecutedOrdersByParkingLot(parkingLot.getName()));
				parkingLotInfo.put("number of canceled orders",
						da.getNumberOfCanceledOrdersByParkingLot(parkingLot.getName()));
				parkingLotInfo.put("number of late arrival orders",
						da.getNumberOfLateArrivalOrdersByParkingLot(parkingLot.getName()));
				out.println(parkingLotInfo);
			} catch (JSONException | SQLException e) {
				// TODO Auto-generated catch block
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
