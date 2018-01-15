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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import allClasses.CPS;
import allClasses.ParkingLot;
import da.DataAccess;

/**
 * Servlet implementation class GetAllParkingLots
 */
@WebServlet("/GetAllParkingLots")
public class GetAllParkingLots extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetAllParkingLots() {
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
	//	response.getWriter().append("Served at: ").append(request.getContextPath());
		ArrayList<ParkingLot> parkingLots = new ArrayList<ParkingLot>();
		ArrayList<ParkingLot> CPSparkingLots = new ArrayList<ParkingLot>();
		CPS cps=null;
		try {
			cps = CPS.getInstance();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		DataAccess da = new DataAccess();
		try {
			parkingLots = da.GetAllParkingLots();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			CPSparkingLots=cps.getParkingLots();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("CPS parkingLots ");
		for (ParkingLot parkingLot : CPSparkingLots) {
			System.out.println("here");
			System.out.println(parkingLot.toString());
		}
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		JSONObject jsonParkingLot;
		for (ParkingLot parkingLot : parkingLots) {
			try {
				jsonParkingLot = new JSONObject();
				jsonParkingLot.put("name", parkingLot.getName());
				jsonParkingLot.put("location", parkingLot.getLocation());
				jsonParkingLot.put("manager", parkingLot.getManager());
				jsonParkingLot.put("width",parkingLot.getWidth());
				out.println(jsonParkingLot);
			} catch (JSONException ex) {

			}
			out.flush();
			System.out.println(parkingLot.toString());
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
